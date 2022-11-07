package modele.elements.cartes.evenements;

import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.effets.evenements.EffetTypePopulationResilienteImpl;
import modele.elements.enums.NomsEvenement;
import modele.elements.cartes.effets.IEffetType;
import modele.exceptions.EffetManquantException;

import java.util.Optional;

public class PopulationResiliente extends CarteEvenement {

    private Plateau plateau;

    public PopulationResiliente(Plateau plateau) {
        this.plateau = plateau;
    }

    private final NomsEvenement NOMEVENEMENT = NomsEvenement.POPULATION_RESILIENTE;
    private final String DESCRIPTION = "Retirez du jeu 1 carte de votre choix de la défausse Propagation. (Vous pouvez jouer Population résiliente entre les étapes Infection et Intensification d'une carte Épidémie.)";

    @Override
    public NomsEvenement getNomEvennement() {
        return NOMEVENEMENT;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

//    @Override
//    public void effet(Optional<IEffetType> effetType) throws Exception, EffetManquantException {
//        if (effetType.isPresent()) {
//            EffetTypePopulationResilienteImpl effetTypePopulationResilienteImpl = (EffetTypePopulationResilienteImpl) effetType.get();
//            plateau.getDefausseCartePropagation().remove(effetTypePopulationResilienteImpl.getCartePropagation());
//        } else {
//            throw new EffetManquantException();
//        }
//    }

    @Override
    public void execEvent(PionJoueur pionJoueur) {

    }
}
