package modele.elements;


import lombok.*;

import java.util.*;

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
    private String couleurVirusVille;


    public Ville(String nomVille) {
        this.nomVille = nomVille;
    }

    public Ville(String nomVille, int nbPopulationTotaleVille, int nbPopulationKmCarreeVille){
        this.villesVoisinesVille = new ArrayList<>();
        this.nomVille = nomVille;
        this.nbPopulationTotaleVille = nbPopulationTotaleVille;
        this.nbPopulationKmCarreeVille = nbPopulationKmCarreeVille;
        this.nbCubeVirusVille = new HashMap<>();

    }

    public void setVillesVoisines(List<String> villesVoisines) {
        this.villesVoisinesVille = villesVoisines;
    }

    public void setStationDeRechercheVille(boolean stationDeRechercheVille) {
        this.stationDeRechercheVille = stationDeRechercheVille;
    }

    public String retourneVirusNbCubeVirusVille(){
        StringBuilder s = new StringBuilder("[");
        for(Map.Entry<Virus, Integer> donneesCubesVirusVille : nbCubeVirusVille.entrySet()){
            s.append("(").append(donneesCubesVirusVille.getKey().getVirusCouleur()).append(", ").append(donneesCubesVirusVille.getValue()).append(")");
        }
        return s + "]";
    }

    public void setEclosionVille(boolean eclosionVille) {
        this.eclosionVille = eclosionVille;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "nomVille = '" + nomVille + '\'' +
                ", villesVoisinesVille = " + villesVoisinesVille +
                ", couleurVirusVille = "+ couleurVirusVille +
                ", nbCubesVirusVille = "+ retourneVirusNbCubeVirusVille() +
                ", nbPopulationTotaleVille = " + nbPopulationTotaleVille +
                ", nbPopulationKmCarreeVille = " + nbPopulationKmCarreeVille +
                ", stationDeRechercheVille = " + stationDeRechercheVille +
                ", eclosionVille = " + eclosionVille +
                '}';
    }

    public void setCouleurVirusVille(String couleurVirusVille) {
        this.couleurVirusVille = couleurVirusVille;
    }
}
