package modele.facade;

import modele.elements.Partie;
import modele.elements.Ville;
import modele.elements.enums.Actions;
import modele.exceptions.*;
import modele.elements.Joueur;
import modele.elements.enums.ModesDeplacements;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FacadePandemic9Impl implements FacadePandemic9 {

    private Map<String, Partie> parties;


    public FacadePandemic9Impl() {
        this.parties = new HashMap<>();
    }

    @Override
    public String creerPartie(String pseudoJoueurPartie) throws  CasCouleurVilleIncorrectException {
        Partie partie = new Partie(pseudoJoueurPartie);
        parties.put(partie.getCodePartie(),partie);
        return partie.getCodePartie();
    }

    @Override
    public void rejoindrePartie(String codePartie, String pseudoJoueurPartie) throws PseudoDejaExistantException,CodePartieInexistantException, DonneManquanteException {
        if(Objects.isNull(parties.get(codePartie)))
            throw new CodePartieInexistantException();
        if (parties.get(codePartie).isJoueurDejaDansPartie(pseudoJoueurPartie))
            throw new PseudoDejaExistantException();
        parties.get(codePartie).getJoueursPartie().put(pseudoJoueurPartie, new Joueur(pseudoJoueurPartie));
    }

    @Override
    public void jouerTour(String codePartie, Actions action, ModesDeplacements modeDeplacementChoisis, Ville villeDestination) throws CodePartieInexistantException, VilleAvecAucuneStationDeRechercheException, VilleNonVoisineException, PseudoInexistantDansLaPartieException, VilleInexistanteDansDeckJoueurException {
        Partie partie = this.parties.get(codePartie);
        String pseudoJoueurPartie = partie.aQuiLeTour();
        Joueur joueurPartie = partie.getJoueursPartie().get(pseudoJoueurPartie);
        switch (action) {
            case DEPLACEMENT:
                jouerActionDeplacement(joueurPartie, modeDeplacementChoisis, villeDestination);
                break;
            case TRAITER_MALADIE:
                break;
            case DECOUVRIR_REMEDE:
                break;
            case PARTAGER_CONNAISSANCE:
                break;
            case CONSTRUIRE_UNE_STATION:
                break;
        }
        partie.getJoueursPartie().put(joueurPartie.getPseudoJoueur(), joueurPartie);
        parties.put(partie.getCodePartie(), partie);
    }

    @Override
    public void jouerActionDeplacement(Joueur joueurPartie, ModesDeplacements modeDeplacementChoisis, Ville villeDestination) throws VilleAvecAucuneStationDeRechercheException, VilleNonVoisineException, VilleInexistanteDansDeckJoueurException {
        joueurPartie.choixDeplacement(modeDeplacementChoisis);
        joueurPartie.seDeplacer(villeDestination);
        partie.getJoueursPartie().put(pseudoJoueurPartie,joueurPartie);
        parties.put(codePartie,partie);

    }


    @Override
    public boolean estPartieTerminee(String pseudoJoueurPartie) throws CodePartieInexistantException {
        return false;
    }

}