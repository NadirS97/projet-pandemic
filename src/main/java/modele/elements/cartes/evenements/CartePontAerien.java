package modele.elements.cartes.evenements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;
import modele.exceptions.PermissionNonAccordeException;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CartePontAerien extends CarteEvenement implements Serializable {


    private Ville villeChoisis;
    private PionJoueur pionChoisis;

    private final NomsEvenement nomEvenement = NomsEvenement.PONT_AERIEN;
    private final String description = "Déplacez un pion quelconque sur la ville de votre choix. Vous devez avoir la permission du propriétaire du pion qui sera déplacé.";

    @Override
    public void execEffet(PionJoueur pionJoueur) throws PermissionNonAccordeException {
        if (!pionChoisis.isPermissionPontAerien())
            throw new PermissionNonAccordeException();
        pionChoisis.setVilleActuelle(villeChoisis);
    }
}
