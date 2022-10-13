package modele;

import exceptions.CasCouleurVilleIncorrectException;
import exceptions.VilleIntrouvableException;
import lombok.Getter;
import modele.cartes.CarteEvenement;
import modele.cartes.CarteJoueur;
import modele.cartes.CartePropagation;
import modele.cartes.CarteVille;
import modele.enums.Couleurs;
import modele.enums.DonneesVille;
import modele.enums.NomsEvenement;

import java.util.*;

import static modele.enums.DonneesVille.*;
import static modele.enums.DonneesVille.San_Francisco;

@Getter
public class Plateau{

    private List<Virus> listeVirus;
    private Map<String,Ville> villes;
    private int marqueurVitessePropagation;   // entre 1et 3 = vitesse2 , 4,5 = vitesse3 , 6,7 vitesse 4 , pas vraiment besoin d'un tableau ?
    private int marqueurVitesseEclosion;
    private List<CarteJoueur> piocheCarteJoueur;
    private List<CarteJoueur> defausseCarteJoueur;
    private List<CartePropagation> piocheCartePropagation;
    private List<CartePropagation> defausseCartePropagation;


//    Pour l'initialisation des villes
    private final int NB_VILLES_BLEUES = 12;
    private final int NB_VILLES_JAUNES = 12;
    private final int NB_VILLES_NOIRES = 12;
    private final int NB_VILLES_ROUGES = 12;

    public Plateau() throws CasCouleurVilleIncorrectException {
        listeVirus = new ArrayList<>();
        villes = new HashMap<>();
        marqueurVitessePropagation = 0;
        marqueurVitesseEclosion = 0;
        piocheCarteJoueur = new ArrayList<>();
        defausseCarteJoueur = new ArrayList<>();
        piocheCartePropagation = new ArrayList<>();
        defausseCartePropagation = new ArrayList<>();

        initialisationVirus();
        initialisationVilles();
        initialiserCartesJoueur();
    }

    Ville getVilleByName(String name){
        return villes.get(name);
    }



    public void initialiserCartesJoueur(){
        for (Ville ville : this.getVilles().values()){
            piocheCarteJoueur.add(new CarteVille(ville));
        }
        for (NomsEvenement nomsEvenement : NomsEvenement.values()){
            piocheCarteJoueur.add(new CarteEvenement(nomsEvenement));
        }
        melangerPaquet(piocheCarteJoueur);
        System.out.println(piocheCarteJoueur);

    }



    public List melangerPaquet(List paquet){
        Collections.shuffle(paquet);
        return paquet;
    }


    public Boolean isVille(String nomVille) throws VilleIntrouvableException {
        return villes.containsKey(nomVille);
    }



    public void initialisationVirus(){
        for(Couleurs couleursVirus : Couleurs.values()){
            Virus virus = new Virus(couleursVirus);
            getListeVirus().add(virus);
        }
    }

