package modele.elements.cartes.evenements;

import modele.elements.Plateau;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;
import modele.elements.cartes.effets.IEffetType;

import java.util.Optional;

public class Prevision extends CarteEvenement {

    private Plateau plateau;

    public Prevision(Plateau plateau) {
        this.plateau = plateau;
    }

    private final NomsEvenement nomEvenement = NomsEvenement.PREVISION;
    private final String description = "Piochez, consultez et réorganisez dans l'ordre de votre choix les 6 premières cartes du paquet Propagation. Replacez-les ensuite sur le dessus du paquet.";

    @Override
    public NomsEvenement getNomEvennement() {
        return nomEvenement;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void effet(Optional<IEffetType> effetType) throws Exception {

    }
}
