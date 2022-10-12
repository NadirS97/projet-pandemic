package modele.cartes;

import modele.enums.NomsEvenement;

public class CarteEvenement extends CarteJoueur {
    private NomsEvenement nomEvenement;

    @Override
    String getNomCarte() {
        return nomCarte;
    }

    //TODO : Liste d'effets de carte
}
