package modele.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import modele.elements.enums.EtatVirus;


@Getter
@Setter
@ToString
public class Virus {
    private String virusCouleur;
    private EtatVirus etatVirus;
    private int nbCubes;


    public Virus(String virusCouleur, EtatVirus etatVirus) {
        this.virusCouleur = virusCouleur;
        this.etatVirus = etatVirus;
        nbCubes = 24;
    }

   public void retirerCubes(int nbCubesRetirer){
        nbCubes = nbCubes- nbCubesRetirer;
   }
}
