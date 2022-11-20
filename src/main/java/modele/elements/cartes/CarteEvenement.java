package modele.elements.cartes;

import lombok.Getter;
import lombok.ToString;
import modele.elements.enums.NomsEvenement;

@ToString
@Getter
public abstract class CarteEvenement extends CarteJoueur implements IEffet {
    private NomsEvenement nomEvenement;
    private String description;
    public String getDescription() {
        return description;
    }
}
