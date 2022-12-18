package modele.elements.cartes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.Ville;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartePropagation implements Serializable {

    private Ville villeCartePropagation;

    @Override
    public String toString() {
        return "CartePropagation{" + "villeCartePropagation=" + villeCartePropagation.getNomVille() + '}';
    }
}
