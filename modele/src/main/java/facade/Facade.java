package facade;

import exceptions.CasCouleurVilleIncorrectException;
import exceptions.VilleIntrouvableException;
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
import static modele.enums.DonneesVille.*;

import java.util.List;
import java.util.Scanner;

public class Facade {

    private static Plateau plateau;
    private final int NB_VILLES_BLEUES = 12;
    private final int NB_VILLES_JAUNES = 12;
    private final int NB_VILLES_NOIRES = 12;
    private final int NB_VILLES_ROUGES = 12;


    public void initialisation() throws CasCouleurVilleIncorrectException {
        plateau = new Plateau();
        initialisationVirus();
        initialisationVilles();
        plateau.initialiserCartesJoueur();
    }

    public void initialisationVirus(){
        for(Couleurs couleursVirus : Couleurs.values()){
            Virus virus = new Virus(couleursVirus);
            plateau.getListeVirus().add(virus);
        }
    }

    public void initialisationVilles() throws CasCouleurVilleIncorrectException {
        for(Virus virus : plateau.getListeVirus()){

            int POS_FIN_VILLES_BLEUES = NB_VILLES_BLEUES;
            int POS_FIN_VILLES_JAUNES = NB_VILLES_BLEUES + NB_VILLES_JAUNES;
            int POS_FIN_VILLES_NOIRES = NB_VILLES_BLEUES + NB_VILLES_JAUNES + NB_VILLES_NOIRES;
            int POS_FIN_VILLES_ROUGES = NB_VILLES_BLEUES + NB_VILLES_JAUNES + NB_VILLES_NOIRES + NB_VILLES_ROUGES;

            switch(virus.getVirusCouleur()){
                case BLEU:
                    for (int i = 0; i < POS_FIN_VILLES_BLEUES; i++) {
                        DonneesVille donneesVilleBleue = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleBleue.name(), donneesVilleBleue.getPopulationTotaleVille(), donneesVilleBleue.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleBleue.name(),ville);
                    }
                    break;
                case JAUNE:
                    for (int i = POS_FIN_VILLES_BLEUES; i < POS_FIN_VILLES_JAUNES; i++) {
                        DonneesVille donneesVilleJaune = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleJaune.name(), donneesVilleJaune.getPopulationTotaleVille(), donneesVilleJaune.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleJaune.name(),ville);
                    }
                    break;
                case NOIR:
                    for (int i = POS_FIN_VILLES_JAUNES; i < POS_FIN_VILLES_NOIRES; i++) {
                        DonneesVille donneesVilleNoire = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleNoire.name(), donneesVilleNoire.getPopulationTotaleVille(), donneesVilleNoire.getPopulationKmCarreVille(), virus);
                        plateau.getVilles().put(donneesVilleNoire.name(),ville);
                    }
                    break;
                case ROUGE:
                    for (int i = POS_FIN_VILLES_NOIRES; i < POS_FIN_VILLES_ROUGES; i++) {
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
            try {
                attributionVoisins(ville.getNomVille());
            } catch (VilleIntrouvableException e) {
                e.printStackTrace();
            }
        }
    }

    public void attributionVoisins(String ville) throws VilleIntrouvableException {
            switch (ville){
                case "Atlanta":
                    if(plateau.isVille(Chicago.name())
                            && plateau.isVille(Washington.name())
                            && plateau.isVille(Miami.name())) {
                        plateau.getVilles().get(Atlanta.name()).setVillesVoisines(List.of(Chicago.name(), Washington.name(), Miami.name()));
                    }
                    break;
                case "Chicago":
                    if(plateau.isVille(Atlanta.name())
                            && plateau.isVille(San_Francisco.name())
                            && plateau.isVille(Los_Angeles.name())
                            && plateau.isVille(Mexico.name())
                            && plateau.isVille(Montreal.name())) {
                        plateau.getVilles().get(Chicago.name()).setVillesVoisines(List.of(Atlanta.name(), San_Francisco.name(), Los_Angeles.name(), Mexico.name(), Montreal.name()));
                    }
                    break;
                case "Essen":
                    if(plateau.isVille(Londres.name())
                            && plateau.isVille(Paris.name())
                            && plateau.isVille(Milan.name())
                            && plateau.isVille(Saint_Petersbourg.name())) {
                        plateau.getVilles().get(Essen.name()).setVillesVoisines(List.of(Londres.name(), Paris.name(), Milan.name(), Saint_Petersbourg.name()));
                    }
                    break;
                case "Londres":
                    if(plateau.isVille(New_York.name())
                            && plateau.isVille(Madrid.name())
                            && plateau.isVille(Paris.name())
                            && plateau.isVille(Essen.name())) {
                        plateau.getVilles().get(Londres.name()).setVillesVoisines(List.of(New_York.name(), Madrid.name(), Paris.name(), Essen.name()));
                    }
                    break;
                case "Madrid":
                    if(plateau.isVille(New_York.name())
                            && plateau.isVille(Alger.name())
                            && plateau.isVille(Paris.name())
                            && plateau.isVille(Londres.name())
                            && plateau.isVille(Sao_Paulo.name())) {
                        plateau.getVilles().get(Madrid.name()).setVillesVoisines(List.of(New_York.name(), Alger.name(), Paris.name(), Londres.name(), Sao_Paulo.name()));
                    }
                    break;
                case "Milan":
                    if(plateau.isVille(Essen.name())
                            && plateau.isVille(Paris.name())
                            && plateau.isVille(Istanbul.name())) {
                        plateau.getVilles().get(Milan.name()).setVillesVoisines(List.of(Essen.name(), Paris.name(), Istanbul.name()));
                    }
                    break;
                case "Montreal":
                    if(plateau.isVille(New_York.name())
                            && plateau.isVille(Washington.name())
                            && plateau.isVille(Chicago.name())) {
                        plateau.getVilles().get(Montreal.name()).setVillesVoisines(List.of(New_York.name(), Washington.name(), Chicago.name()));
                    }
                    break;
                case "Paris":
                    if(plateau.isVille(Essen.name())
                            && plateau.isVille(Alger.name())
                            && plateau.isVille(Madrid.name())
                            && plateau.isVille(Londres.name())
                            && plateau.isVille(Milan.name())) {
                        plateau.getVilles().get(Paris.name()).setVillesVoisines(List.of(Essen.name(), Alger.name(), Madrid.name(), Londres.name(), Milan.name()));
                    }
                    break;
                case "Saint_Petersbourg":
                    if(plateau.isVille(Essen.name())
                            && plateau.isVille(Istanbul.name())
                            && plateau.isVille(Moscou.name())) {
                        plateau.getVilles().get(Saint_Petersbourg.name()).setVillesVoisines(List.of(Essen.name(), Istanbul.name(), Moscou.name()));
                    }
                    break;
                case "San_Francisco":
                    if(plateau.isVille(Los_Angeles.name())
                            && plateau.isVille(Chicago.name())
                            && plateau.isVille(Tokyo.name())
                            && plateau.isVille(Manille.name())) {
                        plateau.getVilles().get(San_Francisco.name()).setVillesVoisines(List.of(Los_Angeles.name(), Chicago.name(), Tokyo.name(), Manille.name()));
                    }
                    break;
                case "New_York":
                    if(plateau.isVille(Montreal.name())
                            && plateau.isVille(Washington.name())
                            && plateau.isVille(Madrid.name())
                            && plateau.isVille(Londres.name())) {
                        plateau.getVilles().get(New_York.name()).setVillesVoisines(List.of(Montreal.name(), Washington.name(), Madrid.name(), Londres.name()));
                    }
                    break;
                case "Washington":
                    if(plateau.isVille(New_York.name())
                            && plateau.isVille(Montreal.name())
                            && plateau.isVille(Atlanta.name())
                            && plateau.isVille(Miami.name())) {
                        plateau.getVilles().get(Washington.name()).setVillesVoisines(List.of(New_York.name(), Montreal.name(), Atlanta.name(), Miami.name()));
                    }
                    break;
                case "Bogota":
                    if(plateau.isVille(Miami.name())
                            && plateau.isVille(Mexico.name())
                            && plateau.isVille(Lima.name())
                            && plateau.isVille(Buenos_Aires.name())
                            && plateau.isVille(Sao_Paulo.name())) {
                        plateau.getVilles().get(Bogota.name()).setVillesVoisines(List.of(Miami.name(), Mexico.name(), Lima.name(), Buenos_Aires.name(), Sao_Paulo.name()));
                    }
                    break;
                case "Buenos_Aires":
                    if(plateau.isVille(Bogota.name())
                            && plateau.isVille(Sao_Paulo.name())) {
                        plateau.getVilles().get(Buenos_Aires.name()).setVillesVoisines(List.of(Bogota.name(), Sao_Paulo.name()));
                    }
                    break;
                case "Johannesburg":
                    if(plateau.isVille(Kinshasa.name())
                            && plateau.isVille(Khartoum.name())) {
                        plateau.getVilles().get(Johannesburg.name()).setVillesVoisines(List.of(Kinshasa.name(), Khartoum.name()));
                    }
                    break;
                case "Khartoum":
                    if(plateau.isVille(Kinshasa.name())
                            && plateau.isVille(Le_Caire.name())
                            && plateau.isVille(Lagos.name())
                            && plateau.isVille(Johannesburg.name())) {
                        plateau.getVilles().get(Khartoum.name()).setVillesVoisines(List.of(Kinshasa.name(), Le_Caire.name(), Lagos.name(), Johannesburg.name()));
                    }
                    break;
                case "Kinshasa":
                    if(plateau.isVille(Lagos.name())
                            && plateau.isVille(Khartoum.name())
                            && plateau.isVille(Johannesburg.name())) {
                        plateau.getVilles().get(Kinshasa.name()).setVillesVoisines(List.of(Lagos.name(), Khartoum.name(), Johannesburg.name()));
                    }
                    break;
                case "Lagos":
                    if(plateau.isVille(Sao_Paulo.name())
                            && plateau.isVille(Kinshasa.name())
                            && plateau.isVille(Khartoum.name())) {
                        plateau.getVilles().get(Lagos.name()).setVillesVoisines(List.of(Sao_Paulo.name(), Kinshasa.name(), Khartoum.name()));
                    }
                    break;
                case "Lima":
                    if(plateau.isVille(Mexico.name())
                            && plateau.isVille(Bogota.name())
                            && plateau.isVille(Santiago.name())) {
                        plateau.getVilles().get(Lima.name()).setVillesVoisines(List.of(Mexico.name(), Bogota.name(), Santiago.name()));
                    }
                    break;
                case "Los_Angeles":
                    if(plateau.isVille(Mexico.name())
                            && plateau.isVille(Chicago.name())
                            && plateau.isVille(San_Francisco.name())
                            && plateau.isVille(Sydney.name())) {
                        plateau.getVilles().get(Los_Angeles.name()).setVillesVoisines(List.of(Mexico.name(), Chicago.name(), San_Francisco.name(), Sydney.name()));
                    }
                    break;
                case "Mexico":
                    if(plateau.isVille(Los_Angeles.name())
                            && plateau.isVille(Chicago.name())
                            && plateau.isVille(Miami.name())
                            && plateau.isVille(Bogota.name())
                            && plateau.isVille(Lima.name())) {
                        plateau.getVilles().get(Mexico.name()).setVillesVoisines(List.of(Los_Angeles.name(), Chicago.name(), Miami.name(), Bogota.name(), Lima.name()));
                    }
                    break;
                case "Miami":
                    if(plateau.isVille(Washington.name())
                            && plateau.isVille(Atlanta.name())
                            && plateau.isVille(Mexico.name())
                            && plateau.isVille(Bogota.name())) {
                        plateau.getVilles().get(Miami.name()).setVillesVoisines(List.of(Washington.name(), Atlanta.name(), Mexico.name(), Bogota.name()));
                    }
                    break;
                case "Santiago":
                    if(plateau.isVille(Lima.name())) {
                        plateau.getVilles().get(Santiago.name()).setVillesVoisines(List.of(Lima.name()));
                    }
                    break;
                case "Sao_Paulo":
                    if(plateau.isVille(Madrid.name())
                            && plateau.isVille(Lagos.name())
                            && plateau.isVille(Bogota.name())
                            && plateau.isVille(Buenos_Aires.name())) {
                        plateau.getVilles().get(Sao_Paulo.name()).setVillesVoisines(List.of(Madrid.name(), Lagos.name(), Bogota.name(), Buenos_Aires.name()));
                    }
                    break;
                case "Alger":
                    if(plateau.isVille(Madrid.name())
                            && plateau.isVille(Paris.name())
                            && plateau.isVille(Istanbul.name())
                            && plateau.isVille(Le_Caire.name())) {
                        plateau.getVilles().get(Alger.name()).setVillesVoisines(List.of(Madrid.name(), Paris.name(), Istanbul.name(), Le_Caire.name()));
                    }
                    break;
                case "Bagdad":
                    if(plateau.isVille(Teheran.name())
                            && plateau.isVille(Istanbul.name())
                            && plateau.isVille(Le_Caire.name())
                            && plateau.isVille(Riyad.name())
                            && plateau.isVille(Karachi.name())) {
                        plateau.getVilles().get(Bagdad.name()).setVillesVoisines(List.of(Teheran.name(), Istanbul.name(), Le_Caire.name(), Riyad.name(), Karachi.name()));
                    }
                    break;
                case "Calcutta":
                    if(plateau.isVille(Hong_kong.name())
                            && plateau.isVille(Bangkok.name())
                            && plateau.isVille(Chennai.name())
                            && plateau.isVille(Delhi.name())) {
                        plateau.getVilles().get(Calcutta.name()).setVillesVoisines(List.of(Hong_kong.name(), Bangkok.name(), Chennai.name(), Delhi.name()));
                    }
                    break;
                case "Chennai":
                    if(plateau.isVille(Mumbai.name())
                            && plateau.isVille(Delhi.name())
                            && plateau.isVille(Calcutta.name())
                            && plateau.isVille(Bangkok.name())
                            && plateau.isVille(Jakarta.name())) {
                        plateau.getVilles().get(Chennai.name()).setVillesVoisines(List.of(Mumbai.name(), Delhi.name(), Calcutta.name(), Bangkok.name(), Jakarta.name()));
                    }
                    break;
                case "Delhi":
                    if(plateau.isVille(Teheran.name())
                            && plateau.isVille(Karachi.name())
                            && plateau.isVille(Mumbai.name())
                            && plateau.isVille(Chennai.name())
                            && plateau.isVille(Calcutta.name())) {
                        plateau.getVilles().get(Delhi.name()).setVillesVoisines(List.of(Teheran.name(), Karachi.name(), Mumbai.name(), Chennai.name(), Calcutta.name()));
                    }
                    break;
                case "Istanbul":
                    if(plateau.isVille(Milan.name())
                            && plateau.isVille(Saint_Petersbourg.name())
                            && plateau.isVille(Moscou.name())
                            && plateau.isVille(Bagdad.name())
                            && plateau.isVille(Le_Caire.name())
                            && plateau.isVille(Alger.name())) {
                        plateau.getVilles().get(Istanbul.name()).setVillesVoisines(List.of(Milan.name(), Saint_Petersbourg.name(), Moscou.name(), Bagdad.name(), Le_Caire.name(), Alger.name()));
                    }
                    break;
                case "Karachi":
                    if(plateau.isVille(Riyad.name())
                            && plateau.isVille(Mumbai.name())
                            && plateau.isVille(Delhi.name())
                            && plateau.isVille(Teheran.name())
                            && plateau.isVille(Bagdad.name())) {
                        plateau.getVilles().get(Karachi.name()).setVillesVoisines(List.of(Riyad.name(), Mumbai.name(), Delhi.name(), Teheran.name(), Bagdad.name()));
                    }
                    break;
                case "Le_Caire":
                    if(plateau.isVille(Alger.name())
                            && plateau.isVille(Istanbul.name())
                            && plateau.isVille(Bagdad.name())
                            && plateau.isVille(Riyad.name())) {
                        plateau.getVilles().get(Le_Caire.name()).setVillesVoisines(List.of(Alger.name(), Istanbul.name(), Bagdad.name(), Riyad.name()));
                    }
                    break;
                case "Moscou":
                    if (plateau.isVille(Saint_Petersbourg.name())
                            && plateau.isVille(Istanbul.name())
                            && plateau.isVille(Teheran.name())){
                        plateau.getVilles().get(Moscou.name()).setVillesVoisines(List.of(Saint_Petersbourg.name(), Istanbul.name(), Teheran.name()));
                    }
                    break;
                case "Mumbai":
                    if (plateau.isVille(Karachi.name())
                            && plateau.isVille(Delhi.name())
                            && plateau.isVille(Chennai.name())){
                        plateau.getVilles().get(Mumbai.name()).setVillesVoisines(List.of(Karachi.name(), Delhi.name(), Chennai.name()));
                    }
                    break;
                case "Riyad":
                    if (plateau.isVille(Le_Caire.name())
                            && plateau.isVille(Bagdad.name())
                            && plateau.isVille(Karachi.name())){
                        plateau.getVilles().get(Riyad.name()).setVillesVoisines(List.of(Le_Caire.name(), Bagdad.name(), Karachi.name()));
                    }
                    break;
                case "Teheran":
                    if (plateau.isVille(Moscou.name())
                            && plateau.isVille(Bagdad.name())
                            && plateau.isVille(Karachi.name())
                            && plateau.isVille(Delhi.name())){
                        plateau.getVilles().get(Teheran.name()).setVillesVoisines(List.of(Moscou.name(), Bagdad.name(), Karachi.name(), Delhi.name()));
                    }
                    break;
                case "Bangkok":
                    if (plateau.isVille(Calcutta.name())
                            && plateau.isVille(Chennai.name())
                            && plateau.isVille(Jakarta.name())
                            && plateau.isVille(Ho_chi_minh_ville.name())
                            && plateau.isVille(Hong_kong.name())){
                        plateau.getVilles().get(Bangkok.name()).setVillesVoisines(List.of(Calcutta.name(), Chennai.name(), Jakarta.name(), Ho_chi_minh_ville.name(), Hong_kong.name()));
                    }
                    break;
                case "Ho_chi_minh_ville":
                    if (plateau.isVille(Hong_kong.name())
                            && plateau.isVille(Manille.name())
                            && plateau.isVille(Bangkok.name())
                            && plateau.isVille(Jakarta.name())){
                        plateau.getVilles().get(Ho_chi_minh_ville.name()).setVillesVoisines(List.of(Hong_kong.name(), Manille.name(), Bangkok.name(), Jakarta.name()));
                    }
                    break;
                case "Jakarta":
                    if (plateau.isVille(Chennai.name())
                            && plateau.isVille(Bangkok.name())
                            && plateau.isVille(Ho_chi_minh_ville.name())
                            && plateau.isVille(Sydney.name())){
                        plateau.getVilles().get(Jakarta.name()).setVillesVoisines(List.of(Chennai.name(), Bangkok.name(), Ho_chi_minh_ville.name(), Sydney.name()));
                    }
                    break;
                case "Manille":
                    if (plateau.isVille(Taipei.name())
                            && plateau.isVille(Hong_kong.name())
                            && plateau.isVille(Ho_chi_minh_ville.name())
                            && plateau.isVille(Sydney.name())
                            && plateau.isVille(San_Francisco.name())){
                        plateau.getVilles().get(Manille.name()).setVillesVoisines(List.of(Taipei.name(), Hong_kong.name(), Ho_chi_minh_ville.name(), Sydney.name(),San_Francisco.name()));
                    }
                    break;
                case "Osaka":
                    if (plateau.isVille(Taipei.name())
                            && plateau.isVille(Tokyo.name())){
                        plateau.getVilles().get(Osaka.name()).setVillesVoisines(List.of(Taipei.name(), Tokyo.name()));
                    }
                    break;
                case "Pekin":
                    if (plateau.isVille(Seoul.name())
                            && plateau.isVille(Shangai.name())){
                        plateau.getVilles().get(Pekin.name()).setVillesVoisines(List.of(Seoul.name(), Shangai.name()));
                    }
                    break;
                case "Seoul":

                    if (plateau.isVille(Shangai.name())
                            && plateau.isVille(Tokyo.name())
                            && plateau.isVille(Pekin.name())) {
                        plateau.getVilles().get(Seoul.name()).setVillesVoisines(List.of(Shangai.name(), Tokyo.name(), Pekin.name()));
                    }
                    break;
                case "Shangai":

                    if (plateau.isVille(Pekin.name())
                            && plateau.isVille(Seoul.name())
                            && plateau.isVille(Tokyo.name())
                            && plateau.isVille(Taipei.name())
                            && plateau.isVille(Hong_kong.name())) {
                        plateau.getVilles().get(Shangai.name()).setVillesVoisines(List.of(Pekin.name(), Seoul.name(), Tokyo.name(), Taipei.name(), Hong_kong.name()));
                    }
                    break;
                case "Sydney":
                    if (plateau.isVille(Sydney.name())
                            && plateau.isVille(Jakarta.name())
                            && plateau.isVille(Manille.name())
                            && plateau.isVille(Los_Angeles.name())) {
                        plateau.getVilles().get(Sydney.name()).setVillesVoisines(List.of(Sydney.name(), Jakarta.name(), Manille.name(), Los_Angeles.name()));
                    }
                    break;
                case "Hong_kong":
                    if (plateau.isVille(Shangai.name())
                            && plateau.isVille(Taipei.name())
                            && plateau.isVille(Manille.name())
                            && plateau.isVille(Ho_chi_minh_ville.name())
                            && plateau.isVille(Bangkok.name())
                            && plateau.isVille(Calcutta.name())) {
                        plateau.getVilles().get(Hong_kong.name()).setVillesVoisines(List.of(Shangai.name(), Taipei.name(), Manille.name(), Ho_chi_minh_ville.name(),Bangkok.name(), Calcutta.name()));
                    }
                    break;
                case "Taipei":
                    if (plateau.isVille(Osaka.name())
                            && plateau.isVille(Manille.name())
                            && plateau.isVille(Shangai.name())
                            && plateau.isVille(Hong_kong.name())) {
                        plateau.getVilles().get(Taipei.name()).setVillesVoisines(List.of(Osaka.name(), Manille.name(), Shangai.name(), Hong_kong.name()));
                    }
                    break;
                case "Tokyo":
                    if (plateau.isVille(Osaka.name())
                        && plateau.isVille(Seoul.name())
                        && plateau.isVille(Shangai.name())
                        && plateau.isVille(San_Francisco.name())) {
                    plateau.getVilles().get(Tokyo.name()).setVillesVoisines(List.of(Osaka.name(), Seoul.name(), Shangai.name(), San_Francisco.name()));
                    }
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
