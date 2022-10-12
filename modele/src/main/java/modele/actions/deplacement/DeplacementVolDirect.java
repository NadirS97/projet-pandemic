package modele.actions.deplacement;

import modele.Ville;

public class DeplacementVolDirect implements Deplacement {


    @Override
    public void seDeplacer(Ville villeOrigine, Ville villeDestination) {
        System.out.println("MACRON FLY from " + villeOrigine.getNomVille() + ", villeDestination : " + villeDestination.getNomVille() + " AVEC L'ARGENT DE NOS IMPOTS");
    }
}
