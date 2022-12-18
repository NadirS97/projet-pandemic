package modele.elements.cartes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import modele.elements.enums.NomsEvenement;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
public abstract class CarteEvenement extends CarteJoueur implements IEffet {
    private NomsEvenement nomEvenement;
    private String description;
}
