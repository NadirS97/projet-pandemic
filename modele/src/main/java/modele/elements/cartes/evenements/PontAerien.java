package modele.elements.cartes.evenements;

import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;

public class PontAerien extends CarteEvenement {

    private final NomsEvenement NOMEVENEMENT = NomsEvenement.PONT_AERIEN;
    private final String DESCRIPTION = "Déplacez un pion quelconque sur la ville de votre choix. Vous devez avoir la permission du propriétaire du pion qui sera déplacé.";

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
    public void effet(boolean autorisation) {
        if (autorisation) {

        } else {

        }
    }
}
