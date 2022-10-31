package modele.elements.cartes.effets.evenements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import modele.elements.cartes.CartePropagation;
import modele.elements.cartes.effets.IEffetType;

@Getter
@Setter
@AllArgsConstructor
public class EffetTypePopulationResilienteImpl implements IEffetType {
    private CartePropagation cartePropagation;
}
