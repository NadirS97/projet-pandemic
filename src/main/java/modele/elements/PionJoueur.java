package modele.elements;

import lombok.Getter;
import lombok.Setter;
import modele.elements.actions.Deplacement;
import modele.elements.actions.IAction;
import modele.elements.cartes.*;
import modele.elements.enums.NomsRoles;
import modele.exceptions.*;
import modele.utils.DonneesVariablesStatiques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PionJoueur {

    private IAction action;
    private int nbActions;
    private String pseudoJoueur;
    private CarteRole roleJoueur;
    private String couleurPion;
    private List<CarteJoueur> deckJoueur;
    private Ville villeActuelle;
    private Plateau plateau;

    private boolean permissionPontAerien;
    private CarteEvenement cartePlanificateurUrgenceEntrepose;
    private boolean autorisationDeplacementRepartiteur;
    private List<CartePropagation> mainAReorganiser = new ArrayList<>();




    public PionJoueur(Plateau plateau){
        this.plateau = plateau;
        this.deckJoueur = new ArrayList<>();
        this.nbActions = DonneesVariablesStatiques.nbActionsMaxParTour;
        this.roleJoueur = plateau.piocherCarteRole();
        this.autorisationDeplacementRepartiteur = false;
        // tout les pions commencent à Atlanta
        this.villeActuelle = plateau.getVilleByName("Atlanta");
    }


    /**
     * Fonction permettant de savoir si le joueur possède une carteVille dans sa main correspondant à la ville en paramètre
     *
     * @param ville
     * @return True si le joueur possède la carte en main, False sinon
     */
    public Boolean isVilleOfCarteVilleDeckJoueur(Ville ville) {
        List<Ville> listeVillesDeckJoueur = new ArrayList<>();
        for (CarteJoueur carteJoueur : deckJoueur) {
            if (carteJoueur instanceof CarteVille) {
                listeVillesDeckJoueur.add(((CarteVille) carteJoueur).getVilleCarteVille());
            }
        }
        return listeVillesDeckJoueur.contains(ville); //True
    }

    public Map.Entry<String, Integer> recupNbMaxCarteVilleMemeCouleurDeckJoueur(){
        Map<String, Integer> maMap= new HashMap<>();
        plateau.getLesVirus().keySet().forEach(s -> maMap.put(s, 0));
        deckJoueur.forEach(carteJoueur -> {
            if(carteJoueur instanceof CarteVille){
                String couleurVirusVille = ((CarteVille) carteJoueur).getVilleCarteVille().getCouleurVirusVille();
                int nbCarteVille = maMap.get(couleurVirusVille);
                maMap.put(couleurVirusVille, nbCarteVille + 1);
            }
        });
        return maMap.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null);
    }

    public List<CarteVille> recupLesCartesVilleDeMemeCouleurDeDeckJoueur(String couleurVirus){
        List<CarteVille> cartesVilleDeMemeCouleur= new ArrayList<>();
        deckJoueur.forEach(carteJoueur -> {
            if(carteJoueur instanceof CarteVille){
                if(((CarteVille) carteJoueur).getVilleCarteVille().getCouleurVirusVille().equals(couleurVirus)){
                    cartesVilleDeMemeCouleur.add((CarteVille) carteJoueur);
                }
            }
        });
        return cartesVilleDeMemeCouleur;
    }

    public CarteJoueur defausseCarteVilleDeDeckJoueur(Ville ville) {
        for (CarteJoueur carteJoueur : deckJoueur) {
            if (carteJoueur instanceof CarteVille) {
                if (((CarteVille) carteJoueur).getVilleCarteVille().equals(ville)) {
                    deckJoueur.remove(carteJoueur);
                    return carteJoueur;
                }
            }
        }
        return null;
    }

    public void jouerCarteEvenement(CarteEvenement carteEvenement) throws CarteEvenementNotFoundInDeckException, VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, PermissionNonAccordeException, CartePropagationNotInDefausseException, DefaitePartieTermineException {
        if (!deckJoueur.contains(carteEvenement))
            throw new CarteEvenementNotFoundInDeckException();
        carteEvenement.execEffet(this);
        deckJoueur.remove(carteEvenement);
    }

    public void piocherCartes() throws TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException, VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, DefaitePartieTermineException {
        if (plateau.getPiocheCarteJoueur().isEmpty())
            throw new EchecDeLaPartiePlusDeCarteJoueurException();

        for (int i = 0; i < DonneesVariablesStatiques.nbCartesAPiocherParTour; i++) {
            CarteJoueur carte = plateau.piocherCarteJoueur();
            if(carte instanceof CarteEpidemie) {
                ((CarteEpidemie) carte).execEffet(this);
                plateau.defausserCarteJoueur(carte);
            }else {
                deckJoueur.add(carte);
            }
        }

        if (deckJoueur.size() >= DonneesVariablesStatiques.nbCartesJoueurMaxEnMain){
            throw new TropDeCarteEnMainException();
        }
    }

    public void defausserListeCarteJoueurEnTrop(List<CarteJoueur> carteJoueurs) throws CarteJoueurInexistanteDansDeckException {
        for (CarteJoueur carteJoueurDefausser : carteJoueurs){
            defausserCarteJoueur(carteJoueurDefausser);
        }
    }
    public void defausserCarteJoueur(CarteJoueur carteJoueur) throws CarteJoueurInexistanteDansDeckException {
        if (!deckJoueur.contains(carteJoueur))
            throw new CarteJoueurInexistanteDansDeckException();
        deckJoueur.remove(carteJoueur);
        plateau.getDefausseCarteJoueur().add(carteJoueur);
    }


    public void ajouterCarteVilleDeckJoueur(CarteVille carteVille) {
        deckJoueur.add(carteVille);
    }

    public void setVilleActuelle(Ville villeActuelle) {
        this.villeActuelle = villeActuelle;
    }

    public void executerAction() throws CarteVilleInexistanteDansDeckJoueurException, NbCartesVilleDansDeckJoueurInvalideException, VirusDejaEradiqueException, VilleNonVoisineException, NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteEvenementNonTrouveDansDefausseException, JoueursNonPresentMemeVilleException, VirusDejaTraiteException, VilleIntrouvableException, VilleDestinationEstVilleActuelleException, MauvaisRoleException, VirusInexistantDansLaVilleActuelException, VilleAvecAucuneStationDeRechercheException, DonneeManquanteException {
        this.action.execAction(this);
        this.nbActions--;
    }

    public void repartiteurActionDeplacementAutrePion(PionJoueur joueurCible) throws AutorisationManquanteException, CarteVilleInexistanteDansDeckJoueurException, NbCartesVilleDansDeckJoueurInvalideException, VirusDejaEradiqueException, VilleNonVoisineException, NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteEvenementNonTrouveDansDefausseException, JoueursNonPresentMemeVilleException, VirusDejaTraiteException, VilleIntrouvableException, VilleDestinationEstVilleActuelleException, MauvaisRoleException, VirusInexistantDansLaVilleActuelException, VilleAvecAucuneStationDeRechercheException, DonneeManquanteException, ActionNonDeplacementException {
        if (!this.autorisationDeplacementRepartiteur)
            throw new AutorisationManquanteException();
        if (this.action instanceof Deplacement){
            joueurCible.setAction(action);
            joueurCible.executerAction();
            this.nbActions--;
        }
        throw new ActionNonDeplacementException();

    }



    public void jouerCarteEntreposerPlanificateurUrgence() throws MauvaisRoleException, VilleDejaEclosException, CarteEvenementNotFoundInDeckException, PermissionNonAccordeException, NbCubesAAjouterInvalideException, CartePropagationNotInDefausseException, PropagationImpossibleCarSpecialisteQuarantaineException, DefaitePartieTermineException {
        if (!this.roleJoueur.getNomRole().equals(NomsRoles.PLANIFICATEUR_D_URGENCE))
            throw new MauvaisRoleException();
        jouerCarteEvenement(cartePlanificateurUrgenceEntrepose);
        cartePlanificateurUrgenceEntrepose = null;

    }

    public void repartiteurDeplacementPion(Partie partie, PionJoueur joueurCible,Ville villeDestination) throws AutorisationManquanteException, AucunJoueurDansVilleDestinationException {
        if (!joueurCible.autorisationDeplacementRepartiteur)
            throw new AutorisationManquanteException();

        // verif si la ville destination possède un joueur
        for (PionJoueur pionJoueur : partie.getJoueurs()){
            if (pionJoueur.getVilleActuelle().equals(villeDestination)){
                joueurCible.setVilleActuelle(villeDestination);
                nbActions++;
                return;
            }
        }

        throw new AucunJoueurDansVilleDestinationException();
    }

    @Override
    public String toString() {
        return "PionJoueur{" +
                "roleJoueur=" + roleJoueur.getNomRole() +
                '}';
    }
}

