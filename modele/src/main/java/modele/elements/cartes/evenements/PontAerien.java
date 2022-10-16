package modele.elements.cartes.evenements;

import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;

public class PontAerien extends CarteEvenement {
    private final String DESCRIPTION = "Déplacez un pion quelconque sur la ville de votre choix. Vous devez avoir la permission du propriétaire du pion qui sera déplacé.";

    public PontAerien(NomsEvenement nomEvenement) {
        super(nomEvenement);
    }

    @Override
    public void effet() {
        //effet
    }
}
