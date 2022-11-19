package modele.elements.cartes;

import modele.elements.PionJoueur;
import modele.exceptions.NbCubesAAjouterInvalideException;
import modele.exceptions.PropagationImpossibleCarSpecialisteQuarantaineException;
import modele.exceptions.VilleDejaEclosException;

public interface IEffet {

    void execEffet(PionJoueur pionJoueur) throws VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException;
}
