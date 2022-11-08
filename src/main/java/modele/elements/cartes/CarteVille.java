package modele.elements.cartes;

import lombok.Builder;
import lombok.Getter;
import modele.elements.Ville;

@Getter
public class CarteVille extends CarteJoueur {

    private Ville villeCarteVille;
    private String nomCarte;

    public CarteVille(Ville villeCarteVille) {
        this.villeCarteVille = villeCarteVille;
        this.nomCarte = villeCarteVille.getNomVille();
    }

    @Override
    public String toString() {
        return "CarteVille{" +
                "villeCarteVille=" + villeCarteVille.getNomVille() +
                '}';
    }

}
