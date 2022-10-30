package modele.elements.cartes;

import lombok.*;
import modele.elements.cartes.effets.IEffetType;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.util.Optional;

@Getter
@Setter
@ToString
public abstract class CarteRole {

    private NomsRoles nomRole;
    private CouleurPionsRole couleurPionRole;
    private String descriptionRole;

    public CarteRole(CouleurPionsRole couleurPionRole) {
        this.couleurPionRole = couleurPionRole;
    }

    public abstract void effet(Optional<IEffetType> effetType) throws Exception;


}
