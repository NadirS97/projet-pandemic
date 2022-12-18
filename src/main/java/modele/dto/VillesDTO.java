package modele.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VillesDTO implements Serializable {

    private String nomVille;
    private int populationTotaleVille;
    private int populationKmCarreVille;
    private List<String> listeNomsVillesVoisines;
    private String couleurVirusVille;

}