package modele.facade;

import modele.exceptions.*;
import modele.elements.Joueur;

public interface FacadePandemic9 {
    void initialisation() throws CasCouleurVilleIncorrectException;

    void jouerTour(Joueur joueur) throws VilleIntrouvableException, VilleNonVoisineException, VilleInexistanteDansDeckJoueurException, VilleAvecAucuneStationDeRechercheException;
}
