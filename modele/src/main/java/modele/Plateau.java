package modele;

import modele.cartes.CartePropagation;
import modele.cartes.CarteVille;
import modele.enums.NomVillesBleu;
import modele.enums.Virus;

import java.util.ArrayList;
import java.util.List;

public class Plateau {


    List<Ville> villes = new ArrayList<>();
    int marqueurVitessePropagation;   // entre 1et 3 = vitesse2 , 4,5 = vitesse3 , 6,7 vitesse 4 , pas vraiment besoin d'un tableau ?
    int marqueurVitesseEclosion;
    List<CartePropagation>

//    public void genererVille(String nom, List<CarteVille> listeVillesJoignables, Virus virus, int populationTotale, int populationKmCarre) {
//
//    }
}
