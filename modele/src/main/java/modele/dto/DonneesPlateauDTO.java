package modele.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class DonneesPlateauDTO {

    private Set<VillesDTO> villes;
    private Set<VirusDTO> liste_virus;
    private Set<PionsDTO> liste_pions;
    private Set<EffetsDTO> liste_effets;
    private Set<RolesDTO> cartes_role;
    private Set<CartesDTO> types_carte;

}
