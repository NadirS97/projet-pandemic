package modele.elements;


import lombok.Getter;
import modele.elements.cartes.CarteRole;
import modele.exceptions.NbJoueursPartieIncorrectException;

import javax.management.relation.Role;
import java.util.*;

@Getter
public class Partie {

//    Map<PionJoueur,CarteRole> joueurs;
    List<PionJoueur> joueurs;
    List<Role> roles;
    Plateau plateau;
    int nbJoueurs;

    public Partie(int nbJoueurs) throws Exception {
        if (nbJoueurs <= 0 || nbJoueurs > 4)
            throw new NbJoueursPartieIncorrectException();
        // marqueur eclosion et propagation placé à 0 lors de la création du plateau
       this.plateau = new Plateau();
       this.nbJoueurs = nbJoueurs;
       joueurs = new ArrayList<>();

       miseEnPlaceJeuCartePropagation();
       distributionCarteJoueurs();
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
    }


}
