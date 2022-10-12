package modele;

import lombok.Getter;
import modele.cartes.CarteJoueur;
import modele.cartes.CartePropagation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Plateau {

    private List<Virus> listeVirus;
    private Map<String,Ville> villes;
    private int marqueurVitessePropagation;   // entre 1et 3 = vitesse2 , 4,5 = vitesse3 , 6,7 vitesse 4 , pas vraiment besoin d'un tableau ?
    private int marqueurVitesseEclosion;
    private List<CarteJoueur> piocheCarteJoueur;
    private List<CarteJoueur> defausseCarteJoueur;
    private List<CartePropagation> piocheCartePropagation;
    private List<CartePropagation> defausseCartePropagation;

    public Plateau() {
        listeVirus = new ArrayList<>();
        villes = new HashMap<>();
        marqueurVitessePropagation = 0;
        marqueurVitesseEclosion = 0;
        piocheCarteJoueur = new ArrayList<>();
        defausseCarteJoueur = new ArrayList<>();
        piocheCartePropagation = new ArrayList<>();
        defausseCartePropagation = new ArrayList<>();
    }

    Ville getVilleByName(String name){
        return villes.get(name);
    }

    public Boolean isVille(String nomVille){
        return villes.containsKey(nomVille);
    }

}
