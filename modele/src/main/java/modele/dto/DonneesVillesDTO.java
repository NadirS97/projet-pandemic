package modele.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DonneesVillesDTO {
    private List<VillesDTO> villes_bleues;
    private List<VillesDTO> villes_jaunes;
    private List<VillesDTO> villes_rouges;
    private List<VillesDTO> villes_noires;
}
