package modele.elements.cartes;

import lombok.Getter;
import lombok.ToString;
import modele.elements.enums.NomsEvenement;
import modele.exceptions.AutorisationManquanteException;

@ToString
@Getter
public abstract class CarteEvenement extends CarteJoueur {
    private NomsEvenement nomEvenement;
    private String description;

    public NomsEvenement getNomEvennement() {
        return nomEvenement;
    }

    public String getDescription() {
        return description;
    }

    public abstract void effet();

    public abstract void effet(boolean autorisation);
}
