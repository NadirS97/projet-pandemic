package modele.cartes;

import lombok.Getter;
import lombok.ToString;
import modele.enums.NomsEvenement;

@ToString
@Getter
public class CarteEvenement extends CarteJoueur {
    private NomsEvenement nomEvenement;

    public CarteEvenement(NomsEvenement nomEvenement) {
        this.nomEvenement = nomEvenement;
    }



    //TODO : Liste d'effets de carte

}
