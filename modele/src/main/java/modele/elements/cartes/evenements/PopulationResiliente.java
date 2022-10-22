package modele.elements.cartes.evenements;

import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;

public class PopulationResiliente extends CarteEvenement {

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
    public void effet() {
        //effet
    }
}
