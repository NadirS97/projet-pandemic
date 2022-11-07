package modele.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class VillesDTO {

    private String nomVille;
    private int populationTotaleVille;
    private int populationKmCarreVille;
    private List<String> listeNomsVillesVoisines;
    private String couleurVirusVille;

}