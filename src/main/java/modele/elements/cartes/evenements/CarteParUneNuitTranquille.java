package modele.elements.cartes.evenements;

import lombok.Getter;
import modele.elements.PionJoueur;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;

@Getter
public class CarteParUneNuitTranquille extends CarteEvenement {

    private final NomsEvenement nomEvenement = NomsEvenement.PAR_UNE_NUIT_TRANQUILE;
    private String nomCarte = nomEvenement.toString();
    private final String description = "Ne faites pas la prochaine phase Propagation des maladies (ne dévoilez aucune carte Propagation).";

    @Override
    public void execEffet(PionJoueur pionJoueur) {
        pionJoueur.getPlateau().setEffetParUneNuitTranquilleActif(true);
    }
}
