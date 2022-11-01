package modele.facade;

import modele.elements.actions.IAction;
import modele.elements.Partie;
import modele.elements.Plateau;
import modele.exceptions.*;
import modele.elements.PionJoueur;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FacadePandemic9Impl implements FacadePandemic9 {

    private Map<String, Partie> parties;

    public FacadePandemic9Impl() {
        this.parties = new HashMap<>();
    }

    @Override
    public String creerPartie(String pseudoJoueurPartie) throws CasCouleurVilleIncorrectException {
        Partie partie = new Partie(pseudoJoueurPartie);
        parties.put(partie.getCodePartie(), partie);
        return partie.getCodePartie();
    }

    @Override
    public void rejoindrePartie(String codePartie, String pseudoJoueurPartie) throws PseudoDejaExistantException, CodePartieInexistantException, DonneManquanteException {
        if (Objects.isNull(parties.get(codePartie)))
            throw new CodePartieInexistantException();
        if (parties.get(codePartie).isJoueurDejaDansPartie(pseudoJoueurPartie))
            throw new PseudoDejaExistantException();

        Plateau plateau = parties.get(codePartie).getPlateauPartie();
        PionJoueur nouveauPionJoueur = new PionJoueur(pseudoJoueurPartie, plateau,4);
        parties.get(codePartie).getJoueursPartie().put(pseudoJoueurPartie, nouveauPionJoueur);
    }

//    @Override
//    public void jouerTour(String codePartie, Actions action, ModesDeplacements modeDeplacementChoisis, Ville villeDestination) throws VilleAvecAucuneStationDeRechercheException, VilleNonVoisineException, PseudoInexistantDansLaPartieException, VilleInexistanteDansDeckJoueurException, ModeDeplacementInexistantException, VilleActuellePossedeDejaUneStationDeRechercheException {
//        Partie partie = this.parties.get(codePartie);
//        String pseudoJoueurPartie = partie.aQuiLeTour();
//        PionJoueur pionJoueurPartie = partie.getJoueursPartie().get(pseudoJoueurPartie);
//        int nbActions = 0;
//        while (nbActions < 4){
//            switch (action) {
//                case DEPLACEMENT:
//                    if (!Objects.isNull(modeDeplacementChoisis)){
//                        nbActions++;
//                    }
//                    break;
//                case TRAITER_MALADIE:
//
//                    nbActions++;
//                    break;
//                case DECOUVRIR_REMEDE:
//
//                    nbActions++;
//                    break;
//                case PARTAGER_CONNAISSANCE:
//
//                    nbActions++;
//                    break;
//                case CONSTRUIRE_UNE_STATION:
//                    if(partie.getPlateauPartie().getNbStationsDeRechercheConstruites() < 6) {
//                        pionJoueurPartie.construireStation();
//                        pionJoueurPartie.defausseCarteVilleDeDeckJoueur(pionJoueurPartie.getVilleActuelle());
//                    }else{
//                        pionJoueurPartie.deplacerStationDeRecherche(villeDestination);
//                    }
//                    nbActions++;
//                    break;
//            }
//        }
//        partie.getJoueursPartie().put(pionJoueurPartie.getPseudoJoueur(), pionJoueurPartie);
//        parties.put(partie.getCodePartie(), partie);
//    }

//    public void JouerTourr(String codePartie, IAction action){
//        Partie partie = this.parties.get(codePartie);
//        String pseudoJoueurPartie = partie.aQuiLeTour();
//        PionJoueur pionJoueurPartie = partie.getJoueursPartie().get(pseudoJoueurPartie);
//        pionJoueurPartie.setAction(action);
//        pionJoueurPartie.executerAction();
//    }

    @Override
    public void jouerTour(PionJoueur joueurActuel, IAction action) throws Exception {
        joueurActuel.setAction(action);
        joueurActuel.executerAction();
    }

    @Override
    public boolean estPartieTerminee(String pseudoJoueurPartie) throws CodePartieInexistantException {
        return false;
    }
}