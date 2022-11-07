package programme;

import modele.elements.Partie;
import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.actions.traiter_maladie.TraiterMaladie;
import modele.elements.cartes.CarteVille;
import modele.exceptions.*;


import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
//       FacadePandemic9Impl facadePandemic9Impl = new FacadePandemic9Impl();
//       facadePandemic9Impl.initialisation();
//       facadePandemic9Impl.JouerTourr("111",new DeplacementNAvette(new Ville()));
//       facadePandemic9Impl.JouerTourr("211",new PartageConnaissance());
//       facade.jouerTour(new Joueur());
//      ================================================================================================================

        try {
            Plateau plateau = new Plateau();

            plateau.getVilles().values().forEach(ville -> {
                System.out.println(ville);
            });
            System.out.println("=====================================");

            plateau.getToutesLesCartesRolesExistante().forEach(carteRole -> {
                System.out.println(carteRole);
            });
            System.out.println("=====================================");

            plateau.getLesVirus().values().forEach(virus -> {
                System.out.println(virus);
            });
            System.out.println("=====================================");

            PionJoueur joueur = new PionJoueur("Nadir", plateau);
            joueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("Atlanta")));
            joueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("Chicago")));
            joueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("Tokyo")));
            joueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("Alger")));
            System.out.println(joueur.getNbMaxCarteVilleMemeCouleurDeckJoueur());
            System.out.println("=====================================");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//      ================================================================================================================
    }
}
