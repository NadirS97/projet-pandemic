package modele.action.deplacement;

import modele.action.IAction;
import modele.elements.PionJoueur;
import modele.elements.Ville;

public class DeplacementVoiture implements IAction {

    PionJoueur pionJoueur;
    Ville villeDestination;

    public DeplacementVoiture(PionJoueur pionJoueur, Ville villeDestination) {
        this.pionJoueur = pionJoueur;
        this.villeDestination = villeDestination;
    }



    @Override
    public void execAction() {

        pionJoueur.setVilleActuelle(this.villeDestination);
    }
}
