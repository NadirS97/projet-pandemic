package modele.facade;

import modele.elements.Partie;
import modele.elements.Ville;
import modele.exceptions.*;
import modele.elements.Joueur;
import modele.elements.enums.ModesDeplacements;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FacadePandemic9Impl implements FacadePandemic9 {

    private Map<String, Partie> parties;


    public FacadePandemic9Impl() {
        this.parties = new HashMap<>();
    }

    @Override
    public String creerPartie(String pseudoJoueurPartie) throws  CasCouleurVilleIncorrectException {
        Partie partie = new Partie(pseudoJoueurPartie);
        parties.put(partie.getCodePartie(),partie);
        return partie.getCodePartie();
    }

    @Override
    public void rejoindrePartie(String codePartie, String pseudoJoueurPartie) throws PseudoDejaExistantException,CodePartieInexistantException, DonneManquanteException {
        if(Objects.isNull(parties.get(codePartie)))
            throw new CodePartieInexistantException();
        if (parties.get(codePartie).isJoueurDejaDansPartie(pseudoJoueurPartie))
            throw new PseudoDejaExistantException();
        parties.get(codePartie).getJoueursPartie().put(pseudoJoueurPartie, new Joueur(pseudoJoueurPartie));
    }

    @Override
    public void jouerUnTourDeplacement(String codePartie, String pseudoJoueurPartie, ModesDeplacements deplacement, Ville villeDestination) throws CodePartieInexistantException, PseudoInexistantDansLaPartieException, VilleAvecAucuneStationDeRechercheException, VilleNonVoisineException, VilleInexistanteDansDeckJoueurException {
        Partie partie = this.parties.get(codePartie);
        Joueur joueurPartie = partie.getJoueursPartie().get(pseudoJoueurPartie);

        joueurPartie.choixDeplacement(deplacement);
        joueurPartie.seDeplacer(villeDestination);
        partie.getJoueursPartie().put(pseudoJoueurPartie,joueurPartie);
        parties.put(codePartie,partie);

    }


    @Override
    public boolean estPartieTerminee(String pseudoJoueurPartie) throws CodePartieInexistantException {
        return false;
    }

//    @Override
//    public void initialisation() throws CasCouleurVilleIncorrectException {
//        plateau = new Plateau();
//        System.out.println(plateau.getVilles());
//
//    }


// TODO : pas de println ni user input

//    @Override
//    public void jouerTour(Joueur joueur) throws VilleIntrouvableException, VilleNonVoisineException, VilleInexistanteDansDeckJoueurException, VilleAvecAucuneStationDeRechercheException {
//        joueur.setPlateau(plateau);
//        Scanner sc = new Scanner(System.in);
//        int nbAction = 0;
//        while (nbAction < 4){
//            System.out.println("Hello vous avez 4 actions possible");
//            String choixAction = sc.nextLine();
//            switch (choixAction){
//                case "DEPLACEMENT":
//                    System.out.println("Hello vous avez 4 deplacement possible");
//                    String choixDeplacement = sc.nextLine();
//                    System.out.println("Quelle ville ?");
////                            TODO : bcp trop de verif à faire
//                    String choixVille = sc.nextLine();
//                    nbAction++;
//                    switch (choixDeplacement){
//                        case "VOITURE":
//                            // Déplacer le pion entre deux villes reliées par une ligne
//                            joueur.setDeplacement(new DeplacementVoiture());
//                            joueur.seDeplacer(ModesDeplacements.VOITURE.name(),choixVille);
//                            break;
//                        case "NAVETTE":
//                            // Déplacer le pion entre deux stations de recherche
//                            joueur.setDeplacement(new DeplacementNavette());
//                            joueur.seDeplacer(ModesDeplacements.NAVETTE.name(),choixVille);
//                            break;
//                        case "VOL_CHARTER":
//                            // Défausser la carte ville correspondant à la ville où se trouve le pion pour atteindre n’importe quelle autre ville du plateau
//                            // Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondante à la villeActuelle
//                            joueur.setDeplacement(new DeplacementVolCharter());
//                            joueur.seDeplacer(ModesDeplacements.VOL_CHARTER.name(),choixVille);
//                            plateau.getDefausseCarteJoueur().add(joueur.defausseCarteVilleDeDeckJoueur(joueur.getVilleActuelle()));
//                            break;
//                        case "VOL_DIRECT":
//                            // Défausser une carte ville pour déplacer le pion sur la ville de la carte défaussée
//                            // Pour cela on vérifie que le Joueur possède dans sa main/son deck la carte Ville correspondant à la villeDestination
//                            joueur.setDeplacement(new DeplacementVolDirect());
//                            joueur.seDeplacer(ModesDeplacements.VOL_DIRECT.name(),choixVille);
//                            Ville villeDestination = plateau.getVilleByName(choixVille);
//                            plateau.getDefausseCarteJoueur().add(joueur.defausseCarteVilleDeDeckJoueur(villeDestination));
//                            break;
//                    }
//                case "STATION":
//                    break;
//
//            }
//        }
//
//    }
}
