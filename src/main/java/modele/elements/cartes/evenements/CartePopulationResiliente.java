package modele.elements.cartes.evenements;

import modele.elements.PionJoueur;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CartePropagation;
import modele.elements.enums.NomsEvenement;
import modele.exceptions.CartePropagationNotInDefausseException;

public class CartePopulationResiliente extends CarteEvenement {

   private CartePropagation cartePropagationChoisis;

    public CartePopulationResiliente() {
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


    @Override
    public void execEvent(PionJoueur pionJoueur) throws Exception {
        if (!pionJoueur.getPlateau().getDefausseCartePropagation().contains(cartePropagationChoisis))
            throw new CartePropagationNotInDefausseException();

        pionJoueur.getPlateau().getDefausseCartePropagation().remove(cartePropagationChoisis);
    }


    public void setCartePropagationChoisis(CartePropagation cartePropagationChoisis) {
        this.cartePropagationChoisis = cartePropagationChoisis;
    }
}
