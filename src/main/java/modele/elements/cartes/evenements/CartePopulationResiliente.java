package modele.elements.cartes.evenements;

import lombok.Getter;
import modele.elements.PionJoueur;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CartePropagation;
import modele.elements.enums.NomsEvenement;
import modele.exceptions.CartePropagationNotInDefausseException;

@Getter
public class CartePopulationResiliente extends CarteEvenement {

   private CartePropagation cartePropagationChoisis;

    public CartePopulationResiliente() {
    }

    private final NomsEvenement nomEvenement = NomsEvenement.POPULATION_RESILIENTE;
    private final String descriptionN = "Retirez du jeu 1 carte de votre choix de la défausse Propagation. (Vous pouvez jouer Population résiliente entre les étapes Infection et Intensification d'une carte Épidémie.)";

    @Override
    public void execEffet(PionJoueur pionJoueur) throws CartePropagationNotInDefausseException {
        if (!pionJoueur.getPlateau().getDefausseCartePropagation().contains(cartePropagationChoisis))
            throw new CartePropagationNotInDefausseException();

        pionJoueur.getPlateau().getDefausseCartePropagation().remove(cartePropagationChoisis);
    }

    public void setCartePropagationChoisis(CartePropagation cartePropagationChoisis) {
        this.cartePropagationChoisis = cartePropagationChoisis;
    }
}
