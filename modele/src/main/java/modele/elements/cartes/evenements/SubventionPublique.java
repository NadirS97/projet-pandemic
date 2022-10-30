package modele.elements.cartes.evenements;

import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;
import modele.elements.cartes.effets.IEffetType;

import java.util.Optional;

public class SubventionPublique extends CarteEvenement {

    private final NomsEvenement NOMEVENEMENT = NomsEvenement.SUBVENTION_PUBLIQUE;
    private final String DESCRIPTION = "Placez 1 station de recherche dans la ville de votre choix (sans avoir à défausser une carte Ville).";

    @Override
    public NomsEvenement getNomEvennement() {
        return NOMEVENEMENT;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void effet() {

    }

    @Override
    public void effet(Optional<IEffetType> effetType) throws Exception {

    }

//    @Override
//    public void effet(boolean autorisationDuJoueur, PionJoueur pionJoueur, Ville villeDestination) throws DeplacementRefuseException {
//
//    }
}
