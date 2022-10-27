package modele.action.deplacement;

import modele.action.IAction;
import modele.exceptions.VilleNonVoisineException;

public class DeplacementVoiture implements IAction {

    /**
     * Déplacer le pion du joueur entre deux villes reliées par une ligne
     * Pour cela on vérifie que la villeDestination est bien voisine de la villeActuelle
     * @param
     * @param
     * @return la nouvelle villeActuelle
     * @throws VilleNonVoisineException
     */
    /*@Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleNonVoisineException {
       if (joueur.getPlateau().isVilleVoisine(joueur.getVilleActuelle(),villeDestination))
           joueur.setVilleActuelle(villeDestination);
       return joueur.getVilleActuelle();
    }*/

    @Override
    public void execAction() {

    }
}
