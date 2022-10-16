package modele.elements.actions.deplacement;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;

public class DeplacementVolDirect implements Deplacement {

    /**
     * Défausser une carte ville pour déplacer le pion sur la ville de la carte défaussée
     * Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondant à la villeDestination
     * @param joueur
     * @param villeDestination
     * @return la nouvelle villeActuelle
     * @throws VilleInexistanteDansDeckJoueurException
     */
    @Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleInexistanteDansDeckJoueurException {
        if(joueur.isVilleOfCarteVilleDeckJoueur(villeDestination)){
            joueur.setVilleActuelle(villeDestination);
        }
        return joueur.getVilleActuelle();
    }

}
