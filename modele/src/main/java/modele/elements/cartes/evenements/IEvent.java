package modele.elements.cartes.evenements;

import modele.elements.PionJoueur;
import modele.exceptions.CartePropagationNotInDefausseException;

public interface IEvent {

    void execEvent(PionJoueur pionJoueur) throws Exception;
}
