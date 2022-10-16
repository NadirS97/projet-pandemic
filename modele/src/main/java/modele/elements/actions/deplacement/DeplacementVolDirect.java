package modele.elements.actions.deplacement;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;

public class DeplacementVolDirect implements Deplacement {

    @Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleInexistanteDansDeckJoueurException {
        // Défausser une carte ville pour déplacer le pion sur la ville de la carte défaussée
        // Pour cela on vérifie que le Joueur possède dans sa main/son deck la carte Ville correspondant à la villeDestination
        if(joueur.isVilleOfCarteVilleDeckJoueur(villeDestination)){
            joueur.setVilleActuelle(villeDestination);
        }
        return joueur.getVilleActuelle();
    }

}
