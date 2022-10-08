package modele;


import lombok.*;
import modele.enums.Virus;

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
    private List<String> villesVoisines;
//    private List<Map<Virus, Integer>> listeVirusVille; // Integer pour le nbCubesActifs par Virus

    private Map<Virus,Integer> nbCubeVirus = new HashMap<>();
    private int nbPopulationTotaleVille;
    private int nbPopulationKmCarreeVille;



//
    public Ville(String nomVille, int nbPopulationTotaleVille, int nbPopulationKmCarreeVille,Virus virus){
        this.nomVille = nomVille;
        this.nbPopulationTotaleVille = nbPopulationTotaleVille;
        this.nbPopulationKmCarreeVille = nbPopulationKmCarreeVille;
        nbCubeVirus.put(virus, 0);
    }

    public void setVillesVoisines(List<String> villesVoisines) {
        this.villesVoisines = villesVoisines;
    }


    // TODO: pour moi il s'agit de la classe CarteVille
    //  on doit rajouter la classe Ville à part: avec (String nomVille, Map<Virus, nbCubesActifs> cubeVille, List<Ville> villesVoisines)
    //  avec 2 constructeurs : un constructeur ville(String nomVille, new Map<nomVirus, 0>, et une liste vide) et un constructeur avec la liste des villes voisines



}
