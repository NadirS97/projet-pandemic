package modele.elements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.enums.EtatVirus;
import modele.utils.DonneesVariablesStatiques;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Virus implements Serializable {

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

    public int rajouterCubesSac(int nbCubesRajoute){
        return nbCubes = nbCubes + nbCubesRajoute;
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
