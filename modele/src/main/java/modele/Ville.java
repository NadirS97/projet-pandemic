package modele;


import java.util.List;
import java.util.Map;

public class Ville {

    private String nomVille;
    private List<Ville> villesVoisines;
    private List<Map<Virus, Integer>> listeVirusVille; // Integer pour le nbCubesActifs par Virus
    private int nbPopulationTotaleVille;
    private int nbPopulationKmCarreeVille;

    // TODO: pour moi il s'agit de la classe CarteVille
    //  on doit rajouter la classe Ville à part: avec (String nomVille, Map<Virus, nbCubesActifs> cubeVille, List<Ville> villesVoisines)
    //  avec 2 constructeurs : un constructeur ville(String nomVille, new Map<nomVirus, 0>, et une liste vide) et un constructeur avec la liste des villes voisines


}
