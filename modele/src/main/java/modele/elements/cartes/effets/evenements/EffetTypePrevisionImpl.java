package modele.elements.cartes.effets.evenements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import modele.elements.Ville;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.effets.IEffetType;

@Getter
@Setter
@AllArgsConstructor
public class EffetTypePrevisionImpl implements IEffetType {
    private int choixCarte1;
    private int choixCarte2;
    private int choixCarte3;
    private int choixCarte4;
    private int choixCarte5;
    private int choixCarte6;
}
