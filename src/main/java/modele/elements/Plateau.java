package modele.elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modele.dto.DonneesPlateauDTO;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.evenements.*;
import modele.elements.cartes.roles.*;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.EtatVirus;
import modele.elements.enums.NomsRoles;
import modele.exceptions.*;
import lombok.Getter;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CartePropagation;
import modele.elements.cartes.CarteVille;

import modele.elements.enums.NomsEvenement;
import modele.utils.DonneesVariablesStatiques;

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
    private LinkedList<CarteJoueur> piocheCarteJoueur;
    private List<CarteJoueur> defausseCarteJoueur;
    private List<CartePropagation> piocheCartePropagation;
    private List<CartePropagation> defausseCartePropagation;
    private List<CarteRole> toutesLesCartesRolesExistante;

    // TODO : créer méthode pour y mettre les couleurs des pions de joueurs
    private List<String> listeCouleursPionsJoueurs;
    private int nbStationsDeRechercheConstruites;

    // TODO : au moment de la phase propagation des maladies, vérifier que effetParUneNuitTranquilleActif est à false, sinon passer la phase puis passer effetParUneNuitTranquilleActif à false
    private boolean effetParUneNuitTranquilleActif;
    private DonneesPlateauDTO donneesPlateauDTO;

    public Plateau() throws Exception {
        lesVirus = new HashMap<>();
        villes = new HashMap<>();
        // indicateur du nombre d'éclosion doit être placé sur la case 0.
        marqueurVitessePropagation = 0; // 0,1,3 = 2 ; 3,4 = 3 ; 4,5 = 4
        marqueurVitesseEclosion = 0;
        nbStationsDeRechercheConstruites = 0;
        piocheCarteJoueur = new LinkedList<>();
        defausseCarteJoueur = new ArrayList<>();
        piocheCartePropagation = new ArrayList<>();
        defausseCartePropagation = new ArrayList<>();
        listeCouleursPionsJoueurs = new ArrayList<>();
        toutesLesCartesRolesExistante = new ArrayList<>();
        effetParUneNuitTranquilleActif = false;
        initialisationPlateau();
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

    public void initialisationPlateau() throws FileNotFoundException, VilleIntrouvableException, VirusIntrouvableException, RoleIntrouvableException, EvenementInnexistantException {
        donneesPlateauDTO = lectureDonneesPlateau();
        initialisationCartesRoles();
        initialisationVirus();
        initialisationVilles();
        initialiserCartesJoueur();
        initialiserCartesPropagation();
    }

    public void initialiserCartesJoueur() throws EvenementInnexistantException {
        for (Ville ville : this.getVilles().values()) {
            piocheCarteJoueur.add(new CarteVille(ville));
        }
        for (NomsEvenement nomEvenement : NomsEvenement.values()) {
            switch (nomEvenement) {
                case PONT_AERIEN -> piocheCarteJoueur.add(new CartePontAerien());
                case SUBVENTION_PUBLIQUE -> piocheCarteJoueur.add(new CarteSubventionPublique());
                case PREVISION -> piocheCarteJoueur.add(new CartePrevision());
                case PAR_UNE_NUIT_TRANQUILE -> piocheCarteJoueur.add(new CarteParUneNuitTranquille());
                case POPULATION_RESILIENTE -> piocheCarteJoueur.add(new CartePopulationResiliente());
                default -> throw new EvenementInnexistantException(
                        "L'évènement : " + nomEvenement + " est inexistant.");
            }
        }
        melangerPaquet(piocheCarteJoueur);
    }

    public void initialiserCartesPropagation() throws EvenementInnexistantException {
        for (Ville ville : this.getVilles().values()) {
            piocheCartePropagation.add(new CartePropagation(ville));
        }
        melangerPaquet(piocheCartePropagation);
    }

    public void initialisationVilles() throws VilleIntrouvableException, VirusIntrouvableException {
        attributionVirus(donneesPlateauDTO);
        attributionVoisins(donneesPlateauDTO);
    }

    public void initialisationCartesRoles() throws RoleIntrouvableException {
        for (NomsRoles role : NomsRoles.values()) {
            switch (role) {
                case CHERCHEUSE -> toutesLesCartesRolesExistante.add(new CarteChercheuse(CouleurPionsRole.MARRON));
                case SCIENTIFIQUE -> toutesLesCartesRolesExistante.add(new CarteScientifique(CouleurPionsRole.BLANC));
                case REPARTITEUR -> toutesLesCartesRolesExistante.add(new CarteRepartiteur(CouleurPionsRole.ROSE));
                case SPECIALISTE_EN_MISE_EN_QUARANTAINE -> toutesLesCartesRolesExistante.add(new CarteSpecialisteEnMiseEnQuarantaine(CouleurPionsRole.VERT_FONCE));
                case EXPERT_AUX_OPERATIONS ->toutesLesCartesRolesExistante.add(new CarteExpertAuxOperations(CouleurPionsRole.VERT_CLAIR));
                case MEDECIN -> toutesLesCartesRolesExistante.add(new CarteMedecin(CouleurPionsRole.ORANGE));
                case PLANIFICATEUR_D_URGENCE -> toutesLesCartesRolesExistante.add(new CartePlanificateurDUrgence(CouleurPionsRole.BLEU));
                default -> throw new RoleIntrouvableException("Le rôle : " + role + " est inexistant.");
            }
        }
        melangerPaquet(toutesLesCartesRolesExistante);
    }

    public void initialisationVirus() {
        donneesPlateauDTO.getListe_virus().forEach(virusDTO -> {
            for (EtatVirus etatVirus : EtatVirus.values()) {
                if (etatVirus.toString().equals(virusDTO.getEtatVirus())) {
                    Virus virus = new Virus(virusDTO.getCouleurVirus(), etatVirus);
                    getLesVirus().put(virusDTO.getCouleurVirus(), virus);
                }
            }
        });
    }

    // Sert exclusivement à l'effet Prevision
    public CarteJoueur piocherCarteJoueur() {
        return piocheCarteJoueur.remove(0);
    }

    // Sert exclusivement à l'effet Prevision
    public void ajouterDansPiocheCarteJoueur(CarteJoueur carteJoueur) {
        piocheCarteJoueur.addFirst(carteJoueur);
    }

    // sert exclusivement au test de l'évènement Prévision
    public void setPiocheCarteJoueur(LinkedList<CarteJoueur> piocheCarteJoueur) {
        this.piocheCarteJoueur = piocheCarteJoueur;
    }

    public Ville piocherCartePropagation() {
        CartePropagation cartePropagation = piocheCartePropagation.remove(0);
        return villes.get(cartePropagation.getVilleCartePropagation().getNomVille());
    }

    public void initialiserPropagation() throws VilleDejaEclosException, NuitTranquilleException {
        if (isEffetParUneNuitTranquilleActif())
            throw new NuitTranquilleException();
        for (int i = 0; i < nbCartePropagationPiocherSelonVitesse(); i++) {
            Ville villePropagation = piocherCartePropagation();
            propagationMaladie(villePropagation);
        }
    }

    public void propagationMaladie(Ville villePropagation) throws VilleDejaEclosException, NuitTranquilleException{
        Virus virus = lesVirus.get(villePropagation.getCouleurVirusVille());
        if (villePropagation.isEclosionVille())
            throw new VilleDejaEclosException();
        villePropagation.getNbCubeVirusVille().put(virus, villePropagation.getNbCubeVirusVille().get(virus) + 1);
        if (villePropagation.getNbCubeVirusVille().get(virus) == 3) {
            villePropagation.setEclosionVille(true);
            eclosion(villePropagation, virus);
        }
    }

    public void eclosion(Ville ville, Virus virus) {
        for (String villeVoisineString : ville.getVillesVoisinesVille()) {
            villes.get(villeVoisineString)
                    .getNbCubeVirusVille().put(virus, villes.get(villeVoisineString).getNbCubeVirusVille().get(virus) + 1);
            // si la ville est d'une autre couleur que le virus propagé, il faut l'ajouter à la map
            if (!villes.get(villeVoisineString).getNbCubeVirusVille().containsKey(virus))
                villes.get(villeVoisineString).getNbCubeVirusVille().put(virus, 0);

            System.out.println(villes.get(villeVoisineString) + " :" + villes.get(villeVoisineString).getNbCubeVirusVille().get(virus));
            if (villes.get(villeVoisineString).getNbCubeVirusVille().get(virus) == 3) {
                villes.get(villeVoisineString).setEclosionVille(true);
                eclosion(villes.get(villeVoisineString), virus);
            }
        }
    }

    public int nbCartePropagationPiocherSelonVitesse() {
        if (marqueurVitesseEclosion >= 5)
            return 4;
        else if (marqueurVitesseEclosion >= 3)
            return 3;
        else
            return 2;
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
        FileReader reader = new FileReader("src/main/resources/DonneesPlateau.json");
        BufferedReader br = new BufferedReader(reader);
        String donnees = br.lines().collect(Collectors.joining());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(donnees, DonneesPlateauDTO.class);
    }

    public void attributionVirus(DonneesPlateauDTO donneesPlateauDTO) throws VirusIntrouvableException {
        List<String> listeVillesVirusNonValide = listeVilleVirusNonValide(donneesPlateauDTO);
        if (!listeVillesVirusNonValide.isEmpty()) throw new VirusIntrouvableException(
                "Une erreur vient de se produire, vous trouverez ci-joint la liste des villes dont la couleur du virus est incorrect: " +
                        listeVillesVirusNonValide);
        donneesPlateauDTO.getVilles().forEach(villesDTO -> {
            Ville ville = new Ville(villesDTO.getNomVille(), villesDTO.getPopulationTotaleVille(), villesDTO.getPopulationKmCarreVille());
            lesVirus.values().forEach(virus -> {
                ville.getNbCubeVirusVille().put(virus, DonneesVariablesStatiques.nbCubesInitialementPresentDansChaqueVille);
            });
            ville.setCouleurVirusVille(villesDTO.getCouleurVirusVille());
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

    public void setEffetParUneNuitTranquilleActif(boolean effetParUneNuitTranquilleActif) {
        this.effetParUneNuitTranquilleActif = effetParUneNuitTranquilleActif;
    }
}
