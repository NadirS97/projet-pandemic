package modele.elements.cartes.evenements;

import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;

public class Prevision extends CarteEvenement {

    private final NomsEvenement NOMEVENEMENT = NomsEvenement.PREVISION;
    private final String DESCRIPTION = "Piochez, consultez et réorganisez dans l'ordre de votre choix les 6 premières cartes du paquet Propagation. Replacez-les ensuite sur le dessus du paquet.";

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

    }
}
