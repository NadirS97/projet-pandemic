package modele.elements.cartes.evenements;

import modele.elements.Plateau;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.effets.evenements.EffetTypeParUneNuitTranquilleImpl;
import modele.elements.enums.NomsEvenement;
import modele.elements.cartes.effets.IEffetType;
import modele.exceptions.EffetManquantException;

import java.util.Optional;

public class ParUneNuitTranquille extends CarteEvenement {

    private Plateau plateau;

    public ParUneNuitTranquille(Plateau plateau) {
        this.plateau = plateau;
    }

    private final NomsEvenement NOMEVENEMENT = NomsEvenement.PAR_UNE_NUIT_TRANQUILE;
    private final String DESCRIPTION = "Ne faites pas la prochaine phase Propagation des maladies (ne dévoilez aucune carte Propagation).";

    @Override
    public NomsEvenement getNomEvennement() {
        return NOMEVENEMENT;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void effet(Optional<IEffetType> effetType) throws Exception, EffetManquantException {
        if (effetType.isPresent()) {
            EffetTypeParUneNuitTranquilleImpl effetTypeParUneNuitTranquilleImpl = (EffetTypeParUneNuitTranquilleImpl) effetType.get();
            plateau.setEffetParUneNuitTranquilleActif(true);
        } else {
            throw new EffetManquantException();
        }
    }
}
