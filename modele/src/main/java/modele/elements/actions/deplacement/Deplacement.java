package modele.elements.actions.deplacement;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;
import modele.exceptions.VilleNonVoisineException;

public interface Deplacement {

    Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleNonVoisineException, VilleAvecAucuneStationDeRechercheException, VilleInexistanteDansDeckJoueurException;

}
