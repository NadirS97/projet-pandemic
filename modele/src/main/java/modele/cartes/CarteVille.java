package modele.cartes;

import lombok.Getter;
import modele.Ville;
import modele.Virus;

import java.util.List;

@Getter
public class CarteVille extends Carte {

    private Ville villeCarteVille;
    // Pour recuperer :
    // nomVille,
    // listeVirusVille (qui ne contiendra qu'un seul virus au départ, le virus qui de base est rattaché à la ville),
    // nbPopulationTotaleVille,
    // nbPopulationKmCarreeVille.

}
