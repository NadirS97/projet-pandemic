package modele.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import modele.elements.PionJoueur;
import modele.elements.Ville;

@Getter
@Setter
@AllArgsConstructor
public class EffetTypePontAerienImpl implements IEffetType{

    private boolean autorisationDuJoueur;
    private PionJoueur pionJoueur;
    private Ville villeDestination;

}
