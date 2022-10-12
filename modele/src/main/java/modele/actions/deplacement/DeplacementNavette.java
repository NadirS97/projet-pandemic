package modele.actions.deplacement;

import modele.Ville;

public class DeplacementNavette implements Deplacement{


    @Override
    public void seDeplacer(Ville villeOrigine, Ville villeDestination) {
        System.out.println("NAVETTE FROM" + villeOrigine.getNomVille() +" to " + villeDestination.getNomVille() );
    }
}
