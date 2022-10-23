package modele.elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modele.dto.DonneesVillesDTO;
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
    }
}
