package modele.elements.actions.deplacement;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;

public class DeplacementVolCharter implements Deplacement{

    @Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleInexistanteDansDeckJoueurException {
        // Défausser la carte ville correspondant à la ville où se trouve le pion pour atteindre n’importe quelle autre ville du plateau
        // Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondante à la villeActuelle
        if(joueur.isVilleOfCarteVilleDeckJoueur(joueur.getVilleActuelle())){
            joueur.setVilleActuelle(villeDestination);
        }
        return joueur.getVilleActuelle();
    }
}
