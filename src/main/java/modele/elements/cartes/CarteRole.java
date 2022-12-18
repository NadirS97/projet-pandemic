package modele.elements.cartes;

import lombok.*;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class CarteRole implements Serializable {

    private NomsRoles nomRole;
    private CouleurPionsRole couleurPionRole;
    private String descriptionRole;

    public CarteRole(CouleurPionsRole couleurPionRole) {
        this.couleurPionRole = couleurPionRole;
    }

}
