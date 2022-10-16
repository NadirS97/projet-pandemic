package modele.elements.actions.deplacement;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.exceptions.VilleNonVoisineException;

public class DeplacementVoiture implements Deplacement {


    @Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleNonVoisineException {
       if (joueur.getPlateau().isVilleVoisine(joueur.getVilleActuelle(),villeDestination))
           joueur.setVilleActuelle(villeDestination);
       return joueur.getVilleActuelle();
    }
}
