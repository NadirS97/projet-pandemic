package modele.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class DonneesPlateauDTO {
    private Set<VillesDTO> villes;
    private Set<VirusDTO> liste_virus;
}
