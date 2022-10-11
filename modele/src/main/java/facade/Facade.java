package facade;

import modele.enums.Couleurs;
import modele.Plateau;
import modele.Ville;
import modele.Virus;
import modele.enums.DonneesVille;

import java.util.List;

public class Facade {

    private static Plateau plateau;
    private final int NBVILLESBLEUES = 3;
    private final int NBVILLESJAUNES = 12;
    private final int NBVILLESNOIRES = 12;
    private final int NBVILLESROUGES = 12;

    public void initialisation(){
        plateau = new Plateau();
        initialisationVirus();
        initialisationVilles();
    }

    public void initialisationVirus(){
        for(Couleurs couleursVirus : Couleurs.values()){
            Virus virus = new Virus(couleursVirus);
            plateau.getListeVirus().add(virus);
        }
    }

    public void initialisationVilles(){
        for(Virus virus : plateau.getListeVirus()){
            switch(virus.getVirusCouleur()){
                case BLEU:
                    for (int i = 0; i<NBVILLESBLEUES; i++){
                        DonneesVille donneesVilleBleue = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleBleue.name(), donneesVilleBleue.getPopulationTotaleVille(), donneesVilleBleue.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleBleue.name(),ville);
                        attributionVoisins(donneesVilleBleue.name());
                    }
                    break;
                case JAUNE:
                    for (int i = NBVILLESBLEUES; i<NBVILLESJAUNES; i++){
                        DonneesVille donneesVilleJaune = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleJaune.name(), donneesVilleJaune.getPopulationTotaleVille(), donneesVilleJaune.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleJaune.name(),ville);
                        attributionVoisins(donneesVilleJaune.name());
                    }
                    break;
                case NOIR:
                    break;
                case ROUGE:
                    break;
                default:
                    //Erreur
            }
        }
        // les villes bleues
//        for (NomVillesBleu nomVillesBleu : NomVillesBleu.values()) {
//            Virus virus = plateau.getListeVirus().get()
//            Ville ville = new Ville(nomVillesBleu.name(), nomVillesBleu.getPopulationTotaleVille(), nomVillesBleu.getPopulationKmCarreVille(), virus);
//            // attribution des voisins
//            attributionVoisins(nomVillesBleu.name());
//            plateau.getVilles().put(nomVillesBleu.name(),ville);
//        }


    }

    public static void attributionVoisins(String ville){
            switch (ville){
                case "Atlanta":
                    plateau.getVilles().get("Atlanta").setVillesVoisines(List.of("Chicago","Essen"));
                    break;
                case "Chicago":
                    plateau.getVilles().get("Chicago").setVillesVoisines(List.of("Atlanta","Essen"));
                    break;
                case "Essen":
                    plateau.getVilles().get("Essen").setVillesVoisines(List.of("Chicago","Atlanta"));
                    break;
            }
    }

    public void displaybullshit(){
        for (Ville ville : plateau.getVilles().values()){
            System.out.println(ville);
        }
    }
}
