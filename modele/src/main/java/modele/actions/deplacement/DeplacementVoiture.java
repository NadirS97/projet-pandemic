package modele.actions.deplacement;

import modele.Ville;

public class DeplacementVoiture implements Deplacement {


    @Override
    public void seDeplacer(Ville ville1, Ville ville2) {
        System.out.println("VROUM VROUM MA BENZ BENZ BENZ");
    }
}
