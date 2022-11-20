package modele.elements.actions;

import modele.elements.PionJoueur;
import modele.exceptions.*;

public interface IAction {

    void execAction(PionJoueur pionJoueur) throws VilleDestinationEstVilleActuelleException,
            NbActionsMaxTourAtteintException, VilleIntrouvableException, CarteVilleInexistanteDansDeckJoueurException,
            VilleNonVoisineException, VilleAvecAucuneStationDeRechercheException, VirusDejaTraiteException,
            NbCartesVilleDansDeckJoueurInvalideException, VilleActuellePossedeDejaUneStationDeRechercheException,
            JoueursNonPresentMemeVilleException, DonneeManquanteException,
            MauvaisRoleException, CarteEvenementNonTrouveDansDefausseException,
            VirusInexistantDansLaVilleActuelException, VirusDejaEradiqueException;
}
