package modele.ville;

import modele.Virus;

import java.util.ArrayList;
import java.util.List;

public class VilleImp implements Ville {
    private String nomVille;
//    private Ville ville;
    private List<Ville> listeVillesJoignables = new ArrayList<>();
    private Virus virus;
    private int populationTotaleVille;
    private int populationKmCarreVille;

    // TODO: pour moi il s'agit de la classe CarteVille
    //  on doit rajouter la classe Ville à part: avec (String nomVille, Map<Virus, nbCubesActifs> cubeVille, List<Ville> villesVoisines)
    //  avec 2 constructeurs : un constructeur ville(String nomVille, new Map<nomVirus, 0>, et une liste vide) et un constructeur avec la liste des villes voisines

    public VilleImp(List<Ville> listeVillesJoignables) {
        this.listeVillesJoignables = listeVillesJoignables;
    }
}
