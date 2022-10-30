package modele.elements.action.deplacement;

import modele.elements.PionJoueur;
import modele.elements.action.IAction;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;

public class DeplacementVolCharter implements IAction {

    /**
     * Défausser la carte ville correspondant à la ville où se trouve le pion pour atteindre n’importe quelle autre ville du plateau
     * Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondante à la villeActuelle
     *
     * @param
     * @param
     * @param pionJoueur
     * @return la nouvelle villeActuelle
     * @throws CarteVilleInexistanteDansDeckJoueurException
     */
  /*  @Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleInexistanteDansDeckJoueurException {
        if(joueur.isVilleOfCarteVilleDeckJoueur(joueur.getVilleActuelle())){
            joueur.setVilleActuelle(villeDestination);
        }
        joueur.defausseCarteVilleDeDeckJoueur(joueur.getVilleActuelle());
        return joueur.getVilleActuelle();
    }
*/
    @Override
    public void execAction(PionJoueur pionJoueur) {

    }
}
