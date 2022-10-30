package modele.elements.cartes;

import lombok.Getter;
import lombok.ToString;
import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.enums.NomsEvenement;
import modele.exceptions.DeplacementRefuseException;
import modele.utils.IEffetType;

import java.util.Optional;

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

//    public abstract void effet(boolean autorisationDuJoueur, PionJoueur pionJoueur, Ville villeDestination) throws DeplacementRefuseException;

    public abstract void effet(Optional<IEffetType> effetType) throws Exception;
}
