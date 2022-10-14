package modele.elements.actions.deplacement;

import modele.elements.Ville;

public class DeplacementVolCharter implements Deplacement{


    @Override
    public void seDeplacer(Ville villeOrigine, Ville villeDestination) {
        System.out.println("VOL CHARTER from " + villeOrigine.getNomVille() + ", villeDestination : " + villeDestination.getNomVille());
    }
}
