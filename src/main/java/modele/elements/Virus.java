package modele.elements;

import lombok.Getter;
import lombok.Setter;
import modele.elements.enums.EtatVirus;
import modele.utils.DonneesVariablesStatiques;

@Getter
@Setter
public class Virus {

    private String virusCouleur;
    private EtatVirus etatVirus;
    private int nbCubes;

    public Virus(String virusCouleur, EtatVirus etatVirus) {
        this.virusCouleur = virusCouleur;
        this.etatVirus = etatVirus;
        nbCubes = DonneesVariablesStatiques.nbCubesExistantParVirus;
    }

   public int retirerCubesSac(int nbCubesRetirer){

        return nbCubes = nbCubes- nbCubesRetirer;
   }

    @Override
    public String toString() {
        return "Virus{" +
                "virusCouleur='" + virusCouleur + '\'' +
                ", etatVirus=" + etatVirus +
                ", nbCubes=" + nbCubes +
                '}';
    }
}
