package modele.elements.actions.deplacement;

import modele.elements.Ville;

public class DeplacementNavette implements Deplacement{


    @Override
    public void seDeplacer(Ville villeOrigine, Ville villeDestination) {
        System.out.println("NAVETTE FROM" + villeOrigine.getNomVille() +" to " + villeDestination.getNomVille() );
    }
}
