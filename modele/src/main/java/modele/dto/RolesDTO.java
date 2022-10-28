package modele.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RolesDTO {

    private String nomRole;
    private String couleurPionRole;
    private List<String> listeEffetsRole;

}
