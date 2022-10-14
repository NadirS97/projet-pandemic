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


    // Pour recuperer :
    // nomVille,
    // listeVirusVille (qui ne contiendra qu'un seul virus au départ, le virus qui de base est rattaché à la ville),
    // nbPopulationTotaleVille,
    // nbPopulationKmCarreeVille.

}
