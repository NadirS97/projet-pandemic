package modele.elements.actions.deplacement;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;

public class DeplacementVolCharter implements Deplacement{

    /**
     * Défausser la carte ville correspondant à la ville où se trouve le pion pour atteindre n’importe quelle autre ville du plateau
     * Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondante à la villeActuelle
     * @param joueur
     * @param villeDestination
     * @return la nouvelle villeActuelle
     * @throws VilleInexistanteDansDeckJoueurException
     */
    @Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleInexistanteDansDeckJoueurException {
        if(joueur.isVilleOfCarteVilleDeckJoueur(joueur.getVilleActuelle())){
            joueur.setVilleActuelle(villeDestination);
        }
        joueur.defausseCarteVilleDeDeckJoueur(joueur.getVilleActuelle());
        return joueur.getVilleActuelle();
    }
}
