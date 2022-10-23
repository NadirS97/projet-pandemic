package modele.elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modele.dto.DonneesVillesDTO;
import modele.dto.VillesDTO;
import modele.elements.cartes.evenements.*;
import modele.exceptions.CasCouleurVilleIncorrectException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VilleNonVoisineException;
import lombok.Getter;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CartePropagation;
import modele.elements.cartes.CarteVille;
import modele.elements.enums.Couleurs;

import modele.elements.enums.NomsEvenement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;


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
    private int nbStationsDeRechercheConstruites;

    public Plateau() {
        listeVirus = new ArrayList<>();
        villes = new HashMap<>();
        marqueurVitessePropagation = 0;
        marqueurVitesseEclosion = 0;
        nbStationsDeRechercheConstruites = 0;
        piocheCarteJoueur = new ArrayList<>();
        defausseCarteJoueur = new ArrayList<>();
        piocheCartePropagation = new ArrayList<>();
        defausseCartePropagation = new ArrayList<>();
    }
    public void initialisationPlateau() throws FileNotFoundException, CasCouleurVilleIncorrectException, VilleIntrouvableException {
        initialisationVirus();
        initialisationVilles();
        initialiserCartesJoueur();
    }

    public Ville getVilleByName(String name) {
        return villes.get(name);
    }

    public int getNbStationsDeRechercheConstruites(){
        for(Ville ville : getVilles().values()){
            if(ville.isStationDeRechercheVille()){
                nbStationsDeRechercheConstruites++;
            }
        }
        return nbStationsDeRechercheConstruites;
    }

    public void initialiserCartesJoueur(){
        for (Ville ville : this.getVilles().values()){
            piocheCarteJoueur.add(new CarteVille(ville));
        }
        for (NomsEvenement nomEvenement : NomsEvenement.values()){
            switch (nomEvenement) {
                case PONT_AERIEN -> piocheCarteJoueur.add(new PontAerien());
                case SUBVENTION_PUBLIQUE -> piocheCarteJoueur.add(new SubventionPublique());
                case PREVISION -> piocheCarteJoueur.add(new Prevision());
                case PAR_UNE_NUIT_TRANQUILE -> piocheCarteJoueur.add(new ParUneNuitTranquille());
                case POPULATION_RESILIENTE -> piocheCarteJoueur.add(new PopulationResiliente());
            }
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

    public Boolean isVilleVoisine(Ville villeActuelle, Ville villeDestination) throws VilleNonVoisineException {
        return villeActuelle.getVillesVoisinesVille().contains(villeDestination);
    }

    public Boolean isVilleStationDeRecherche(Ville villeDestination) throws VilleAvecAucuneStationDeRechercheException {
        return villeDestination.isStationDeRechercheVille();
    }

    public void initialisationVirus(){
        for(Couleurs couleursVirus : Couleurs.values()){
            Virus virus = new Virus(couleursVirus);
            getListeVirus().add(virus);
        }
    }

    public DonneesVillesDTO lectureDonneesVilles() throws FileNotFoundException {
        FileReader reader = new FileReader("modele/src/main/resources/DonneesVilles.json");
        BufferedReader br = new BufferedReader(reader);
        String donnees = br.lines().collect(Collectors.joining());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(donnees, DonneesVillesDTO.class);
    }

    public void initialisationVilles() throws CasCouleurVilleIncorrectException, FileNotFoundException, VilleIntrouvableException {
        DonneesVillesDTO donneesVillesDTO = lectureDonneesVilles();
        for(Virus virus : getListeVirus()){
            switch(virus.getVirusCouleur()){
                case BLEU:
                    donneesVillesDTO.getVilles_bleues().forEach(villesDTO ->{
                        Ville ville = new Ville(villesDTO.getNomVille(), villesDTO.getPopulationTotaleVille(), villesDTO.getPopulationKmCarreVille(), virus);
                        getVilles().put(villesDTO.getNomVille(), ville);
                    });
                    break;
                case JAUNE:
                    donneesVillesDTO.getVilles_jaunes().forEach(villesDTO ->{
                        Ville ville = new Ville(villesDTO.getNomVille(), villesDTO.getPopulationTotaleVille(), villesDTO.getPopulationKmCarreVille(), virus);
                        getVilles().put(villesDTO.getNomVille(), ville);
                    });
                    break;
                case NOIR:
                    donneesVillesDTO.getVilles_noires().forEach(villesDTO ->{
                        Ville ville = new Ville(villesDTO.getNomVille(), villesDTO.getPopulationTotaleVille(), villesDTO.getPopulationKmCarreVille(), virus);
                        getVilles().put(villesDTO.getNomVille(), ville);
                    });
                    break;
                case ROUGE:
                    donneesVillesDTO.getVilles_rouges().forEach(villesDTO ->{
                        Ville ville = new Ville(villesDTO.getNomVille(), villesDTO.getPopulationTotaleVille(), villesDTO.getPopulationKmCarreVille(), virus);
                        getVilles().put(villesDTO.getNomVille(), ville);
                    });
                    break;
                default:
                    throw new CasCouleurVilleIncorrectException();
            }
        }
        attributionVoisins(donneesVillesDTO);
    }

    private List<String> listeVillesNonValide (DonneesVillesDTO donneesVillesDTO){
        List<String> listeNonValide = new ArrayList<>();
        donneesVillesDTO.getVilles_bleues().forEach(villesDTO ->{
            villesDTO.getListeNomsVillesVoisines().forEach(s -> {
                if(!villes.containsKey(s))
                    listeNonValide.add(s);
            });
        });
        donneesVillesDTO.getVilles_rouges().forEach(villesDTO ->{
            villesDTO.getListeNomsVillesVoisines().forEach(s -> {
                if(!villes.containsKey(s))
                    listeNonValide.add(s);
            });
        });
        donneesVillesDTO.getVilles_jaunes().forEach(villesDTO ->{
            villesDTO.getListeNomsVillesVoisines().forEach(s -> {
                if(!villes.containsKey(s))
                    listeNonValide.add(s);
            });
        });
        donneesVillesDTO.getVilles_noires().forEach(villesDTO ->{
            villesDTO.getListeNomsVillesVoisines().forEach(s -> {
                if(!villes.containsKey(s))
                    listeNonValide.add(s);
            });
        });
        return listeNonValide;
    }

    public void attributionVoisins(DonneesVillesDTO donneesVillesDTO) throws VilleIntrouvableException {
        List<String> listeVillesNonValide = listeVillesNonValide(donneesVillesDTO);
        if(!listeVillesNonValide.isEmpty()) throw new VilleIntrouvableException(
                "Une erreur vient de se produire, le nom des villes ci-joint ne sont pas corrects: " +
                        listeVillesNonValide.toString());
        donneesVillesDTO.getVilles_bleues().forEach(villesDTO -> {
            List<String> listeNomsVillesVoisines = villesDTO.getListeNomsVillesVoisines();
            villes.get(villesDTO.getNomVille()).setVillesVoisines(listeNomsVillesVoisines);
        });
        donneesVillesDTO.getVilles_jaunes().forEach(villesDTO -> {
            List<String> listeNomsVillesVoisines = villesDTO.getListeNomsVillesVoisines();
            villes.get(villesDTO.getNomVille()).setVillesVoisines(listeNomsVillesVoisines);
        });
        donneesVillesDTO.getVilles_rouges().forEach(villesDTO -> {
            List<String> listeNomsVillesVoisines = villesDTO.getListeNomsVillesVoisines();
            villes.get(villesDTO.getNomVille()).setVillesVoisines(listeNomsVillesVoisines);
        });
        donneesVillesDTO.getVilles_noires().forEach(villesDTO -> {
            List<String> listeNomsVillesVoisines = villesDTO.getListeNomsVillesVoisines();
            villes.get(villesDTO.getNomVille()).setVillesVoisines(listeNomsVillesVoisines);
        });

//            case "Bogota":
//                if(isVille(Miami.name())
//                        && isVille(Mexico.name())
//                        && isVille(Lima.name())
//                        && isVille(Buenos_Aires.name())
//                        && isVille(Sao_Paulo.name())) {
//                    getVilles().get(Bogota.name()).setVillesVoisines(List.of(Miami.name(), Mexico.name(), Lima.name(), Buenos_Aires.name(), Sao_Paulo.name()));
//                }
//                break;
//            case "Buenos_Aires":
//                if(isVille(Bogota.name())
//                        && isVille(Sao_Paulo.name())) {
//                    getVilles().get(Buenos_Aires.name()).setVillesVoisines(List.of(Bogota.name(), Sao_Paulo.name()));
//                }
//                break;
//            case "Johannesburg":
//                if(isVille(Kinshasa.name())
//                        && isVille(Khartoum.name())) {
//                    getVilles().get(Johannesburg.name()).setVillesVoisines(List.of(Kinshasa.name(), Khartoum.name()));
//                }
//                break;
//            case "Khartoum":
//                if(isVille(Kinshasa.name())
//                        && isVille(Le_Caire.name())
//                        && isVille(Lagos.name())
//                        && isVille(Johannesburg.name())) {
//                    getVilles().get(Khartoum.name()).setVillesVoisines(List.of(Kinshasa.name(), Le_Caire.name(), Lagos.name(), Johannesburg.name()));
//                }
//                break;
//            case "Kinshasa":
//                if(isVille(Lagos.name())
//                        && isVille(Khartoum.name())
//                        && isVille(Johannesburg.name())) {
//                    getVilles().get(Kinshasa.name()).setVillesVoisines(List.of(Lagos.name(), Khartoum.name(), Johannesburg.name()));
//                }
//                break;
//            case "Lagos":
//                if(isVille(Sao_Paulo.name())
//                        && isVille(Kinshasa.name())
//                        && isVille(Khartoum.name())) {
//                    getVilles().get(Lagos.name()).setVillesVoisines(List.of(Sao_Paulo.name(), Kinshasa.name(), Khartoum.name()));
//                }
//                break;
//            case "Lima":
//                if(isVille(Mexico.name())
//                        && isVille(Bogota.name())
//                        && isVille(Santiago.name())) {
//                    getVilles().get(Lima.name()).setVillesVoisines(List.of(Mexico.name(), Bogota.name(), Santiago.name()));
//                }
//                break;
//            case "Los_Angeles":
//                if(isVille(Mexico.name())
//                        && isVille(Chicago.name())
//                        && isVille(San_Francisco.name())
//                        && isVille(Sydney.name())) {
//                    getVilles().get(Los_Angeles.name()).setVillesVoisines(List.of(Mexico.name(), Chicago.name(), San_Francisco.name(), Sydney.name()));
//                }
//                break;
//            case "Mexico":
//                if(isVille(Los_Angeles.name())
//                        && isVille(Chicago.name())
//                        && isVille(Miami.name())
//                        && isVille(Bogota.name())
//                        && isVille(Lima.name())) {
//                    getVilles().get(Mexico.name()).setVillesVoisines(List.of(Los_Angeles.name(), Chicago.name(), Miami.name(), Bogota.name(), Lima.name()));
//                }
//                break;
//            case "Miami":
//                if(isVille(Washington.name())
//                        && isVille(Atlanta.name())
//                        && isVille(Mexico.name())
//                        && isVille(Bogota.name())) {
//                    getVilles().get(Miami.name()).setVillesVoisines(List.of(Washington.name(), Atlanta.name(), Mexico.name(), Bogota.name()));
//                }
//                break;
//            case "Santiago":
//                if(isVille(Lima.name())) {
//                    getVilles().get(Santiago.name()).setVillesVoisines(List.of(Lima.name()));
//                }
//                break;
//            case "Sao_Paulo":
//                if(isVille(Madrid.name())
//                        && isVille(Lagos.name())
//                        && isVille(Bogota.name())
//                        && isVille(Buenos_Aires.name())) {
//                    getVilles().get(Sao_Paulo.name()).setVillesVoisines(List.of(Madrid.name(), Lagos.name(), Bogota.name(), Buenos_Aires.name()));
//                }
//                break;
//            case "Alger":
//                if(isVille(Madrid.name())
//                        && isVille(Paris.name())
//                        && isVille(Istanbul.name())
//                        && isVille(Le_Caire.name())) {
//                    getVilles().get(Alger.name()).setVillesVoisines(List.of(Madrid.name(), Paris.name(), Istanbul.name(), Le_Caire.name()));
//                }
//                break;
//            case "Bagdad":
//                if(isVille(Teheran.name())
//                        && isVille(Istanbul.name())
//                        && isVille(Le_Caire.name())
//                        && isVille(Riyad.name())
//                        && isVille(Karachi.name())) {
//                    getVilles().get(Bagdad.name()).setVillesVoisines(List.of(Teheran.name(), Istanbul.name(), Le_Caire.name(), Riyad.name(), Karachi.name()));
//                }
//                break;
//            case "Calcutta":
//                if(isVille(Hong_kong.name())
//                        && isVille(Bangkok.name())
//                        && isVille(Chennai.name())
//                        && isVille(Delhi.name())) {
//                    getVilles().get(Calcutta.name()).setVillesVoisines(List.of(Hong_kong.name(), Bangkok.name(), Chennai.name(), Delhi.name()));
//                }
//                break;
//            case "Chennai":
//                if(isVille(Mumbai.name())
//                        && isVille(Delhi.name())
//                        && isVille(Calcutta.name())
//                        && isVille(Bangkok.name())
//                        && isVille(Jakarta.name())) {
//                    getVilles().get(Chennai.name()).setVillesVoisines(List.of(Mumbai.name(), Delhi.name(), Calcutta.name(), Bangkok.name(), Jakarta.name()));
//                }
//                break;
//            case "Delhi":
//                if(isVille(Teheran.name())
//                        && isVille(Karachi.name())
//                        && isVille(Mumbai.name())
//                        && isVille(Chennai.name())
//                        && isVille(Calcutta.name())) {
//                    getVilles().get(Delhi.name()).setVillesVoisines(List.of(Teheran.name(), Karachi.name(), Mumbai.name(), Chennai.name(), Calcutta.name()));
//                }
//                break;
//            case "Istanbul":
//                if(isVille(Milan.name())
//                        && isVille(Saint_Petersbourg.name())
//                        && isVille(Moscou.name())
//                        && isVille(Bagdad.name())
//                        && isVille(Le_Caire.name())
//                        && isVille(Alger.name())) {
//                    getVilles().get(Istanbul.name()).setVillesVoisines(List.of(Milan.name(), Saint_Petersbourg.name(), Moscou.name(), Bagdad.name(), Le_Caire.name(), Alger.name()));
//                }
//                break;
//            case "Karachi":
//                if(isVille(Riyad.name())
//                        && isVille(Mumbai.name())
//                        && isVille(Delhi.name())
//                        && isVille(Teheran.name())
//                        && isVille(Bagdad.name())) {
//                    getVilles().get(Karachi.name()).setVillesVoisines(List.of(Riyad.name(), Mumbai.name(), Delhi.name(), Teheran.name(), Bagdad.name()));
//                }
//                break;
//            case "Le_Caire":
//                if(isVille(Alger.name())
//                        && isVille(Istanbul.name())
//                        && isVille(Bagdad.name())
//                        && isVille(Riyad.name())) {
//                    getVilles().get(Le_Caire.name()).setVillesVoisines(List.of(Alger.name(), Istanbul.name(), Bagdad.name(), Riyad.name()));
//                }
//                break;
//            case "Moscou":
//                if (isVille(Saint_Petersbourg.name())
//                        && isVille(Istanbul.name())
//                        && isVille(Teheran.name())){
//                    getVilles().get(Moscou.name()).setVillesVoisines(List.of(Saint_Petersbourg.name(), Istanbul.name(), Teheran.name()));
//                }
//                break;
//            case "Mumbai":
//                if (isVille(Karachi.name())
//                        && isVille(Delhi.name())
//                        && isVille(Chennai.name())){
//                    getVilles().get(Mumbai.name()).setVillesVoisines(List.of(Karachi.name(), Delhi.name(), Chennai.name()));
//                }
//                break;
//            case "Riyad":
//                if (isVille(Le_Caire.name())
//                        && isVille(Bagdad.name())
//                        && isVille(Karachi.name())){
//                    getVilles().get(Riyad.name()).setVillesVoisines(List.of(Le_Caire.name(), Bagdad.name(), Karachi.name()));
//                }
//                break;
//            case "Teheran":
//                if (isVille(Moscou.name())
//                        && isVille(Bagdad.name())
//                        && isVille(Karachi.name())
//                        && isVille(Delhi.name())){
//                    getVilles().get(Teheran.name()).setVillesVoisines(List.of(Moscou.name(), Bagdad.name(), Karachi.name(), Delhi.name()));
//                }
//                break;
//            case "Bangkok":
//                if (isVille(Calcutta.name())
//                        && isVille(Chennai.name())
//                        && isVille(Jakarta.name())
//                        && isVille(Ho_chi_minh_ville.name())
//                        && isVille(Hong_kong.name())){
//                    getVilles().get(Bangkok.name()).setVillesVoisines(List.of(Calcutta.name(), Chennai.name(), Jakarta.name(), Ho_chi_minh_ville.name(), Hong_kong.name()));
//                }
//                break;
//            case "Ho_chi_minh_ville":
//                if (isVille(Hong_kong.name())
//                        && isVille(Manille.name())
//                        && isVille(Bangkok.name())
//                        && isVille(Jakarta.name())){
//                    getVilles().get(Ho_chi_minh_ville.name()).setVillesVoisines(List.of(Hong_kong.name(), Manille.name(), Bangkok.name(), Jakarta.name()));
//                }
//                break;
//            case "Jakarta":
//                if (isVille(Chennai.name())
//                        && isVille(Bangkok.name())
//                        && isVille(Ho_chi_minh_ville.name())
//                        && isVille(Sydney.name())){
//                    getVilles().get(Jakarta.name()).setVillesVoisines(List.of(Chennai.name(), Bangkok.name(), Ho_chi_minh_ville.name(), Sydney.name()));
//                }
//                break;
//            case "Manille":
//                if (isVille(Taipei.name())
//                        && isVille(Hong_kong.name())
//                        && isVille(Ho_chi_minh_ville.name())
//                        && isVille(Sydney.name())
//                        && isVille(San_Francisco.name())){
//                    getVilles().get(Manille.name()).setVillesVoisines(List.of(Taipei.name(), Hong_kong.name(), Ho_chi_minh_ville.name(), Sydney.name(),San_Francisco.name()));
//                }
//                break;
//            case "Osaka":
//                if (isVille(Taipei.name())
//                        && isVille(Tokyo.name())){
//                    getVilles().get(Osaka.name()).setVillesVoisines(List.of(Taipei.name(), Tokyo.name()));
//                }
//                break;
//            case "Pekin":
//                if (isVille(Seoul.name())
//                        && isVille(Shangai.name())){
//                    getVilles().get(Pekin.name()).setVillesVoisines(List.of(Seoul.name(), Shangai.name()));
//                }
//                break;
//            case "Seoul":
//
//                if (isVille(Shangai.name())
//                        && isVille(Tokyo.name())
//                        && isVille(Pekin.name())) {
//                    getVilles().get(Seoul.name()).setVillesVoisines(List.of(Shangai.name(), Tokyo.name(), Pekin.name()));
//                }
//                break;
//            case "Shangai":
//
//                if (isVille(Pekin.name())
//                        && isVille(Seoul.name())
//                        && isVille(Tokyo.name())
//                        && isVille(Taipei.name())
//                        && isVille(Hong_kong.name())) {
//                    getVilles().get(Shangai.name()).setVillesVoisines(List.of(Pekin.name(), Seoul.name(), Tokyo.name(), Taipei.name(), Hong_kong.name()));
//                }
//                break;
//            case "Sydney":
//                if (isVille(Sydney.name())
//                        && isVille(Jakarta.name())
//                        && isVille(Manille.name())
//                        && isVille(Los_Angeles.name())) {
//                    getVilles().get(Sydney.name()).setVillesVoisines(List.of(Sydney.name(), Jakarta.name(), Manille.name(), Los_Angeles.name()));
//                }
//                break;
//            case "Hong_kong":
//                if (isVille(Shangai.name())
//                        && isVille(Taipei.name())
//                        && isVille(Manille.name())
//                        && isVille(Ho_chi_minh_ville.name())
//                        && isVille(Bangkok.name())
//                        && isVille(Calcutta.name())) {
//                    getVilles().get(Hong_kong.name()).setVillesVoisines(List.of(Shangai.name(), Taipei.name(), Manille.name(), Ho_chi_minh_ville.name(),Bangkok.name(), Calcutta.name()));
//                }
//                break;
//            case "Taipei":
//                if (isVille(Osaka.name())
//                        && isVille(Manille.name())
//                        && isVille(Shangai.name())
//                        && isVille(Hong_kong.name())) {
//                    getVilles().get(Taipei.name()).setVillesVoisines(List.of(Osaka.name(), Manille.name(), Shangai.name(), Hong_kong.name()));
//                }
//                break;
//            case "Tokyo":
//                if (isVille(Osaka.name())
//                        && isVille(Seoul.name())
//                        && isVille(Shangai.name())
//                        && isVille(San_Francisco.name())) {
//                    getVilles().get(Tokyo.name()).setVillesVoisines(List.of(Osaka.name(), Seoul.name(), Shangai.name(), San_Francisco.name()));
//                }
//                break;
//        }
    }

}
