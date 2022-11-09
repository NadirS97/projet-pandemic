package modele.elements.cartes.evenements;

import lombok.Getter;
import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;
import modele.exceptions.PermissionNonAccordeException;

@Getter
public class CartePontAerien extends CarteEvenement {


    private Ville villeChoisis;
    private PionJoueur pionChoisis;

    public CartePontAerien() {

    }

    private final NomsEvenement nomEvenement = NomsEvenement.PONT_AERIEN;
    private final String nomCarte = nomEvenement.toString();
    private final String description = "Déplacez un pion quelconque sur la ville de votre choix. Vous devez avoir la permission du propriétaire du pion qui sera déplacé.";

    @Override
    public void execEffet(PionJoueur pionJoueur) throws PermissionNonAccordeException {
        if (!pionChoisis.isPermissionPontAerien())
            throw new PermissionNonAccordeException();
        pionChoisis.setVilleActuelle(villeChoisis);
    }

    public void setVilleChoisis(Ville villeChoisis) {
        this.villeChoisis = villeChoisis;
    }

    public void setPionChoisis(PionJoueur pionChoisis) {
        this.pionChoisis = pionChoisis;
    }
}
