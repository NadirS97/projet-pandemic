package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;

public class DeplacementVolDirect implements Deplacement {

    /**
     * Défausser une carte ville pour déplacer le pion sur la ville de la carte défaussée
     * Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondant à la villeDestination
     * @param pionJoueur
     * @param villeDestination
     * @return la nouvelle villeActuelle
     * @throws VilleInexistanteDansDeckJoueurException
     */
    @Override
    public Ville seDeplacer(PionJoueur pionJoueur, Ville villeDestination) throws VilleInexistanteDansDeckJoueurException {
        if(pionJoueur.isVilleOfCarteVilleDeckJoueur(villeDestination)){
            pionJoueur.setVilleActuelle(villeDestination);
        }
        pionJoueur.defausseCarteVilleDeDeckJoueur(villeDestination);
        return pionJoueur.getVilleActuelle();
    }

}
