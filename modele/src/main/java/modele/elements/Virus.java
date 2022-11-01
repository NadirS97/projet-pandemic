package modele.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import modele.elements.enums.EtatVirus;
import modele.utils.DonneesStatiques;


@Getter
@Setter
public class Virus {
    private String virusCouleur;
    private EtatVirus etatVirus;
    private int nbCubes;
    private DonneesStatiques donneesStatiques = new DonneesStatiques();


    public Virus(String virusCouleur, EtatVirus etatVirus) {
        this.virusCouleur = virusCouleur;
        this.etatVirus = etatVirus;
        nbCubes = donneesStatiques.getNbMaxCubesParVirus();
    }

   public int retirerCubes(int nbCubesRetirer){

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
