package modele;

import lombok.Getter;
import modele.cartes.CarteJoueur;
import modele.cartes.CartePropagation;
import modele.cartes.CarteVille;
import modele.enums.NomVillesBleu;
import modele.enums.Virus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Plateau {


    Map<String,Ville> villes;
    Map<Ville,Virus> villes2;
    int marqueurVitessePropagation;   // entre 1et 3 = vitesse2 , 4,5 = vitesse3 , 6,7 vitesse 4 , pas vraiment besoin d'un tableau ?
    int marqueurVitesseEclosion;
    List<CarteJoueur> piocheCarteJoueur;
    List<CarteJoueur> defausseCarteJoueur;
    List<CartePropagation> piocheCartePropagation;
    List<CartePropagation> defausseCartePropagation;

    public Plateau() {

        villes = new HashMap<>();
        marqueurVitessePropagation = 0;
        marqueurVitesseEclosion = 0;

        piocheCarteJoueur = new ArrayList<>();
        defausseCarteJoueur = new ArrayList<>();
        piocheCartePropagation = new ArrayList<>();
        defausseCartePropagation = new ArrayList<>();
    }

    //    public void genererVille(String nom, List<CarteVille> listeVillesJoignables, Virus virus, int populationTotale, int populationKmCarre) {
//
//    }
}
