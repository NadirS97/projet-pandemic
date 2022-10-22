package modele.elements.cartes.evenements;

import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;

public class ParUneNuitTranquille extends CarteEvenement {

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
    public void effet() {
        //effet
    }
}
