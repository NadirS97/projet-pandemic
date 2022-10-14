package modele.elements.cartes;

import lombok.Getter;
import lombok.ToString;
import modele.elements.enums.NomsEvenement;

@ToString
@Getter
public class CarteEvenement extends CarteJoueur {
    private NomsEvenement nomEvenement;

    public CarteEvenement(NomsEvenement nomEvenement) {
        this.nomEvenement = nomEvenement;
    }



    //TODO : Liste d'effets de carte

}