    public void initialisationVilles() throws CasCouleurVilleIncorrectException {
        for(Virus virus : getListeVirus()){

            int POS_FIN_VILLES_BLEUES = NB_VILLES_BLEUES;
            int POS_FIN_VILLES_JAUNES = NB_VILLES_BLEUES + NB_VILLES_JAUNES;
            int POS_FIN_VILLES_NOIRES = NB_VILLES_BLEUES + NB_VILLES_JAUNES + NB_VILLES_NOIRES;
            int POS_FIN_VILLES_ROUGES = NB_VILLES_BLEUES + NB_VILLES_JAUNES + NB_VILLES_NOIRES + NB_VILLES_ROUGES;

            switch(virus.getVirusCouleur()){
                case BLEU:
                    for (int i = 0; i < POS_FIN_VILLES_BLEUES; i++) {
                        DonneesVille donneesVilleBleue = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleBleue.name(), donneesVilleBleue.getPopulationTotaleVille(), donneesVilleBleue.getPopulationKmCarreVille(), virus);
                        getVilles().put(donneesVilleBleue.name(),ville);
                    }
                    break;
                case JAUNE:
                    for (int i = POS_FIN_VILLES_BLEUES; i < POS_FIN_VILLES_JAUNES; i++) {
                        DonneesVille donneesVilleJaune = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleJaune.name(), donneesVilleJaune.getPopulationTotaleVille(), donneesVilleJaune.getPopulationKmCarreVille(), virus);
                        getVilles().put(donneesVilleJaune.name(),ville);
                    }
                    break;
                case NOIR:
                    for (int i = POS_FIN_VILLES_JAUNES; i < POS_FIN_VILLES_NOIRES; i++) {
                        DonneesVille donneesVilleNoire = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleNoire.name(), donneesVilleNoire.getPopulationTotaleVille(), donneesVilleNoire.getPopulationKmCarreVille(), virus);
                        getVilles().put(donneesVilleNoire.name(),ville);
                    }
                    break;
                case ROUGE:
                    for (int i = POS_FIN_VILLES_NOIRES; i < POS_FIN_VILLES_ROUGES; i++) {
                        DonneesVille donneesVilleRouge = DonneesVille.values()[i];
                        Ville ville = new Ville(donneesVilleRouge.name(), donneesVilleRouge.getPopulationTotaleVille(), donneesVilleRouge.getPopulationKmCarreVille(), virus);
                        getVilles().put(donneesVilleRouge.name(),ville);
                    }
                    break;
                default:
                    throw new CasCouleurVilleIncorrectException();
            }
        }
        for(Ville ville : getVilles().values()){
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
                if(isVille(Chicago.name())
                        && isVille(Washington.name())
                        && isVille(Miami.name())) {
                    getVilles().get(Atlanta.name()).setVillesVoisines(List.of(Chicago.name(), Washington.name(), Miami.name()));
                }
                break;
            case "Chicago":
                if(isVille(Atlanta.name())
                        && isVille(San_Francisco.name())
                        && isVille(Los_Angeles.name())
                        && isVille(Mexico.name())
                        && isVille(Montreal.name())) {
                    getVilles().get(Chicago.name()).setVillesVoisines(List.of(Atlanta.name(), San_Francisco.name(), Los_Angeles.name(), Mexico.name(), Montreal.name()));
                }
                break;
            case "Essen":
                if(isVille(Londres.name())
                        && isVille(Paris.name())
                        && isVille(Milan.name())
                        && isVille(Saint_Petersbourg.name())) {
                    getVilles().get(Essen.name()).setVillesVoisines(List.of(Londres.name(), Paris.name(), Milan.name(), Saint_Petersbourg.name()));
                }
                break;
            case "Londres":
                if(isVille(New_York.name())
                        && isVille(Madrid.name())
                        && isVille(Paris.name())
                        && isVille(Essen.name())) {
                    getVilles().get(Londres.name()).setVillesVoisines(List.of(New_York.name(), Madrid.name(), Paris.name(), Essen.name()));
                }
                break;
            case "Madrid":
                if(isVille(New_York.name())
                        && isVille(Alger.name())
                        && isVille(Paris.name())
                        && isVille(Londres.name())
                        && isVille(Sao_Paulo.name())) {
                    getVilles().get(Madrid.name()).setVillesVoisines(List.of(New_York.name(), Alger.name(), Paris.name(), Londres.name(), Sao_Paulo.name()));
                }
                break;
            case "Milan":
                if(isVille(Essen.name())
                        && isVille(Paris.name())
                        && isVille(Istanbul.name())) {
                    getVilles().get(Milan.name()).setVillesVoisines(List.of(Essen.name(), Paris.name(), Istanbul.name()));
                }
                break;
            case "Montreal":
                if(isVille(New_York.name())
                        && isVille(Washington.name())
                        && isVille(Chicago.name())) {
                    getVilles().get(Montreal.name()).setVillesVoisines(List.of(New_York.name(), Washington.name(), Chicago.name()));
                }
                break;
            case "Paris":
                if(isVille(Essen.name())
                        && isVille(Alger.name())
                        && isVille(Madrid.name())
                        && isVille(Londres.name())
                        && isVille(Milan.name())) {
                    getVilles().get(Paris.name()).setVillesVoisines(List.of(Essen.name(), Alger.name(), Madrid.name(), Londres.name(), Milan.name()));
                }
                break;
            case "Saint_Petersbourg":
                if(isVille(Essen.name())
                        && isVille(Istanbul.name())
                        && isVille(Moscou.name())) {
                    getVilles().get(Saint_Petersbourg.name()).setVillesVoisines(List.of(Essen.name(), Istanbul.name(), Moscou.name()));
                }
                break;
            case "San_Francisco":
                if(isVille(Los_Angeles.name())
                        && isVille(Chicago.name())
                        && isVille(Tokyo.name())
                        && isVille(Manille.name())) {
                    getVilles().get(San_Francisco.name()).setVillesVoisines(List.of(Los_Angeles.name(), Chicago.name(), Tokyo.name(), Manille.name()));
                }
                break;
            case "New_York":
                if(isVille(Montreal.name())
                        && isVille(Washington.name())
                        && isVille(Madrid.name())
                        && isVille(Londres.name())) {
                    getVilles().get(New_York.name()).setVillesVoisines(List.of(Montreal.name(), Washington.name(), Madrid.name(), Londres.name()));
                }
                break;
            case "Washington":
                if(isVille(New_York.name())
                        && isVille(Montreal.name())
                        && isVille(Atlanta.name())
                        && isVille(Miami.name())) {
                    getVilles().get(Washington.name()).setVillesVoisines(List.of(New_York.name(), Montreal.name(), Atlanta.name(), Miami.name()));
                }
                break;
            case "Bogota":
                if(isVille(Miami.name())
                        && isVille(Mexico.name())
                        && isVille(Lima.name())
                        && isVille(Buenos_Aires.name())
                        && isVille(Sao_Paulo.name())) {
                    getVilles().get(Bogota.name()).setVillesVoisines(List.of(Miami.name(), Mexico.name(), Lima.name(), Buenos_Aires.name(), Sao_Paulo.name()));
                }
                break;
            case "Buenos_Aires":
                if(isVille(Bogota.name())
                        && isVille(Sao_Paulo.name())) {
                    getVilles().get(Buenos_Aires.name()).setVillesVoisines(List.of(Bogota.name(), Sao_Paulo.name()));
                }
                break;
            case "Johannesburg":
                if(isVille(Kinshasa.name())
                        && isVille(Khartoum.name())) {
                    getVilles().get(Johannesburg.name()).setVillesVoisines(List.of(Kinshasa.name(), Khartoum.name()));
                }
                break;
            case "Khartoum":
                if(isVille(Kinshasa.name())
                        && isVille(Le_Caire.name())
                        && isVille(Lagos.name())
                        && isVille(Johannesburg.name())) {
                    getVilles().get(Khartoum.name()).setVillesVoisines(List.of(Kinshasa.name(), Le_Caire.name(), Lagos.name(), Johannesburg.name()));
                }
                break;
            case "Kinshasa":
                if(isVille(Lagos.name())
                        && isVille(Khartoum.name())
                        && isVille(Johannesburg.name())) {
                    getVilles().get(Kinshasa.name()).setVillesVoisines(List.of(Lagos.name(), Khartoum.name(), Johannesburg.name()));
                }
                break;
            case "Lagos":
                if(isVille(Sao_Paulo.name())
                        && isVille(Kinshasa.name())
                        && isVille(Khartoum.name())) {
                    getVilles().get(Lagos.name()).setVillesVoisines(List.of(Sao_Paulo.name(), Kinshasa.name(), Khartoum.name()));
                }
                break;
            case "Lima":
                if(isVille(Mexico.name())
                        && isVille(Bogota.name())
                        && isVille(Santiago.name())) {
                    getVilles().get(Lima.name()).setVillesVoisines(List.of(Mexico.name(), Bogota.name(), Santiago.name()));
                }
                break;
            case "Los_Angeles":
                if(isVille(Mexico.name())
                        && isVille(Chicago.name())
                        && isVille(San_Francisco.name())
                        && isVille(Sydney.name())) {
                    getVilles().get(Los_Angeles.name()).setVillesVoisines(List.of(Mexico.name(), Chicago.name(), San_Francisco.name(), Sydney.name()));
                }
                break;
            case "Mexico":
                if(isVille(Los_Angeles.name())
                        && isVille(Chicago.name())
                        && isVille(Miami.name())
                        && isVille(Bogota.name())
                        && isVille(Lima.name())) {
                    getVilles().get(Mexico.name()).setVillesVoisines(List.of(Los_Angeles.name(), Chicago.name(), Miami.name(), Bogota.name(), Lima.name()));
                }
                break;
            case "Miami":
                if(isVille(Washington.name())
                        && isVille(Atlanta.name())
                        && isVille(Mexico.name())
                        && isVille(Bogota.name())) {
                    getVilles().get(Miami.name()).setVillesVoisines(List.of(Washington.name(), Atlanta.name(), Mexico.name(), Bogota.name()));
                }
                break;
            case "Santiago":
                if(isVille(Lima.name())) {
                    getVilles().get(Santiago.name()).setVillesVoisines(List.of(Lima.name()));
                }
                break;
            case "Sao_Paulo":
                if(isVille(Madrid.name())
                        && isVille(Lagos.name())
                        && isVille(Bogota.name())
                        && isVille(Buenos_Aires.name())) {
                    getVilles().get(Sao_Paulo.name()).setVillesVoisines(List.of(Madrid.name(), Lagos.name(), Bogota.name(), Buenos_Aires.name()));
                }
                break;
            case "Alger":
                if(isVille(Madrid.name())
                        && isVille(Paris.name())
                        && isVille(Istanbul.name())
                        && isVille(Le_Caire.name())) {
                    getVilles().get(Alger.name()).setVillesVoisines(List.of(Madrid.name(), Paris.name(), Istanbul.name(), Le_Caire.name()));
                }
                break;
            case "Bagdad":
                if(isVille(Teheran.name())
                        && isVille(Istanbul.name())
                        && isVille(Le_Caire.name())
                        && isVille(Riyad.name())
                        && isVille(Karachi.name())) {
                    getVilles().get(Bagdad.name()).setVillesVoisines(List.of(Teheran.name(), Istanbul.name(), Le_Caire.name(), Riyad.name(), Karachi.name()));
                }
                break;
            case "Calcutta":
                if(isVille(Hong_kong.name())
                        && isVille(Bangkok.name())
                        && isVille(Chennai.name())
                        && isVille(Delhi.name())) {
                    getVilles().get(Calcutta.name()).setVillesVoisines(List.of(Hong_kong.name(), Bangkok.name(), Chennai.name(), Delhi.name()));
                }
                break;
            case "Chennai":
                if(isVille(Mumbai.name())
                        && isVille(Delhi.name())
                        && isVille(Calcutta.name())
                        && isVille(Bangkok.name())
                        && isVille(Jakarta.name())) {
                    getVilles().get(Chennai.name()).setVillesVoisines(List.of(Mumbai.name(), Delhi.name(), Calcutta.name(), Bangkok.name(), Jakarta.name()));
                }
                break;
            case "Delhi":
                if(isVille(Teheran.name())
                        && isVille(Karachi.name())
                        && isVille(Mumbai.name())
                        && isVille(Chennai.name())
                        && isVille(Calcutta.name())) {
                    getVilles().get(Delhi.name()).setVillesVoisines(List.of(Teheran.name(), Karachi.name(), Mumbai.name(), Chennai.name(), Calcutta.name()));
                }
                break;
            case "Istanbul":
                if(isVille(Milan.name())
                        && isVille(Saint_Petersbourg.name())
                        && isVille(Moscou.name())
                        && isVille(Bagdad.name())
                        && isVille(Le_Caire.name())
                        && isVille(Alger.name())) {
                    getVilles().get(Istanbul.name()).setVillesVoisines(List.of(Milan.name(), Saint_Petersbourg.name(), Moscou.name(), Bagdad.name(), Le_Caire.name(), Alger.name()));
                }
                break;
            case "Karachi":
                if(isVille(Riyad.name())
                        && isVille(Mumbai.name())
                        && isVille(Delhi.name())
                        && isVille(Teheran.name())
                        && isVille(Bagdad.name())) {
                    getVilles().get(Karachi.name()).setVillesVoisines(List.of(Riyad.name(), Mumbai.name(), Delhi.name(), Teheran.name(), Bagdad.name()));
                }
                break;
            case "Le_Caire":
                if(isVille(Alger.name())
                        && isVille(Istanbul.name())
                        && isVille(Bagdad.name())
                        && isVille(Riyad.name())) {
                    getVilles().get(Le_Caire.name()).setVillesVoisines(List.of(Alger.name(), Istanbul.name(), Bagdad.name(), Riyad.name()));
                }
                break;
            case "Moscou":
                if (isVille(Saint_Petersbourg.name())
                        && isVille(Istanbul.name())
                        && isVille(Teheran.name())){
                    getVilles().get(Moscou.name()).setVillesVoisines(List.of(Saint_Petersbourg.name(), Istanbul.name(), Teheran.name()));
                }
                break;
            case "Mumbai":
                if (isVille(Karachi.name())
                        && isVille(Delhi.name())
                        && isVille(Chennai.name())){
                    getVilles().get(Mumbai.name()).setVillesVoisines(List.of(Karachi.name(), Delhi.name(), Chennai.name()));
                }
                break;
            case "Riyad":
                if (isVille(Le_Caire.name())
                        && isVille(Bagdad.name())
                        && isVille(Karachi.name())){
                    getVilles().get(Riyad.name()).setVillesVoisines(List.of(Le_Caire.name(), Bagdad.name(), Karachi.name()));
                }
                break;
            case "Teheran":
                if (isVille(Moscou.name())
                        && isVille(Bagdad.name())
                        && isVille(Karachi.name())
                        && isVille(Delhi.name())){
                    getVilles().get(Teheran.name()).setVillesVoisines(List.of(Moscou.name(), Bagdad.name(), Karachi.name(), Delhi.name()));
                }
                break;
            case "Bangkok":
                if (isVille(Calcutta.name())
                        && isVille(Chennai.name())
                        && isVille(Jakarta.name())
                        && isVille(Ho_chi_minh_ville.name())
                        && isVille(Hong_kong.name())){
                    getVilles().get(Bangkok.name()).setVillesVoisines(List.of(Calcutta.name(), Chennai.name(), Jakarta.name(), Ho_chi_minh_ville.name(), Hong_kong.name()));
                }
                break;
            case "Ho_chi_minh_ville":
                if (isVille(Hong_kong.name())
                        && isVille(Manille.name())
                        && isVille(Bangkok.name())
                        && isVille(Jakarta.name())){
                    getVilles().get(Ho_chi_minh_ville.name()).setVillesVoisines(List.of(Hong_kong.name(), Manille.name(), Bangkok.name(), Jakarta.name()));
                }
                break;
            case "Jakarta":
                if (isVille(Chennai.name())
                        && isVille(Bangkok.name())
                        && isVille(Ho_chi_minh_ville.name())
                        && isVille(Sydney.name())){
                    getVilles().get(Jakarta.name()).setVillesVoisines(List.of(Chennai.name(), Bangkok.name(), Ho_chi_minh_ville.name(), Sydney.name()));
                }
                break;
            case "Manille":
                if (isVille(Taipei.name())
                        && isVille(Hong_kong.name())
                        && isVille(Ho_chi_minh_ville.name())
                        && isVille(Sydney.name())
                        && isVille(San_Francisco.name())){
                    getVilles().get(Manille.name()).setVillesVoisines(List.of(Taipei.name(), Hong_kong.name(), Ho_chi_minh_ville.name(), Sydney.name(),San_Francisco.name()));
                }
                break;
            case "Osaka":
                if (isVille(Taipei.name())
                        && isVille(Tokyo.name())){
                    getVilles().get(Osaka.name()).setVillesVoisines(List.of(Taipei.name(), Tokyo.name()));
                }
                break;
            case "Pekin":
                if (isVille(Seoul.name())
                        && isVille(Shangai.name())){
                    getVilles().get(Pekin.name()).setVillesVoisines(List.of(Seoul.name(), Shangai.name()));
                }
                break;
            case "Seoul":

                if (isVille(Shangai.name())
                        && isVille(Tokyo.name())
                        && isVille(Pekin.name())) {
                    getVilles().get(Seoul.name()).setVillesVoisines(List.of(Shangai.name(), Tokyo.name(), Pekin.name()));
                }
                break;
            case "Shangai":

                if (isVille(Pekin.name())
                        && isVille(Seoul.name())
                        && isVille(Tokyo.name())
                        && isVille(Taipei.name())
                        && isVille(Hong_kong.name())) {
                    getVilles().get(Shangai.name()).setVillesVoisines(List.of(Pekin.name(), Seoul.name(), Tokyo.name(), Taipei.name(), Hong_kong.name()));
                }
                break;
            case "Sydney":
                if (isVille(Sydney.name())
                        && isVille(Jakarta.name())
                        && isVille(Manille.name())
                        && isVille(Los_Angeles.name())) {
                    getVilles().get(Sydney.name()).setVillesVoisines(List.of(Sydney.name(), Jakarta.name(), Manille.name(), Los_Angeles.name()));
                }
                break;
            case "Hong_kong":
                if (isVille(Shangai.name())
                        && isVille(Taipei.name())
                        && isVille(Manille.name())
                        && isVille(Ho_chi_minh_ville.name())
                        && isVille(Bangkok.name())
                        && isVille(Calcutta.name())) {
                    getVilles().get(Hong_kong.name()).setVillesVoisines(List.of(Shangai.name(), Taipei.name(), Manille.name(), Ho_chi_minh_ville.name(),Bangkok.name(), Calcutta.name()));
                }
                break;
            case "Taipei":
                if (isVille(Osaka.name())
                        && isVille(Manille.name())
                        && isVille(Shangai.name())
                        && isVille(Hong_kong.name())) {
                    getVilles().get(Taipei.name()).setVillesVoisines(List.of(Osaka.name(), Manille.name(), Shangai.name(), Hong_kong.name()));
                }
                break;
            case "Tokyo":
                if (isVille(Osaka.name())
                        && isVille(Seoul.name())
                        && isVille(Shangai.name())
                        && isVille(San_Francisco.name())) {
                    getVilles().get(Tokyo.name()).setVillesVoisines(List.of(Osaka.name(), Seoul.name(), Shangai.name(), San_Francisco.name()));
                }
                break;
        }
    }

}
