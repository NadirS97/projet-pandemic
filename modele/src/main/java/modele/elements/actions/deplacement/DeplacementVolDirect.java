package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleIntrouvableException;

public class DeplacementVolDirect implements IAction {

    private Ville villeDestination;


    public DeplacementVolDirect(Ville villeDestination) {
        this.villeDestination = villeDestination;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException();
        if (!pionJoueur.getPlateau().isVille(villeDestination.getNomVille()))
            throw new VilleIntrouvableException(villeDestination.getNomVille()+"non trouvé");
        if (!pionJoueur.isVilleOfCarteVilleDeckJoueur(villeDestination))
            throw new CarteVilleInexistanteDansDeckJoueurException();
        pionJoueur.defausseCarteVilleDeDeckJoueur(villeDestination);
        pionJoueur.setVilleActuelle(villeDestination);
    }
}
