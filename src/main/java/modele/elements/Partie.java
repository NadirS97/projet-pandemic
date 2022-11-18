package modele.elements;


import lombok.Getter;
import lombok.Setter;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.CarteVille;
import modele.exceptions.NbJoueursPartieIncorrectException;

import javax.management.relation.Role;
import java.util.*;

@Getter
@Setter
public class Partie {

//    Map<PionJoueur,CarteRole> joueurs;
    List<PionJoueur> joueurs;
    int indexJoueur;
    List<Role> roles;
    Plateau plateau;
    int nbJoueurs;
    boolean victoire;
    PionJoueur joueurActuel;


    public Partie(int nbJoueurs) throws Exception {
        if (nbJoueurs <= 0 || nbJoueurs > 4)
            throw new NbJoueursPartieIncorrectException();
        // marqueur eclosion et propagation placé à 0 lors de la création du plateau
       this.plateau = new Plateau();
       this.nbJoueurs = nbJoueurs;
       joueurs = new ArrayList<>();
       victoire = false;

       ajoutJoueursDansPartie(nbJoueurs);
       miseEnPlaceJeuCartePropagation();
       distributionCarteJoueurs();
       determinerQuiCommencePartie();
    }


    private void ajoutJoueursDansPartie(int nbJoueurs){
        for (int i = 0 ; i < nbJoueurs ; i++){
            joueurs.add(new PionJoueur(plateau));
        }
    }

    /**
     * Les 3 premières cartes retournées seront les 3 villes contaminées
     * chacune par 3 cubes de sa couleur (chaque carte propagation a une couleur fixe liée à un
     * virus). Les 3 cartes suivantes déterminent alors les trois prochaines villes infectées par deux
     * cubes de leur couleur et enfin 3 dernières cartes pour contaminer les trois dernières villes
     * avec un cube de leur couleur. Les cartes utilisées sont alors déposées face visible dans la
     * défausse « propagation ».
     */
    private void miseEnPlaceJeuCartePropagation(){
        // 3 premiere, 3 cubes
        for (int i = 0 ; i < 3 ; i++){
           Ville villeContamine =  plateau.piocherCartePropagation(0);
           Virus virus = plateau.getLesVirus().get(villeContamine.getCouleurVirusVille());
           plateau.getVilleByName(villeContamine.getNomVille()).getNbCubeVirusVille().put(virus,3);
        }
        for (int i = 0 ; i < 3 ; i++){
            Ville villeContamine =  plateau.piocherCartePropagation(0);
            Virus virus = plateau.getLesVirus().get(villeContamine.getCouleurVirusVille());
            plateau.getVilleByName(villeContamine.getNomVille()).getNbCubeVirusVille().put(virus,2);
        }
        for (int i = 0 ; i < 3 ; i++){
            Ville villeContamine =  plateau.piocherCartePropagation(0);
            Virus virus = plateau.getLesVirus().get(villeContamine.getCouleurVirusVille());
            plateau.getVilleByName(villeContamine.getNomVille()).getNbCubeVirusVille().put(virus,1);
        }
    }

    /**
     * On ne garde dans un premier temps que les cartes ville et évènement. Chaque joueur reçoit
     * alors 4 cartes pour une partie à 2 joueurs, 3 cartes pour une partie à 3 joueurs ou 2 cartes
     * pour une partie à 4 joueurs.
     */
    private void distributionCarteJoueurs(){
        for (PionJoueur pionJoueur : joueurs){
            for (int i = 0 ; i < nbJoueurs ; i++){
                pionJoueur.getDeckJoueur().add(plateau.getPiocheCarteJoueur().remove(0));
            }
        }
        // une fois la distribution mise en place, on insere les carte epidémie
        plateau.insererCartesEpidemie();
    }

    private void determinerQuiCommencePartie(){
        PionJoueur joueurAvecPlusGrandeVille = joueurs.get(0);
        int plusGrandePopulation = 0;
        for (PionJoueur pionJoueur : joueurs){
            for (CarteJoueur carteJoueur : pionJoueur.getDeckJoueur()) {
                // dans cette boucle on va chercher si le joueur possede 0 ou plusieurs carte villes, et sa plus grande ville
                if (carteJoueur instanceof CarteVille) {
                    // on compare la population de chaque carte joueur ville avec la population la plus grande qu'on a trouvé
                    // si on trouve une ville qui possède une population plus élevé, le joueur qui l'a possede devient le joeuur avec la plus grandeville
                    if ( plusGrandePopulation < ((CarteVille) carteJoueur).getVilleCarteVille().getNbPopulationTotaleVille()){
                        plusGrandePopulation = ((CarteVille) carteJoueur).getVilleCarteVille().getNbPopulationTotaleVille();
                        joueurAvecPlusGrandeVille = pionJoueur;
                    }
                }
            }
        }

        joueurActuel = joueurAvecPlusGrandeVille;
        indexJoueur = joueurs.indexOf(joueurActuel);
    }

    public void joueurSuivant(){
        indexJoueur++;
        if (indexJoueur >= joueurs.size()) {
            indexJoueur = 0;
        }
        joueurActuel = joueurs.get(indexJoueur);
        joueurActuel.setNbActions(4);

    }


    public boolean isVictoire(){
        return plateau.isToutLesRemedeDecouvert();
    }





}
