package modele.facade;

import modele.exceptions.*;
import modele.elements.Joueur;
import modele.elements.actions.deplacement.DeplacementNavette;
import modele.elements.actions.deplacement.DeplacementVoiture;
import modele.elements.actions.deplacement.DeplacementVolCharter;
import modele.elements.actions.deplacement.DeplacementVolDirect;
import modele.elements.Plateau;
import modele.elements.enums.ModesDeplacements;

import java.util.Scanner;

public class FacadePandemic9Impl implements FacadePandemic9 {
    private static Plateau plateau;


    @Override
    public void initialisation() throws CasCouleurVilleIncorrectException {
        plateau = new Plateau();
        System.out.println(plateau.getVilles());

    }


    @Override
    public void jouerTour(Joueur joueur) throws VilleIntrouvableException, VilleNonVoisineException, VilleInexistanteDansDeckJoueurException, VilleAvecAucuneStationDeRechercheException {
        joueur.setPlateau(plateau);
        Scanner sc = new Scanner(System.in);
        int nbAction = 0;
        while (nbAction < 4){
            System.out.println("Hello vous avez 4 actions possible");
            String choixAction = sc.nextLine();
            switch (choixAction){
                case "DEPLACEMENT":
                    System.out.println("Hello vous avez 4 deplacement possible");
                    String choixDeplacement = sc.nextLine();
                    System.out.println("Quelle ville ?");
//                            TODO : bcp trop de verif à faire
                    String choixVille = sc.nextLine();
                    nbAction++;
                    switch (choixDeplacement){
                        case "VOITURE":
                            joueur.setDeplacement(new DeplacementVoiture());
                            joueur.seDeplacer(ModesDeplacements.VOITURE.name(),choixVille);
                            break;
                        case "NAVETTE":
                            joueur.setDeplacement(new DeplacementNavette());
                            joueur.seDeplacer(ModesDeplacements.NAVETTE.name(),choixVille);
                            break;
                        case "VOL_CHARTER":
                            joueur.setDeplacement(new DeplacementVolCharter());
                            joueur.seDeplacer(ModesDeplacements.VOL_CHARTER.name(),choixVille);
                            break;
                        case "VOL_DIRECT":
                            joueur.setDeplacement(new DeplacementVolDirect());
                            joueur.seDeplacer(ModesDeplacements.VOL_DIRECT.name(),choixVille);
                            break;
                    }
                case "STATION":
                    break;

            }
        }

    }
}
