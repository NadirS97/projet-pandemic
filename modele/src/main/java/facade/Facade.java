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
import static modele.enums.DonneesVille.*;

public class Facade {

    private static Plateau plateau;
    private final int NBVILLESBLEUES = 12;
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
                    for (int i = 0; i < NBVILLESBLEUES; i++) {
                        DonneesVille donneesVilleBleue = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleBleue.name(), donneesVilleBleue.getPopulationTotaleVille(), donneesVilleBleue.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleBleue.name(),ville);
                    }
                    break;
                case JAUNE:
                    for (int i = NBVILLESBLEUES; i < NBVILLESBLEUES+NBVILLESJAUNES; i++) {
                        DonneesVille donneesVilleJaune = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleJaune.name(), donneesVilleJaune.getPopulationTotaleVille(), donneesVilleJaune.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleJaune.name(),ville);
                    }
                    break;
                case NOIR:
                    for (int i = NBVILLESJAUNES; i < NBVILLESBLEUES+NBVILLESJAUNES+NBVILLESNOIRES; i++) {
                        DonneesVille donneesVilleNoire = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleNoire.name(), donneesVilleNoire.getPopulationTotaleVille(), donneesVilleNoire.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleNoire.name(),ville);
                    }
                    break;
                case ROUGE:
                    for (int i = NBVILLESNOIRES; i < NBVILLESBLEUES+NBVILLESJAUNES+NBVILLESNOIRES+NBVILLESROUGES; i++) {
                        DonneesVille donneesVilleRouge = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleRouge.name(), donneesVilleRouge.getPopulationTotaleVille(), donneesVilleRouge.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleRouge.name(),ville);
                    }
                    break;
                default:
                    throw new CasCouleurVilleIncorrectException();
            }
        }
        attributionVoisins("Delhi");
        for(Ville ville : plateau.getVilles().values()){
//            attributionVoisins(ville.getNomVille());
        }
    }

    public void attributionVoisins(String ville){
            switch (ville){
                case "Atlanta":
                    if(plateau.isVille("Chicago")
                            && plateau.isVille("Washington")
                            && plateau.isVille("Miami")) {
                        plateau.getVilles().get("Atlanta").setVillesVoisines(List.of("Chicago", "Washington", "Miami"));
                    }
                    break;
                case "Chicago":
                    if(plateau.isVille("Atlanta")
                            && plateau.isVille("San_Francisco")
                            && plateau.isVille("Los_Angeles")
                            && plateau.isVille("Mexico")
                            && plateau.isVille("Montreal")) {
                        plateau.getVilles().get("Chicago").setVillesVoisines(List.of("Atlanta", "San_Francisco", "Los_Angeles", "Mexico", "Montreal"));
                    }
                    break;
                case "Essen":
                    if(plateau.isVille("Londres")
                            && plateau.isVille("Paris")
                            && plateau.isVille("Milan")
                            && plateau.isVille("Saint_Petersbourg")) {
                        plateau.getVilles().get("Essen").setVillesVoisines(List.of("Londres", "Paris", "Milan", "Saint_Petersbourg"));
                    }
                    break;
                case "Londres":
                    if(plateau.isVille("New_York")
                            && plateau.isVille("Madrid")
                            && plateau.isVille("Paris")
                            && plateau.isVille("Essen")) {
                        plateau.getVilles().get("Londres").setVillesVoisines(List.of("New_York", "Madrid", "Paris", "Essen"));
                    }
                    break;
                case "Madrid":
                    if(plateau.isVille("New_York")
                            && plateau.isVille("Alger")
                            && plateau.isVille("Paris")
                            && plateau.isVille("Londres")
                            && plateau.isVille("Sao_Paulo")) {
                        plateau.getVilles().get("Madrid").setVillesVoisines(List.of("New_York", "Alger", "Paris", "Londres", "Sao_Paulo"));
                    }
                    break;
                case "Milan":
                    if(plateau.isVille("Essen")
                            && plateau.isVille("Paris")
                            && plateau.isVille("Istanbul")) {
                        plateau.getVilles().get("Milan").setVillesVoisines(List.of("Essen", "Paris", "Istanbul"));
                    }
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
                    if (plateau.isVille(Saint_Petersbourg.name())
                            && plateau.isVille(Istanbul.name())
                            && plateau.isVille(Teheran.name()))
                    {
                        plateau.getVilles().get(Tokyo.name()).setVillesVoisines(List.of(Saint_Petersbourg.name(), Istanbul.name(), Teheran.name()));
                    }break;

                case "Mumbai":
                    if (plateau.isVille(Karachi.name())
                            && plateau.isVille(Delhi.name())
                            && plateau.isVille(Chennai.name()))
                    {
                        plateau.getVilles().get(Mumbai.name()).setVillesVoisines(List.of(Karachi.name(), Delhi.name(), Chennai.name()));
                    }break;

                case "Riyad":

                    if (plateau.isVille(Le_Caire.name())
                            && plateau.isVille(Bagdad.name())
                            && plateau.isVille(Karachi.name()))
                    {
                        plateau.getVilles().get(Riyad.name()).setVillesVoisines(List.of(Le_Caire.name(), Bagdad.name(), Karachi.name()));
                    }break;

                case "Teheran":

                    if (plateau.isVille(Moscou.name())
                            && plateau.isVille(Bagdad.name())
                            && plateau.isVille(Karachi.name())
                            && plateau.isVille(Delhi.name()))
                    {
                        plateau.getVilles().get(Teheran.name()).setVillesVoisines(List.of(Moscou.name(), Bagdad.name(), Karachi.name(), Delhi.name()));
                    }break;
                case "Bangkok":

                    if (plateau.isVille(Calcutta.name())
                            && plateau.isVille(Chennai.name())
                            && plateau.isVille(Jakarta.name())
                            && plateau.isVille(Ho_chi_minh_ville.name())
                            && plateau.isVille(Hong_kong.name()))
                    {
                        plateau.getVilles().get(Bangkok.name()).setVillesVoisines(List.of(Calcutta.name(), Chennai.name(), Jakarta.name(), Ho_chi_minh_ville.name(), Hong_kong.name()));
                    }break;

                case "Ho_chi_minh_ville":
                    if (plateau.isVille(Hong_kong.name())
                            && plateau.isVille(Manille.name())
                            && plateau.isVille(Bangkok.name())
                            && plateau.isVille(Jakarta.name()))
                    {
                        plateau.getVilles().get(Ho_chi_minh_ville.name()).setVillesVoisines(List.of(Hong_kong.name(), Manille.name(), Bangkok.name(), Jakarta.name()));
                    }break;


                case "Jakarta":
                    if (plateau.isVille(Chennai.name())
                            && plateau.isVille(Bangkok.name())
                            && plateau.isVille(Ho_chi_minh_ville.name())
                            && plateau.isVille(Sydney.name()))
                    {
                        plateau.getVilles().get(Jakarta.name()).setVillesVoisines(List.of(Chennai.name(), Bangkok.name(), Ho_chi_minh_ville.name(), Sydney.name()));
                    }break;

                case "Manille":
                    if (plateau.isVille(Taipei.name())
                            && plateau.isVille(Hong_kong.name())
                            && plateau.isVille(Ho_chi_minh_ville.name())
                            && plateau.isVille(Sydney.name())
                            && plateau.isVille(San_Francisco.name()))
                    {
                        plateau.getVilles().get(Manille.name()).setVillesVoisines(List.of(Taipei.name(), Hong_kong.name(), Ho_chi_minh_ville.name(), Sydney.name(),San_Francisco.name()));
                    }break;

                case "Osaka":
                    if (plateau.isVille(Taipei.name())
                            && plateau.isVille(Tokyo.name()))
                    {
                        plateau.getVilles().get(Osaka.name()).setVillesVoisines(List.of(Taipei.name(), Tokyo.name()));
                    }break;

                case "Pekin":
                    if (plateau.isVille(Seoul.name())
                            && plateau.isVille(Shangai.name()))

                    {
                        plateau.getVilles().get(Pekin.name()).setVillesVoisines(List.of(Seoul.name(), Shangai.name()));
                    }break;

                case "Seoul":

                    if (plateau.isVille(Shangai.name())
                            && plateau.isVille(Tokyo.name())
                            && plateau.isVille(Pekin.name()))

                    {
                        plateau.getVilles().get(Seoul.name()).setVillesVoisines(List.of(Shangai.name(), Tokyo.name(), Pekin.name()));
                    }break;


                case "Shangai":

                    if (plateau.isVille(Pekin.name())
                            && plateau.isVille(Seoul.name())
                            && plateau.isVille(Tokyo.name())
                            && plateau.isVille(Taipei.name())
                            && plateau.isVille(Hong_kong.name()))
                    {
                        plateau.getVilles().get(Shangai.name()).setVillesVoisines(List.of(Pekin.name(), Seoul.name(), Tokyo.name(), Taipei.name(), Hong_kong.name()));
                    }break;


                case "Sydney":

                    if (plateau.isVille(Sydney.name())
                            && plateau.isVille(Jakarta.name())
                            && plateau.isVille(Manille.name())
                            && plateau.isVille(Los_Angeles.name()))
                    {
                        plateau.getVilles().get(Sydney.name()).setVillesVoisines(List.of(Sydney.name(), Jakarta.name(), Manille.name(), Los_Angeles.name()));
                    }break;


                case "Taipei":
                    if (plateau.isVille(Osaka.name())
                            && plateau.isVille(Manille.name())
                            && plateau.isVille(Shangai.name())
                            && plateau.isVille(Hong_kong.name()))
                    {
                        plateau.getVilles().get(Taipei.name()).setVillesVoisines(List.of(Osaka.name(), Manille.name(), Shangai.name(), Hong_kong.name()));
                    }break;

                case "Tokyo":
                    if (plateau.isVille(Osaka.name())
                        && plateau.isVille(Seoul.name())
                        && plateau.isVille(Shangai.name())
                        && plateau.isVille(San_Francisco.name()))
                {
                    plateau.getVilles().get(Tokyo.name()).setVillesVoisines(List.of(Osaka.name(), Seoul.name(), Shangai.name(), San_Francisco.name()));
                }break;
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
