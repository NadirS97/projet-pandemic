package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.exceptions.VilleNonVoisineException;

public class DeplacementVoiture implements Deplacement {

    /**
     * Déplacer le pion du joueur entre deux villes reliées par une ligne
     * Pour cela on vérifie que la villeDestination est bien voisine de la villeActuelle
     * @param pionJoueur
     * @param villeDestination
     * @return la nouvelle villeActuelle
     * @throws VilleNonVoisineException
     */
    @Override
    public Ville seDeplacer(PionJoueur pionJoueur, Ville villeDestination) throws VilleNonVoisineException {
       if (pionJoueur.getPlateau().isVilleVoisine(pionJoueur.getVilleActuelle(),villeDestination))
           pionJoueur.setVilleActuelle(villeDestination);
       return pionJoueur.getVilleActuelle();
    }

}
