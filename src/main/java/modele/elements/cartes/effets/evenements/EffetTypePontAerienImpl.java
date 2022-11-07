package modele.elements.cartes.effets.evenements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.cartes.effets.IEffetType;

@Getter
@Setter
@AllArgsConstructor
public class EffetTypePontAerienImpl implements IEffetType {
    private boolean autorisationDuJoueur;
    private PionJoueur pionJoueur;
    private Ville villeDestination;
}
