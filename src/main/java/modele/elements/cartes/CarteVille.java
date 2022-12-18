package modele.elements.cartes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.Ville;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
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
