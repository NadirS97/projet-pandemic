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
    private Set<PionJoueur> listePionsJoueursPresents;

    public Ville() {
    }

    public Ville(String nomVille, int nbPopulationTotaleVille, int nbPopulationKmCarreeVille, Virus virus){
        this.villesVoisinesVille = new ArrayList<>();
        this.nbCubeVirusVille = new HashMap<>();
        this.nomVille = nomVille;
        this.nbPopulationTotaleVille = nbPopulationTotaleVille;
        this.nbPopulationKmCarreeVille = nbPopulationKmCarreeVille;
        this.nbCubeVirusVille.put(virus, 0);
        this.listePionsJoueursPresents = new HashSet<>();
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

    @Override
    public String toString() {
        return "Ville{" +
                "nomVille='" + nomVille + '\'' +
                ", villesVoisinesVille=" + villesVoisinesVille +
                ", nbCubesVirusVille="+ retourneVirusNbCubeVirusVille() +
                ", nbPopulationTotaleVille=" + nbPopulationTotaleVille +
                ", nbPopulationKmCarreeVille=" + nbPopulationKmCarreeVille +
                ", stationDeRechercheVille=" + stationDeRechercheVille +
                ", eclosionVille=" + eclosionVille +
                '}';
    }
}
