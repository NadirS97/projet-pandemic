package modele.facade;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.cartes.CarteJoueur;
import modele.exceptions.*;

import java.util.Set;

public class FacadeActionsJoueurImpl implements FacadeActionsJoueur {


    @Override
    public Ville actionSeDeplacerVoiture(PionJoueur pionJoueur, Ville villeDestination) throws VilleIntrouvableException, VilleNonVoisineException, NbActionsMaxTourAtteintException {
        return pionJoueur.actionSeDeplacerVoiture(villeDestination);
    }

    @Override
    public Ville actionSeDeplacerVolDirect(PionJoueur pionJoueur, Ville villeDestination) throws VilleIntrouvableException, CarteVilleInexistanteDansDeckJoueurException, NbActionsMaxTourAtteintException {
        return pionJoueur.actionSeDeplacerVolDirect(villeDestination);
    }

    @Override
    public Ville actionSeDeplacerVolCharter(PionJoueur pionJoueur, Ville villeDestination) throws VilleIntrouvableException, NbActionsMaxTourAtteintException, CarteVilleInexistanteDansDeckJoueurException {
        return pionJoueur.actionSeDeplacerVolCharter(villeDestination);
    }

    @Override
    public Ville actionSeDeplacerNavette(PionJoueur pionJoueur, Ville villeDestination) throws VilleAvecAucuneStationDeRechercheException, VilleIntrouvableException, NbActionsMaxTourAtteintException {
        return null;
    }

    @Override
    public Ville actionConstruireStationRecherche(PionJoueur pionJoueur) throws NbMaxStationRechercheException, NbActionsMaxTourAtteintException {
        return null;
    }

    @Override
    public Ville actionDeplacerStationRecherche(PionJoueur pionJoueur, Ville villeStationCible) throws VilleIntrouvableException, NbActionsMaxTourAtteintException {
        return null;
    }

    @Override
    public Ville actionTraiterUneMaladieNonTraite(PionJoueur pionJoueur, Ville villeTraite) throws VilleIntrouvableException, NbActionsMaxTourAtteintException {
        return null;
    }

    @Override
    public Ville actionTraiterUneMaladieTraite(PionJoueur pionJoueur, Ville villeTraite) throws VilleIntrouvableException, NbActionsMaxTourAtteintException {
        return null;
    }

    @Override
    public Ville actionDonnerAuJoueurCarteVille(PionJoueur pionJoueur1, PionJoueur pionJoueur2) throws CarteVilleInexistanteDansDeckJoueurException, NbActionsMaxTourAtteintException {
        return null;
    }

    @Override
    public Ville actionPrendreAuJoueurCarteVille(PionJoueur pionJoueur1, PionJoueur pionJoueur2) throws CarteVilleInexistanteDansDeckJoueurException, NbActionsMaxTourAtteintException {
        return null;
    }

    @Override
    public Set<CarteJoueur> actionDecouvrirRemede(PionJoueur pionJoueur) throws PasAssezCarteMemeCouleurException, NbActionsMaxTourAtteintException {
        return null;
    }
}
