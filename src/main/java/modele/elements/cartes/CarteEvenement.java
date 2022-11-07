package modele.elements.cartes;

import lombok.Getter;
import lombok.ToString;
import modele.elements.cartes.evenements.IEvent;
import modele.elements.enums.NomsEvenement;
import modele.elements.cartes.effets.IEffetType;
import modele.exceptions.EffetManquantException;

import java.util.Optional;

@ToString
@Getter
public abstract class CarteEvenement extends CarteJoueur implements IEvent {

    private NomsEvenement nomEvenement;
    private String description;


    public NomsEvenement getNomEvennement() {
        return nomEvenement;
    }

    public String getDescription() {
        return description;
    }

//    public abstract void effet(Optional<IEffetType> effetType) throws Exception, EffetManquantException;

}
