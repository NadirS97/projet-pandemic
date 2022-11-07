package modele.elements.cartes;

import lombok.Getter;
import modele.elements.Ville;

@Getter
public class CarteVille extends CarteJoueur {

    private Ville villeCarteVille;

    public CarteVille(Ville villeCarteVille) {
        this.villeCarteVille = villeCarteVille;
    }

    @Override
    public String toString() {
        return "CarteVille{" +
                "villeCarteVille=" + villeCarteVille.getNomVille() +
                '}';
    }

}
