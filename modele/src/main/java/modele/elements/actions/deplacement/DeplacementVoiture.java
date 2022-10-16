package modele.elements.actions.deplacement;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.exceptions.VilleNonVoisineException;

public class DeplacementVoiture implements Deplacement {

    /**
     * Déplacer le pion du joueur entre deux villes reliées par une ligne
     * Pour cela on vérifie que la villeDestination est bien voisine de la villeActuelle
     * @param joueur
     * @param villeDestination
     * @return la nouvelle villeActuelle
     * @throws VilleNonVoisineException
     */
    @Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleNonVoisineException {
       if (joueur.getPlateau().isVilleVoisine(joueur.getVilleActuelle(),villeDestination))
           joueur.setVilleActuelle(villeDestination);
       return joueur.getVilleActuelle();
    }

}
