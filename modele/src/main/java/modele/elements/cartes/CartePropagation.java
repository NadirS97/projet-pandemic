package modele.elements.cartes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import modele.elements.Ville;

@Getter
@AllArgsConstructor

public class CartePropagation  {

    private Ville villeCartePropagation;


    // Pour recuperer :
    // nomVille,    // listeVirusVille (qui ne contiendra qu'un seul virus au départ).


    @Override
    public String toString() {
        return "CartePropagation{" +
                "villeCartePropagation=" + villeCartePropagation.getNomVille() +
                '}';
    }
}
