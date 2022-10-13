package modele.cartes;

import modele.enums.NomsEvenement;

public class CarteEvenement extends CarteJoueur {
    private NomsEvenement nomEvenement;

    public CarteEvenement(NomsEvenement nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    @Override
    String getNomCarte() {
        return nomCarte;
    }

    //TODO : Liste d'effets de carte

}
