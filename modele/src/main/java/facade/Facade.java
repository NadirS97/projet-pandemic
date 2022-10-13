package facade;

import exceptions.CasCouleurVilleIncorrectException;
import exceptions.VilleIntrouvableException;
import exceptions.VilleNonVoisineException;
import modele.Joueur;
import modele.actions.deplacement.DeplacementNavette;
import modele.actions.deplacement.DeplacementVoiture;
import modele.actions.deplacement.DeplacementVolCharter;
import modele.actions.deplacement.DeplacementVolDirect;
import modele.enums.Couleurs;
import modele.Plateau;
import modele.Ville;
import modele.Virus;
import modele.enums.DonneesVille;
import modele.enums.ModesDeplacements;

import static modele.enums.DonneesVille.*;

import java.util.List;
import java.util.Scanner;

public class Facade {

    private static Plateau plateau;



    public void initialisation() throws CasCouleurVilleIncorrectException {
        plateau = new Plateau();
        System.out.println(plateau.getVilles());

    }



    public void jouerTour(Joueur joueur) throws VilleIntrouvableException, VilleNonVoisineException {
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
