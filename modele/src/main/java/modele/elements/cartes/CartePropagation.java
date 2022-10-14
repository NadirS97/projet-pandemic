package modele.elements.cartes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import modele.elements.Ville;

@Getter
@AllArgsConstructor
public class CartePropagation  {

    private Ville villeCartePropagation;

    // Pour recuperer :
    // nomVille,    // listeVirusVille (qui ne contiendra qu'un seul virus au départ).

}
