package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;

public class DeplacementVolCharter implements Deplacement{

    /**
     * Défausser la carte ville correspondant à la ville où se trouve le pion pour atteindre n’importe quelle autre ville du plateau
     * Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondante à la villeActuelle
     * @param pionJoueur
     * @param villeDestination
     * @return la nouvelle villeActuelle
     * @throws VilleInexistanteDansDeckJoueurException
     */
    @Override
    public Ville seDeplacer(PionJoueur pionJoueur, Ville villeDestination) throws VilleInexistanteDansDeckJoueurException {
        if(pionJoueur.isVilleOfCarteVilleDeckJoueur(pionJoueur.getVilleActuelle())){
            pionJoueur.setVilleActuelle(villeDestination);
        }
        pionJoueur.defausseCarteVilleDeDeckJoueur(pionJoueur.getVilleActuelle());
        return pionJoueur.getVilleActuelle();
    }
}
