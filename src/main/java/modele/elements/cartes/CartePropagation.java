package modele.elements.cartes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import modele.elements.Ville;

@Getter
@AllArgsConstructor
public class CartePropagation {

    private Ville villeCartePropagation;

    @Override
    public String toString() {
        return "CartePropagation{" + "villeCartePropagation=" + villeCartePropagation.getNomVille() + '}';
    }
}
