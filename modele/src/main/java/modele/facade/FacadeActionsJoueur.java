package modele.facade;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.cartes.CarteJoueur;
import modele.exceptions.*;

import java.util.Set;

public interface FacadeActionsJoueur {


    /**
     *
     * @param pionJoueur possède en attribut la ville actuelle où il se trouve
     * @param villeDestination doit être voisine de la ville actuelle
     * @return la Ville Destination si elle est atteinte
     * @throws VilleIntrouvableException
     * @throws VilleNonVoisineException
     * @throws NbActionsMaxTourAtteintException
     */
    Ville actionSeDeplacerVoiture(PionJoueur pionJoueur, Ville villeDestination)
        throws VilleIntrouvableException,
            VilleNonVoisineException,
            NbActionsMaxTourAtteintException;

/*
    Différence entre vol Direct et Vol Charter
    Vol direct : défausse une carte ville pour l'atteindre
    Vol Charter : défausser la carte ville où se trouve le pion pour se déplacer sur N'IMPORTE quelle viles
 */
    Ville actionSeDeplacerVolDirect(PionJoueur pionJoueur, Ville villeDestination)
            throws VilleIntrouvableException,
            CarteVilleInexistanteDansDeckJoueurException,
            NbActionsMaxTourAtteintException;

    Ville actionSeDeplacerVolCharter(PionJoueur pionJoueur, Ville villeDestination)
            throws VilleIntrouvableException,
            CarteVilleNonCorrespondVilleActuelleException,
            NbActionsMaxTourAtteintException, CarteVilleInexistanteDansDeckJoueurException;

    Ville actionSeDeplacerNavette(PionJoueur pionJoueur, Ville villeDestination)
        throws VilleAvecAucuneStationDeRechercheException,
            VilleIntrouvableException,
            NbActionsMaxTourAtteintException;

    Ville actionConstruireStationRecherche(PionJoueur pionJoueur)
        throws NbMaxStationRechercheException,
            NbActionsMaxTourAtteintException;


    Ville actionDeplacerStationRecherche(PionJoueur pionJoueur, Ville villeStationCible)
        throws VilleIntrouvableException,
            NbActionsMaxTourAtteintException;

    Ville actionTraiterUneMaladieNonTraite(PionJoueur pionJoueur, Ville villeTraite)
        throws VilleIntrouvableException,
            NbActionsMaxTourAtteintException;

    // si dernier cube d'une couleur est supprimé, alors maladie éradiquée
    Ville actionTraiterUneMaladieTraite(PionJoueur pionJoueur, Ville villeTraite)
        throws VilleIntrouvableException,
            NbActionsMaxTourAtteintException;

    Ville actionDonnerAuJoueurCarteVille(PionJoueur pionJoueur1,PionJoueur pionJoueur2)
        throws CarteVilleInexistanteDansDeckJoueurException,
            NbActionsMaxTourAtteintException;

    Ville actionPrendreAuJoueurCarteVille(PionJoueur pionJoueur1, PionJoueur pionJoueur2)
        throws CarteVilleInexistanteDansDeckJoueurException,
            NbActionsMaxTourAtteintException;

    Set<CarteJoueur> actionDecouvrirRemede(PionJoueur pionJoueur)
        throws PasAssezCarteMemeCouleurException,
            NbActionsMaxTourAtteintException;
}
