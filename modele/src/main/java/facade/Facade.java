package facade;

import exceptions.CasCouleurVilleIncorrectException;
import modele.Joueur;
import modele.actions.deplacement.DeplacementNavette;
import modele.actions.deplacement.DeplacementVoiture;
import modele.actions.deplacement.DeplacementVolCharter;
import modele.actions.deplacement.DeplacementVolDirect;
import modele.enums.Couleurs;
import modele.Plateau;
import modele.Ville;
import modele.Virus;
import modele.enums.DonneesVille;

import java.util.List;
import java.util.Scanner;

public class Facade {

    private static Plateau plateau;
    private final int NBVILLESBLEUES = 3;
    private final int NBVILLESJAUNES = 12;
    private final int NBVILLESNOIRES = 12;
    private final int NBVILLESROUGES = 12;

    public void initialisation() throws CasCouleurVilleIncorrectException {
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

    public void initialisationVilles() throws CasCouleurVilleIncorrectException {
        for(Virus virus : plateau.getListeVirus()){
            switch(virus.getVirusCouleur()){
                case BLEU:
                    for (int i = 0; i < NBVILLESBLEUES; ++i) {
                        DonneesVille donneesVilleBleue = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleBleue.name(), donneesVilleBleue.getPopulationTotaleVille(), donneesVilleBleue.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleBleue.name(),ville);
                    }
                    break;
                case JAUNE:
                    for (int i = NBVILLESBLEUES; i < NBVILLESJAUNES; ++i) {
                        DonneesVille donneesVilleJaune = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleJaune.name(), donneesVilleJaune.getPopulationTotaleVille(), donneesVilleJaune.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleJaune.name(),ville);
                    }
                    break;
                case NOIR:
                    for (int i = NBVILLESJAUNES; i < NBVILLESNOIRES; ++i) {
                        DonneesVille donneesVilleNoire = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleNoire.name(), donneesVilleNoire.getPopulationTotaleVille(), donneesVilleNoire.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleNoire.name(),ville);
                    }
                    break;
                case ROUGE:
                    for (int i = NBVILLESNOIRES; i < NBVILLESROUGES; ++i) {
                        DonneesVille donneesVilleRouge = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleRouge.name(), donneesVilleRouge.getPopulationTotaleVille(), donneesVilleRouge.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleRouge.name(),ville);
                    }
                    break;
                default:
                    throw new CasCouleurVilleIncorrectException();
            }
        }
        for(Ville ville : plateau.getVilles().values()){
            attributionVoisins(ville.getNomVille());
        }
    }

    public static void attributionVoisins(String ville){
            switch (ville){
                case "Atlanta":
                    plateau.getVilles().get("Atlanta").setVillesVoisines(List.of(
                            plateau.getVilles().get("Chicago"),
                            plateau.getVilles().get("Washington"),
                            plateau.getVilles().get("Miami")
                    ));
                    break;
                case "Chicago":
                    plateau.getVilles().get("Chicago").setVillesVoisines(List.of(
                            plateau.getVilles().get("Atlanta"),
                            plateau.getVilles().get("San_Francisco"),
                            plateau.getVilles().get("Los_Angeles"),
                            plateau.getVilles().get("Mexico"),
                            plateau.getVilles().get("Montreal")
                    ));
                    break;
                case "Essen":
                    plateau.getVilles().get("Essen").setVillesVoisines(List.of(
                            plateau.getVilles().get("Londres"),
                            plateau.getVilles().get("Paris"),
                            plateau.getVilles().get("Milan"),
                            plateau.getVilles().get("Saint_Petersbourg")
                    ));
                    break;
                case "Londres":
                    plateau.getVilles().get("Londres").setVillesVoisines(List.of(
                            plateau.getVilles().get("New_York"),
                            plateau.getVilles().get("Madrid"),
                            plateau.getVilles().get("Paris"),
                            plateau.getVilles().get("Essen")
                    ));
                    break;
                case "Madrid":
                    plateau.getVilles().get("Madrid").setVillesVoisines(List.of(
                            plateau.getVilles().get("New_York"),
                            plateau.getVilles().get("Alger"),
                            plateau.getVilles().get("Paris"),
                            plateau.getVilles().get("Londres"),
                            plateau.getVilles().get("Sao_Paulo")
                    ));
                    break;
                case "Milan":
                    plateau.getVilles().get("Milan").setVillesVoisines(List.of(
                            plateau.getVilles().get("Essen"),
                            plateau.getVilles().get("Paris"),
                            plateau.getVilles().get("Istanbul")
                    ));
                    break;
                case "Montreal":
                    plateau.getVilles().get("Montreal").setVillesVoisines(List.of(
                            plateau.getVilles().get("New_York"),
                            plateau.getVilles().get("Washington"),
                            plateau.getVilles().get("Chicago")
                    ));
                    break;
                case "Paris":
                    plateau.getVilles().get("Paris").setVillesVoisines(List.of(
                            plateau.getVilles().get("Essen"),
                            plateau.getVilles().get("Alger"),
                            plateau.getVilles().get("Madrid"),
                            plateau.getVilles().get("Londres"),
                            plateau.getVilles().get("Milan")
                    ));
                    break;
                case "Saint_Petersbourg":
                    plateau.getVilles().get("Saint_Petersbourg").setVillesVoisines(List.of(
                            plateau.getVilles().get("Essen"),
                            plateau.getVilles().get("Istanbul"),
                            plateau.getVilles().get("Moscou")
                    ));
                    break;
                case "San_Francisco":
                    plateau.getVilles().get("San_Francisco").setVillesVoisines(List.of(
                            plateau.getVilles().get("Los_Angeles"),
                            plateau.getVilles().get("Chicago"),
                            plateau.getVilles().get("Tokyo"),
                            plateau.getVilles().get("Manille")
                    ));
                    break;
                case "New_York":
                    plateau.getVilles().get("New_York").setVillesVoisines(List.of(
                            plateau.getVilles().get("Montreal"),
                            plateau.getVilles().get("Washington"),
                            plateau.getVilles().get("Madrid"),
                            plateau.getVilles().get("Londres")
                    ));
                    break;
                case "Washington":
                    plateau.getVilles().get("Washington").setVillesVoisines(List.of(
                            plateau.getVilles().get("New_York"),
                            plateau.getVilles().get("Montreal"),
                            plateau.getVilles().get("Atlanta"),
                            plateau.getVilles().get("Miami")
                    ));
                    break;
                case "Bogota":
                    plateau.getVilles().get("Bogota").setVillesVoisines(List.of(
                            plateau.getVilles().get("Miami"),
                            plateau.getVilles().get("Mexico"),
                            plateau.getVilles().get("Lima"),
                            plateau.getVilles().get("Buenos_Aires"),
                            plateau.getVilles().get("Sao_Paulo")
                    ));
                    break;
                case "Buenos_Aires":
                    plateau.getVilles().get("Buenos_Aires").setVillesVoisines(List.of(
                            plateau.getVilles().get("Bogota"),
                            plateau.getVilles().get("Sao_Paulo")
                    ));
                    break;
                case "Johannesburg":
                    plateau.getVilles().get("Johannesburg").setVillesVoisines(List.of(
                            plateau.getVilles().get("Kinshasa"),
                            plateau.getVilles().get("Khartoum")
                    ));
                    break;
                case "Khartoum":
                    plateau.getVilles().get("Khartoum").setVillesVoisines(List.of(
                            plateau.getVilles().get("Kinshasa"),
                            plateau.getVilles().get("Le_Caire"),
                            plateau.getVilles().get("Lagos"),
                            plateau.getVilles().get("Johannesburg")
                    ));
                    break;
                case "Kinshasa":
                    plateau.getVilles().get("Kinshasa").setVillesVoisines(List.of(
                            plateau.getVilles().get("Lagos"),
                            plateau.getVilles().get("Khartoum"),
                            plateau.getVilles().get("Johannesburg")
                    ));
                    break;
                case "Lagos":
                    plateau.getVilles().get("Lagos").setVillesVoisines(List.of(
                            plateau.getVilles().get("Sao_Paulo"),
                            plateau.getVilles().get("Kinshasa"),
                            plateau.getVilles().get("Khartoum")
                    ));
                    break;
                case "Lima":
                    plateau.getVilles().get("Lima").setVillesVoisines(List.of(
                            plateau.getVilles().get("Mexico"),
                            plateau.getVilles().get("Bogota"),
                            plateau.getVilles().get("Santiago")
                    ));
                    break;
                case "Los_Angeles":
                    plateau.getVilles().get("Los_Angeles").setVillesVoisines(List.of(
                            plateau.getVilles().get("Mexico"),
                            plateau.getVilles().get("Chicago"),
                            plateau.getVilles().get("San_Francisco"),
                            plateau.getVilles().get("Sydney")
                    ));
                    break;
                case "Mexico":
                    plateau.getVilles().get("Mexico").setVillesVoisines(List.of(
                            plateau.getVilles().get("Los_Angeles"),
                            plateau.getVilles().get("Chicago"),
                            plateau.getVilles().get("Miami"),
                            plateau.getVilles().get("Bogota"),
                            plateau.getVilles().get("Lima")
                    ));
                    break;
                case "Miami":
                    plateau.getVilles().get("Miami").setVillesVoisines(List.of(
                            plateau.getVilles().get("Washington"),
                            plateau.getVilles().get("Atlanta"),
                            plateau.getVilles().get("Mexico"),
                            plateau.getVilles().get("Bogota")
                    ));
                    break;
                case "Santiago":
                    plateau.getVilles().get("Santiago").setVillesVoisines(List.of(
                            plateau.getVilles().get("Lima")
                    ));
                    break;
                case "Sao_Paulo":
                    plateau.getVilles().get("Sao_Paulo").setVillesVoisines(List.of(
                            plateau.getVilles().get("Madrid"),
                            plateau.getVilles().get("Lagos"),
                            plateau.getVilles().get("Bogota"),
                            plateau.getVilles().get("Buenos_Aires")
                    ));
                    break;
                case "Alger":
                    plateau.getVilles().get("Alger").setVillesVoisines(List.of(
                            plateau.getVilles().get("Madrid"),
                            plateau.getVilles().get("Paris"),
                            plateau.getVilles().get("Istanbul"),
                            plateau.getVilles().get("Le_Caire")
                    ));
                    break;
                case "Bagdad":
                    plateau.getVilles().get("Bagdad").setVillesVoisines(List.of(
                            plateau.getVilles().get("Teheran"),
                            plateau.getVilles().get("Istanbul"),
                            plateau.getVilles().get("Le_Caire"),
                            plateau.getVilles().get("Riyad"),
                            plateau.getVilles().get("Karachi")
                    ));
                    break;
                case "Calcutta":
                    plateau.getVilles().get("Calcutta").setVillesVoisines(List.of(
                            plateau.getVilles().get("Hong_kong"),
                            plateau.getVilles().get("Bangkok"),
                            plateau.getVilles().get("Chennai"),
                            plateau.getVilles().get("Delhi")
                    ));
                    break;
                case "Chennai":
                    plateau.getVilles().get("Chennai").setVillesVoisines(List.of(
                            plateau.getVilles().get("Mumbai"),
                            plateau.getVilles().get("Delhi"),
                            plateau.getVilles().get("Calcutta"),
                            plateau.getVilles().get("Bangkok"),
                            plateau.getVilles().get("Jakarta")
                    ));
                    break;
                case "Delhi":
                    plateau.getVilles().get("Delhi").setVillesVoisines(List.of(
                            plateau.getVilles().get("Teheran"),
                            plateau.getVilles().get("Karachi"),
                            plateau.getVilles().get("Mumbai"),
                            plateau.getVilles().get("Chennai"),
                            plateau.getVilles().get("Calcutta")
                    ));
                    break;
                case "Istanbul":
                    plateau.getVilles().get("Istanbul").setVillesVoisines(List.of(
                            plateau.getVilles().get("Milan"),
                            plateau.getVilles().get("Saint_Petersbourg"),
                            plateau.getVilles().get("Moscou"),
                            plateau.getVilles().get("Bagdad"),
                            plateau.getVilles().get("Le_Caire"),
                            plateau.getVilles().get("Alger")
                    ));
                    break;
                case "Karachi":
                    plateau.getVilles().get("Karachi").setVillesVoisines(List.of(
                            plateau.getVilles().get("Riyad"),
                            plateau.getVilles().get("Mumbai"),
                            plateau.getVilles().get("Delhi"),
                            plateau.getVilles().get("Teheran"),
                            plateau.getVilles().get("Bagdad")
                    ));
                    break;
                case "Le_Caire":
                    plateau.getVilles().get("Le_Caire").setVillesVoisines(List.of(
                            plateau.getVilles().get("Alger"),
                            plateau.getVilles().get("Istanbul"),
                            plateau.getVilles().get("Bagdad"),
                            plateau.getVilles().get("Riyad")
                    ));
                    break;
                case "Moscou":
                    plateau.getVilles().get("Moscou").setVillesVoisines(List.of(
                            plateau.getVilles().get("Saint_Petersbourg"),
                            plateau.getVilles().get("Istanbul"),
                            plateau.getVilles().get("Teheran")
                    ));
                    break;
                case "Mumbai":
                    plateau.getVilles().get("Mumbai").setVillesVoisines(List.of(
                            plateau.getVilles().get("Karachi"),
                            plateau.getVilles().get("Delhi"),
                            plateau.getVilles().get("Chennai")
                    ));
                    break;
                case "Riyad":
                    plateau.getVilles().get("Riyad").setVillesVoisines(List.of(
                            plateau.getVilles().get("Le_Caire"),
                            plateau.getVilles().get("Bagdad"),
                            plateau.getVilles().get("Karachi")
                    ));
                    break;
                case "Teheran":
                    plateau.getVilles().get("Teheran").setVillesVoisines(List.of(
                            plateau.getVilles().get("Moscou"),
                            plateau.getVilles().get("Bagdad"),
                            plateau.getVilles().get("Karachi"),
                            plateau.getVilles().get("Delhi")
                    ));
                    break;
                case "Bangkok":
                    plateau.getVilles().get("Bangkok").setVillesVoisines(List.of(
                            plateau.getVilles().get("Calcutta"),
                            plateau.getVilles().get("Chennai"),
                            plateau.getVilles().get("Jakarta"),
                            plateau.getVilles().get("Ho_chi_minh_ville"),
                            plateau.getVilles().get("Hong_kong")
                    ));
                    break;
                case "Ho_chi_minh_ville":
                    plateau.getVilles().get("Ho_chi_minh_ville").setVillesVoisines(List.of(
                            plateau.getVilles().get("Hong_kong"),
                            plateau.getVilles().get("Manille"),
                            plateau.getVilles().get("Bangkok"),
                            plateau.getVilles().get("Jakarta")
                    ));
                    break;
                case "Jakarta":
                    plateau.getVilles().get("Jakarta").setVillesVoisines(List.of(
                            plateau.getVilles().get("Chennai"),
                            plateau.getVilles().get("Bangkok"),
                            plateau.getVilles().get("Ho_chi_minh_ville"),
                            plateau.getVilles().get("Sydney")
                    ));
                    break;
                case "Manille":
                    plateau.getVilles().get("Manille").setVillesVoisines(List.of(
                            plateau.getVilles().get("Taipei"),
                            plateau.getVilles().get("Hong_kong"),
                            plateau.getVilles().get("Ho_chi_minh_ville"),
                            plateau.getVilles().get("Sydney"),
                            plateau.getVilles().get("San_Francisco")
                    ));
                    break;
                case "Osaka":
                    plateau.getVilles().get("Osaka").setVillesVoisines(List.of(
                            plateau.getVilles().get("Taipei"),
                            plateau.getVilles().get("Tokyo")
                    ));
                    break;
                case "Pekin":
                    plateau.getVilles().get("Pekin").setVillesVoisines(List.of(
                            plateau.getVilles().get("Seoul"),
                            plateau.getVilles().get("Shangai")
                    ));
                    break;
                case "Seoul":
                    plateau.getVilles().get("Seoul").setVillesVoisines(List.of(
                            plateau.getVilles().get("Shangai"),
                            plateau.getVilles().get("Tokyo"),
                            plateau.getVilles().get("Pekin")
                    ));
                    break;
                case "Shangai":
                    plateau.getVilles().get("Shangai").setVillesVoisines(List.of(
                            plateau.getVilles().get("Pekin"),
                            plateau.getVilles().get("Seoul"),
                            plateau.getVilles().get("Tokyo"),
                            plateau.getVilles().get("Taipei"),
                            plateau.getVilles().get("Hong_kong")
                    ));
                    break;
                case "Sydney":
                    plateau.getVilles().get("Sydney").setVillesVoisines(List.of(
                            plateau.getVilles().get("Jakarta"),
                            plateau.getVilles().get("Manille"),
                            plateau.getVilles().get("Los_Angeles")
                    ));
                    break;
                case "Taipei":
                    plateau.getVilles().get("Taipei").setVillesVoisines(List.of(
                            plateau.getVilles().get("Osaka"),
                            plateau.getVilles().get("Manille"),
                            plateau.getVilles().get("Shangai"),
                            plateau.getVilles().get("Hong_kong")
                    ));
                    break;
                case "Tokyo":
                    plateau.getVilles().get("Tokyo").setVillesVoisines(List.of(
                            plateau.getVilles().get("Osaka"),
                            plateau.getVilles().get("Seoul"),
                            plateau.getVilles().get("Shangai"),
                            plateau.getVilles().get("San_Francisco")
                    ));
                    break;
            }
    }

    public void displaybullshit(){
        for (Ville ville : plateau.getVilles().values()){
            System.out.println(ville);
        }
    }

    public void jouerTour(Joueur joueur){
        joueur.setPlateau(plateau);
        Scanner sc = new Scanner(System.in);
        int nbAction = 0;
        while (nbAction < 4){
            System.out.println("Hello vous avez 4 actions possible");
            String choixAction = sc.nextLine();
            switch (choixAction){
                case "DEPLACEMENT":
                    System.out.println("Hello vous avez 4 deplacement possible");
                    String choixDeplacement = sc.nextLine();
                    System.out.println("Quelle ville ?");
//                            TODO : bcp trop de verif à faire
                    String choixVille = sc.nextLine();
                    nbAction++;
                    switch (choixDeplacement){
                        case "VOITURE":
                            joueur.setDeplacement(new DeplacementVoiture());

                            joueur.seDeplacer(choixVille);
                        case "NAVETTE":
                            joueur.setDeplacement(new DeplacementNavette());
                            joueur.seDeplacer(choixVille);
                        case "VOL CHARTER":
                            joueur.setDeplacement(new DeplacementVolCharter());
                            joueur.seDeplacer(choixVille);
                        case "VOL DIRECT":
                            joueur.setDeplacement(new DeplacementVolDirect());
                            joueur.seDeplacer(choixVille);

                    }
                case "STATION":

            }
        }

    }
}
