package modele.elements.cartes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class CarteRole {

    private String nomRole;
    private String couleurPionRole;
    private List<String> listeEffetsRole;

}
