package modele.facade;

import modele.exceptions.CasCouleurVilleIncorrectException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VilleNonVoisineException;
import modele.elements.Joueur;

public interface FacadePandemic9 {
    void initialisation() throws CasCouleurVilleIncorrectException;

    void jouerTour(Joueur joueur) throws VilleIntrouvableException, VilleNonVoisineException;
}
