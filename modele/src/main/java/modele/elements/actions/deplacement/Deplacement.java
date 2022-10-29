package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;
import modele.exceptions.VilleNonVoisineException;

public interface Deplacement {

    Ville seDeplacer(PionJoueur pionJoueur, Ville villeDestination) throws VilleNonVoisineException, VilleAvecAucuneStationDeRechercheException, VilleInexistanteDansDeckJoueurException;

}
