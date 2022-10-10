package facade;

import modele.Plateau;
import modele.Ville;
import modele.enums.NomVillesBleu;
import modele.enums.Virus;

import java.util.List;

public class Facade {

    static Plateau plateau;



    public void initialisation(){
        plateau = new Plateau();
        initialisationVilles();
    }

    public static void initialisationVilles(){
        // les villes bleu
        for (NomVillesBleu nomVillesBleu : NomVillesBleu.values()) {
            Ville ville = new Ville(nomVillesBleu.name(), nomVillesBleu.getPopulation(), nomVillesBleu.getKmCarre(), Virus.BLEU);
            // attribution des voisins
            attributionVoisins(nomVillesBleu.name());
            plateau.getVilles().put(nomVillesBleu.name(),ville);

        }


    }

    public static void attributionVoisins(String ville){

            switch (ville){
                case "Atlanta":
                    plateau.getVilles().get("Atlanta").setVillesVoisines(List.of("Chicago","Essen"));
                case "Chicago":
                    plateau.getVilles().get("Chicago").setVillesVoisines(List.of("Atlanta","Essen"));
                case "Essen":
                    plateau.getVilles().get("Essen").setVillesVoisines(List.of("Chicago","Atlanta"));
            }
    }

    public void displaybullshit(){
        for (Ville ville : plateau.getVilles().values()){
            System.out.println(ville);
        }
    }


}
