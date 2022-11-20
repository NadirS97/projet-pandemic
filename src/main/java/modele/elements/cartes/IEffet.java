package modele.elements.cartes;

import modele.elements.PionJoueur;
import modele.exceptions.*;

public interface IEffet {

    void execEffet(PionJoueur pionJoueur) throws VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, CartePropagationNotInDefausseException, PermissionNonAccordeException, DefaitePartieTermineException;
}
