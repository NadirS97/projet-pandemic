package modele.action.deplacement;

import modele.action.IAction;
import modele.elements.PionJoueur;
import modele.elements.Ville;


public class DeplacementVolDirect implements IAction {


    PionJoueur pionJoueur;
    Ville villeDestination;

    public DeplacementVolDirect(PionJoueur pionJoueur, Ville villeDestination) {
        this.pionJoueur = pionJoueur;
        this.villeDestination = villeDestination;
    }

    @Override
    public void execAction() {


    }
}
