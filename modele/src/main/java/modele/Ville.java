package modele;


import lombok.*;
//import modele.enums.Virus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class Ville {

    private String nomVille;
    private List<String> villesVoisinesVille;
    private Map<Virus,Integer> nbCubeVirusVille;
    private int nbPopulationTotaleVille;
    private int nbPopulationKmCarreeVille;
    private boolean stationDeRechercheVille = false;
    private boolean eclosionVille = false;

//
    public Ville(String nomVille, int nbPopulationTotaleVille, int nbPopulationKmCarreeVille,Virus virus){
        this.nbCubeVirusVille = new HashMap<>();
        this.nomVille = nomVille;
        this.nbPopulationTotaleVille = nbPopulationTotaleVille;
        this.nbPopulationKmCarreeVille = nbPopulationKmCarreeVille;
        this.nbCubeVirusVille.put(virus, 0);
    }

    public void setVillesVoisines(List<String> villesVoisines) {
        this.villesVoisinesVille = villesVoisines;
    }


    public void setStationDeRechercheVille(boolean stationDeRechercheVille) {
        this.stationDeRechercheVille = stationDeRechercheVille;
    }

    public String retourneVirusNbCubeVirusVille(){
        String s = "[";
        for(Map.Entry<Virus, Integer> donneesCubesVirusVille : nbCubeVirusVille.entrySet()){
            s = s + "("+(donneesCubesVirusVille.getKey().getVirusCouleur() + ", " + donneesCubesVirusVille.getValue())+")";
        }
        return s + "]";
    }

    @Override
    public String toString() {
        return "Ville{" +
                "nomVille='" + nomVille + '\'' +
                ", villesVoisinesVille=" + villesVoisinesVille +
                ", nbCubesVirusVille="+retourneVirusNbCubeVirusVille()+
                ", nbPopulationTotaleVille=" + nbPopulationTotaleVille +
                ", nbPopulationKmCarreeVille=" + nbPopulationKmCarreeVille +
                ", stationDeRechercheVille=" + stationDeRechercheVille +
                ", eclosionVille=" + eclosionVille +
                '}';
    }
}
