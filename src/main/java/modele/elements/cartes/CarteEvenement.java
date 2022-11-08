package modele.elements.cartes;

import lombok.Getter;
import lombok.ToString;
import modele.elements.PionJoueur;
import modele.elements.enums.NomsEvenement;

import java.util.LinkedList;

@ToString
@Getter
public abstract class CarteEvenement extends CarteJoueur implements IEffet {
    private NomsEvenement nomEvenement;
    private String nomCarte;
    private String description;

    public String getDescription() {
        return description;
    }
}
