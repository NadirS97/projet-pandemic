package modele.elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modele.dto.DonneesPlateauDTO;
import modele.elements.cartes.evenements.*;
import modele.exceptions.*;
import lombok.Getter;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CartePropagation;
import modele.elements.cartes.CarteVille;

import modele.elements.enums.NomsEvenement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;


@Getter
public class Plateau {

    private Map<String, Virus> lesVirus;
    private Map<String, Ville> villes;
    private int marqueurVitessePropagation;   // entre 1 et 3 = vitesse2 , 4, 5 = vitesse3 , 6, 7 vitesse 4, pas vraiment besoin d'un tableau ?
    private int marqueurVitesseEclosion;
    private List<CarteJoueur> piocheCarteJoueur;
    private List<CarteJoueur> defausseCarteJoueur;
    private List<CartePropagation> piocheCartePropagation;
    private List<CartePropagation> defausseCartePropagation;
    // TODO : créer méthode pour y mettre les couleurs des pions de joueurs
    private Set<String> listeCouleursPionsJoueurs;
    private int nbStationsDeRechercheConstruites;

    public Plateau() {
        lesVirus = new HashMap<>();
        villes = new HashMap<>();
        marqueurVitessePropagation = 0;
        marqueurVitesseEclosion = 0;
        nbStationsDeRechercheConstruites = 0;
        piocheCarteJoueur = new ArrayList<>();
        defausseCarteJoueur = new ArrayList<>();
        piocheCartePropagation = new ArrayList<>();
        defausseCartePropagation = new ArrayList<>();
        listeCouleursPionsJoueurs = new HashSet<>();
    }

    public Ville getVilleByName(String name) {
        return villes.get(name);
    }

    public int getNbStationsDeRechercheConstruites() {
        for (Ville ville : getVilles().values()) {
            if (ville.isStationDeRechercheVille()) {
                nbStationsDeRechercheConstruites++;
            }
        }
        return nbStationsDeRechercheConstruites;
    }

    public void initialisationPlateau() throws FileNotFoundException, VilleIntrouvableException, VirusIntrouvableException  {
        initialisationVirus();
        initialisationVilles();
        initialiserCartesJoueur();
    }

    public void initialiserCartesJoueur() {
        for (Ville ville : this.getVilles().values()) {
            piocheCarteJoueur.add(new CarteVille(ville));
        }
        for (NomsEvenement nomEvenement : NomsEvenement.values()) {
            switch (nomEvenement) {
                case PONT_AERIEN -> piocheCarteJoueur.add(new PontAerien(this));
                case SUBVENTION_PUBLIQUE -> piocheCarteJoueur.add(new SubventionPublique());
                case PREVISION -> piocheCarteJoueur.add(new Prevision());
                case PAR_UNE_NUIT_TRANQUILE -> piocheCarteJoueur.add(new ParUneNuitTranquille());
                case POPULATION_RESILIENTE -> piocheCarteJoueur.add(new PopulationResiliente());
            }
        }
        melangerPaquet(piocheCarteJoueur);
    }

    public void melangerPaquet(List<?> paquet) {
        Collections.shuffle(paquet);
    }

    public Boolean isVille(String nomVille) {
        return villes.containsKey(nomVille);
    }

    public Boolean isVilleVoisine(Ville villeActuelle, Ville villeDestination) {
        return villeActuelle.getVillesVoisinesVille().contains(villeDestination.getNomVille());
    }

    public Boolean isVilleStationDeRecherche(Ville villeDestination) {
        return villeDestination.isStationDeRechercheVille();
    }

    public DonneesPlateauDTO lectureDonneesPlateau() throws FileNotFoundException {
        FileReader reader = new FileReader("modele/src/main/resources/DonneesPlateau.json");
        BufferedReader br = new BufferedReader(reader);
        String donnees = br.lines().collect(Collectors.joining());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(donnees, DonneesPlateauDTO.class);
    }

    public void initialisationVirus() throws FileNotFoundException {
        DonneesPlateauDTO donneesPlateauDTO = lectureDonneesPlateau();
        donneesPlateauDTO.getListe_virus().forEach(virusDTO -> {
            Virus virus = new Virus(virusDTO.getCouleurVirus());
            getLesVirus().put(virusDTO.getCouleurVirus(), virus);
        });
    }

    public void initialisationVilles() throws FileNotFoundException, VilleIntrouvableException, VirusIntrouvableException {
        DonneesPlateauDTO donneesPlateauDTO = lectureDonneesPlateau();
        attributionVirus(donneesPlateauDTO);
        attributionVoisins(donneesPlateauDTO);
    }

    public void attributionVirus(DonneesPlateauDTO donneesPlateauDTO) throws VirusIntrouvableException {
        List<String> listeVillesVirusNonValide = listeVilleVirusNonValide(donneesPlateauDTO);
        if (!listeVillesVirusNonValide.isEmpty()) throw new VirusIntrouvableException(
                "Une erreur vient de se produire, vous trouverez ci-joint la liste des villes dont la couleur du virus est incorrect: " +
                        listeVillesVirusNonValide);
        donneesPlateauDTO.getVilles().forEach(villesDTO -> {
            Ville ville = new Ville(villesDTO.getNomVille(), villesDTO.getPopulationTotaleVille(), villesDTO.getPopulationKmCarreVille(), getLesVirus().get(villesDTO.getCouleurVirusVille()));
            getVilles().put(villesDTO.getNomVille(), ville);
        });
    }

    public void attributionVoisins(DonneesPlateauDTO donneesPlateauDTO) throws VilleIntrouvableException {
        List<String> listeVillesNonValide = listeVillesVoisinesNonValide(donneesPlateauDTO);
        if (!listeVillesNonValide.isEmpty()) throw new VilleIntrouvableException(
                "Une erreur vient de se produire, le nom des villes ci-joint ne sont pas corrects: " +
                        listeVillesNonValide);
        donneesPlateauDTO.getVilles().forEach(villesDTO -> {
            villes.get(villesDTO.getNomVille()).setVillesVoisines(villesDTO.getListeNomsVillesVoisines());
        });
    }

    private List<String> listeVillesVoisinesNonValide(DonneesPlateauDTO donneesPlateauDTO) {
        List<String> listeVillesNonValide = new ArrayList<>();
        donneesPlateauDTO.getVilles().forEach(villesDTO -> {
            villesDTO.getListeNomsVillesVoisines().forEach(s -> {
                if (!villes.containsKey(s))
                    listeVillesNonValide.add(s);
            });
        });
        return listeVillesNonValide;
    }

    private List<String> listeVilleVirusNonValide(DonneesPlateauDTO donneesPlateauDTO) {
        List<String> listeVirusNonValide = new ArrayList<>();
        donneesPlateauDTO.getVilles().forEach(villesDTO -> {
            if (!lesVirus.containsKey(villesDTO.getCouleurVirusVille()))
                listeVirusNonValide.add(villesDTO.getNomVille());
        });
        return listeVirusNonValide;
    }
}
