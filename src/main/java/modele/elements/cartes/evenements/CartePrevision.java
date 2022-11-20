package modele.elements.cartes.evenements;

import lombok.Getter;
import modele.elements.PionJoueur;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CartePropagation;
import modele.elements.enums.NomsEvenement;
import modele.utils.DonneesVariablesStatiques;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CartePrevision extends CarteEvenement {

    private final NomsEvenement nomEvenement = NomsEvenement.PREVISION;
    private final String description = "Piochez, consultez et réorganisez dans l'ordre de votre choix les 6 premières cartes du paquet Propagation. Replacez-les ensuite sur le dessus du paquet.";

    @Override
    public void execEffet(PionJoueur pionJoueur) {
        for (int i = 0; i < DonneesVariablesStatiques.nbCartesJoueurAPiocherEffetEvenementPrevision; i++) {
            pionJoueur.getMainAReorganiser().add(pionJoueur.getPlateau().piocherCartePropagationEventPrevision());
        }
    }

    public void ajouterDansPiochePropagation(PionJoueur pionJoueur, List<CartePropagation> cartesReorganisees) {
        cartesReorganisees.forEach(c -> pionJoueur.getPlateau().ajouterDansPiocheCartePropagation(c));
        pionJoueur.setMainAReorganiser(new ArrayList<>());
    }
}
