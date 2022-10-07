package modele;

import modele.cartes.CarteCouleur;

import java.util.List;

public class Ville extends CarteCouleur {
    private String nomVille;
    private List<Ville> listeVillesJoignables;
    private Virus virus;
    private int populationTotaleVille;
    private int populationKmCarreVille;

    // TODO: pour moi il s'agit de la classe CarteVille
    //  on doit rajouter la classe Ville à part: avec (String nomVille, Map<Virus, nbCubesActifs> cubeVille, List<Ville> villesVoisines)
    //  avec 2 constructeurs : un constructeur ville(String nomVille, new Map<nomVirus, 0>, et une liste vide) et un constructeur avec la liste des villes voisines


    public String getNomVille() {
        return nomVille;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }

    public List<Ville> getListeVillesJoignables() {
        return listeVillesJoignables;
    }

    public void setListeVillesJoignables(List<Ville> listeVillesJoignables) {
        this.listeVillesJoignables = listeVillesJoignables;
    }

    public Virus getVirus() {
        return virus;
    }

    public void setVirus(Virus virus) {
        this.virus = virus;
    }

    public int getPopulationTotaleVille() {
        return populationTotaleVille;
    }

    public void setPopulationTotaleVille(int populationTotaleVille) {
        this.populationTotaleVille = populationTotaleVille;
    }

    public int getPopulationKmCarreVille() {
        return populationKmCarreVille;
    }

    public void setPopulationKmCarreVille(int populationKmCarreVille) {
        this.populationKmCarreVille = populationKmCarreVille;
    }
}
