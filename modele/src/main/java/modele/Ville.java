package modele;


import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@ToString
@Getter
@AllArgsConstructor
public class Ville {

    private String nomVille;
    private List<Ville> villesVoisines;
    private List<Map<Virus, Integer>> listeVirusVille; // Integer pour le nbCubesActifs par Virus
    private int nbPopulationTotaleVille;
    private int nbPopulationKmCarreeVille;



    public Ville(String nomVille, List<Ville> villesVoisines, int nbPopulationTotaleVille, int nbPopulationKmCarreeVille,Virus virus) {

        Map<Virus,Integer> virusVille = new HashMap<>();
        virusVille.put(virus,0);
        this.listeVirusVille = new ArrayList<>();
        this.listeVirusVille.add(virusVille);
        this.nomVille = nomVille;
        this.villesVoisines = villesVoisines;
        this.nbPopulationTotaleVille = nbPopulationTotaleVille;
        this.nbPopulationKmCarreeVille = nbPopulationKmCarreeVille;
    }

    // TODO: pour moi il s'agit de la classe CarteVille
    //  on doit rajouter la classe Ville à part: avec (String nomVille, Map<Virus, nbCubesActifs> cubeVille, List<Ville> villesVoisines)
    //  avec 2 constructeurs : un constructeur ville(String nomVille, new Map<nomVirus, 0>, et une liste vide) et un constructeur avec la liste des villes voisines



}
