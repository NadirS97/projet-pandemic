package modele.elements.actions;

import modele.elements.PionJoueur;
import modele.exceptions.CarteEvenementNonTrouveDansDefausseException;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VilleNonVoisineException;

public interface IAction {

    void execAction(PionJoueur pionJoueur) throws Exception, CarteEvenementNonTrouveDansDefausseException;
}
