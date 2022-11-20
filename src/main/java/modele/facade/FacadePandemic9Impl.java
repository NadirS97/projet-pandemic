package modele.facade;


import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.Partie;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;

import modele.exceptions.*;
import modele.elements.PionJoueur;

import java.io.FileNotFoundException;
import java.util.List;

public class FacadePandemic9Impl implements FacadePandemic9 {

    Partie partie;

    @Override
    public void creerPartieDeuxJoueurs() throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        partie = Partie.creerPartieDeuxJoueurs();
    }
    @Override
    public void creerPartieTroisJoueurs() throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        partie = Partie.creerPartieTroisJoueurs();
    }
    @Override
    public void creerPartieQuatreJoueurs() throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        partie = Partie.creerPartieQuatreJoueurs();
    }

    @Override
    public void jouerTour(List<IAction> listeAction) throws EchecDeLaPartiePlusDeCarteJoueurException, CarteVilleInexistanteDansDeckJoueurException, NbCartesVilleDansDeckJoueurInvalideException, VirusDejaEradiqueException, VilleNonVoisineException, NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteEvenementNonTrouveDansDefausseException, JoueursNonPresentMemeVilleException, VirusDejaTraiteException, VilleIntrouvableException, VilleDestinationEstVilleActuelleException, MauvaisRoleException, VirusInexistantDansLaVilleActuelException, VilleAvecAucuneStationDeRechercheException, DonneeManquanteException, TropDeCarteEnMainException, NuitTranquilleException, VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, VictoireFinDePartieException, DefaitePartieTermineException {
        for (IAction action : listeAction){
            jouerAction(partie.getJoueurActuel(),action);
        }
            piocherCartes(partie.getJoueurActuel());
            propagation(partie.getJoueurActuel());

        if (partie.presenceMedecin())
            partie.effetCarteMedecin();

        partie.joueurSuivant();
        partie.isVictoire();

    }



    @Override
    public void jouerAction(PionJoueur joueurActuel, IAction action) throws CarteVilleInexistanteDansDeckJoueurException, NbCartesVilleDansDeckJoueurInvalideException, VirusDejaEradiqueException, VilleNonVoisineException, NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteEvenementNonTrouveDansDefausseException, JoueursNonPresentMemeVilleException, VirusDejaTraiteException, VilleIntrouvableException, VilleDestinationEstVilleActuelleException, MauvaisRoleException, VirusInexistantDansLaVilleActuelException, VilleAvecAucuneStationDeRechercheException, DonneeManquanteException {
        joueurActuel.setAction(action);
        joueurActuel.executerAction();
    }

    @Override
    public void jouerEvent(PionJoueur joueur, CarteEvenement carteEvenement) throws VilleDejaEclosException, CarteEvenementNotFoundInDeckException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, PermissionNonAccordeException, CartePropagationNotInDefausseException, DefaitePartieTermineException {

        joueur.jouerCarteEvenement(carteEvenement);
    }

    @Override
    public void piocherCartes(PionJoueur joueurActuel) throws TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException, VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, DefaitePartieTermineException {

            joueurActuel.piocherCartes();

    }

    public void defausserCartesJoueur(PionJoueur joueurActuel, List<CarteJoueur> cartesJoueursDefausser) throws CarteJoueurInexistanteDansDeckException {
        joueurActuel.defausserListeCarteJoueurEnTrop(cartesJoueursDefausser);
    }

    @Override
    public void propagation(PionJoueur joueurActuel) throws VilleDejaEclosException, NuitTranquilleException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, DefaitePartieTermineException {
        joueurActuel.getPlateau().initialiserPropagation();
    }

    @Override
    public void repartiteurActionDeplacementAutrePion(PionJoueur joueurActuel, PionJoueur joueurCible, IAction action) throws AutorisationManquanteException, CarteVilleInexistanteDansDeckJoueurException, NbCartesVilleDansDeckJoueurInvalideException, VirusDejaEradiqueException, VilleNonVoisineException, NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteEvenementNonTrouveDansDefausseException, JoueursNonPresentMemeVilleException, ActionNonDeplacementException, VirusDejaTraiteException, VilleIntrouvableException, VilleDestinationEstVilleActuelleException, MauvaisRoleException, VirusInexistantDansLaVilleActuelException, VilleAvecAucuneStationDeRechercheException, DonneeManquanteException {
        joueurActuel.setAction(action);
        joueurActuel.repartiteurActionDeplacementAutrePion(joueurCible);
    }

    public void repartiteurDeplacementPion(PionJoueur joueurActuel,PionJoueur joueurCible, Ville villeDestination) throws AucunJoueurDansVilleDestinationException, AutorisationManquanteException {
        joueurActuel.repartiteurDeplacementPion(joueurCible,villeDestination);
    }

    }
