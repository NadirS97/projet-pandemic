package modele.actions.deplacement;

import modele.Ville;

public class DeplacementVoiture implements Deplacement {


    @Override
    public void seDeplacer(Ville villeOrigine, Ville villeDestination) {
        System.out.println("VROUM VROUM MA BENZ BENZ BENZ from" + villeOrigine.getNomVille() +" to " + villeDestination.getNomVille() );
    }
}
