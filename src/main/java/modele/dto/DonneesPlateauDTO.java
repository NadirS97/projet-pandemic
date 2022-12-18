package modele.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class DonneesPlateauDTO implements Serializable {

    private Set<VillesDTO> villes;
    private Set<VirusDTO> liste_virus;

}
