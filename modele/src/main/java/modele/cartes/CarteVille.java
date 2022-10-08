package modele.cartes;

import lombok.Getter;
import modele.Ville;

@Getter
public class CarteVille extends CarteJoueur {

    private Ville villeCarteVille;
    // Pour recuperer :
    // nomVille,
    // listeVirusVille (qui ne contiendra qu'un seul virus au départ, le virus qui de base est rattaché à la ville),
    // nbPopulationTotaleVille,
    // nbPopulationKmCarreeVille.

}
