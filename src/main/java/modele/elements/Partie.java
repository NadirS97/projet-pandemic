package modele.elements;


import lombok.Getter;
import lombok.Setter;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteVille;
import modele.elements.enums.EtatVirus;
import modele.elements.enums.NomsRoles;
import modele.exceptions.*;
import modele.utils.DonneesVariablesStatiques;

import java.io.FileNotFoundException;
import java.util.*;

@Getter
@Setter
public class Partie {

//    Map<PionJoueur,CarteRole> joueurs;

    private List<PionJoueur> joueurs;
    int indexJoueur;
    private Plateau plateau;
    private boolean victoire;
    private boolean defaite;
    private PionJoueur joueurActuel;

    private String codePartie;


    private Partie(String codePartie) throws RoleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException, VilleIntrouvableException {
        // marqueur eclosion et propagation placé à 0 lors de la création du plateau
        // lors de la création du plateau, toute la créations des cartes du jeu se font
        this.codePartie = codePartie;
        this.plateau = new Plateau();
        joueurs = new ArrayList<>();
        victoire = false;
        defaite = false;
        plateau.getVilleByName("Atlanta").setStationDeRechercheVille(true);
    }


    public static Partie creerPartieDeuxJoueurs(String codePartie) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, FileNotFoundException, VirusIntrouvableException {
        Partie partie = new Partie(codePartie);
        partie.ajoutJoueursDansPartie(2);
        partie.distributionCarteJoueurs(4);
        partie.miseEnPlaceJeuCartePropagation();
        partie.determinerQuiCommencePartie();
        return partie;
    }

    public static Partie creerPartieTroisJoueurs(String codePartie) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        Partie partie = new Partie(codePartie);
        partie.ajoutJoueursDansPartie(3);
        partie.distributionCarteJoueurs(3);
        partie.miseEnPlaceJeuCartePropagation();
        partie.determinerQuiCommencePartie();
        return partie;
    }

    public static Partie creerPartieQuatreJoueurs(String codePartie) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        Partie partie = new Partie(codePartie);
        partie.ajoutJoueursDansPartie(4);
        partie.distributionCarteJoueurs(2);
        partie.miseEnPlaceJeuCartePropagation();
        partie.determinerQuiCommencePartie();
        return partie;
    }

    private void ajoutJoueursDansPartie(int nbJoueurs) {
        for (int i = 0 ; i < nbJoueurs ; i++){
            joueurs.add(new PionJoueur(this));
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
        for (int i = 0; i < DonneesVariablesStatiques.nbCartesARetournerPhase; i++){
           Ville villeContamine =  plateau.piocherCartePropagation(0);
           Virus virus = plateau.getLesVirus().get(villeContamine.getCouleurVirusVille());
           plateau.getVilleByName(villeContamine.getNomVille()).getNbCubeVirusVille().put(virus, DonneesVariablesStatiques.nbCubesAPlacerPremierePhase);
           virus.retirerCubesSac(DonneesVariablesStatiques.nbCubesAPlacerPremierePhase);

        }
        for (int i = 0 ; i < DonneesVariablesStatiques.nbCartesARetournerPhase; i++){
            Ville villeContamine =  plateau.piocherCartePropagation(0);
            Virus virus = plateau.getLesVirus().get(villeContamine.getCouleurVirusVille());
            plateau.getVilleByName(villeContamine.getNomVille()).getNbCubeVirusVille().put(virus,DonneesVariablesStatiques.nbCubesAPlacerDeuxiemePhase);
            virus.retirerCubesSac(DonneesVariablesStatiques.nbCubesAPlacerDeuxiemePhase);
        }
        for (int i = 0 ; i < DonneesVariablesStatiques.nbCartesARetournerPhase; i++){
            Ville villeContamine =  plateau.piocherCartePropagation(0);
            Virus virus = plateau.getLesVirus().get(villeContamine.getCouleurVirusVille());
            plateau.getVilleByName(villeContamine.getNomVille()).getNbCubeVirusVille().put(virus, DonneesVariablesStatiques.nbCubesAPlacerTroisiemePhase);
            virus.retirerCubesSac(DonneesVariablesStatiques.nbCubesAPlacerTroisiemePhase);
        }
    }

    /**
     * On ne garde dans un premier temps que les cartes ville et évènement. Chaque joueur reçoit
     * alors 4 cartes pour une partie à 2 joueurs, 3 cartes pour une partie à 3 joueurs ou 2 cartes
     * pour une partie à 4 joueurs.
     */
    private void distributionCarteJoueurs(int nbCartesDistribue){
        for (PionJoueur pionJoueur : joueurs){
            for (int i = 0 ; i < nbCartesDistribue ; i++){
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
        joueurActuel.setNbActions(DonneesVariablesStatiques.nbActionsMaxParTour);
    }

    public PionJoueur getJoueurSuivant(){
        int indexJoueur  = getIndexJoueur() + 1;


        if (indexJoueur >= joueurs.size()) {
            return joueurs.get(0);
        }
        return joueurs.get(indexJoueur);


    }

    public boolean isVictoire() throws VictoireFinDePartieException {
        if (plateau.isToutLesRemedeDecouvert()) {
            victoire = true;
            throw new VictoireFinDePartieException();
        }
        return false;
    }

    public void effetCarteMedecin() {
        List<Virus> virusDejaGueris = this.getPlateau()
                .getLesVirus()
                .values()
                .stream()
                .filter(virus -> virus.getEtatVirus().equals(EtatVirus.TRAITE))
                .toList();

        Optional<PionJoueur> medecin = this.getJoueurs()
                .stream()
                .filter(joueur -> joueur.getRoleJoueur().getNomRole().equals(NomsRoles.MEDECIN))
                .findFirst();

        if (medecin.isPresent()) {
            virusDejaGueris.forEach(medecin
                    .get()
                    .getVilleActuelle()
                    .getNbCubeVirusVille()
                    .keySet()::remove);

            virusDejaGueris.forEach(v -> {
                HashMap<String, Virus> listeVaccinationContreVirus = null;
                listeVaccinationContreVirus = medecin
                        .get()
                        .getVilleActuelle()
                        .getListeVaccinationContreVirus();

                if (!listeVaccinationContreVirus.containsKey(v.getVirusCouleur()))
                    listeVaccinationContreVirus.put(v.getVirusCouleur(), v);
            });
        }
    }

    public boolean presenceMedecin() {
        return joueurs.stream().anyMatch(joueur -> joueur.getRoleJoueur().getNomRole().equals(NomsRoles.MEDECIN));
    }
}
