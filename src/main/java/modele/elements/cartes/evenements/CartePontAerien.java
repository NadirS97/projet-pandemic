package modele.elements.cartes.evenements;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;
import modele.exceptions.PermissionNonAccordeException;

public class CartePontAerien extends CarteEvenement {


    private Ville villeChoisis;
    private PionJoueur pionChoisis;

    public CartePontAerien() {

    }

    private final NomsEvenement NOMEVENEMENT = NomsEvenement.PONT_AERIEN;
    private final String DESCRIPTION = "Déplacez un pion quelconque sur la ville de votre choix. Vous devez avoir la permission du propriétaire du pion qui sera déplacé.";

    @Override
    public NomsEvenement getNomEvennement() {
        return NOMEVENEMENT;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

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
