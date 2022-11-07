package modele.elements.cartes.evenements;

import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;
import modele.exceptions.DeplacementRefuseException;
import modele.elements.cartes.effets.evenements.EffetTypePontAerienImpl;
import modele.elements.cartes.effets.IEffetType;
import modele.exceptions.EffetManquantException;

import java.util.Optional;

public class PontAerien extends CarteEvenement {

    private Plateau plateau;

    public PontAerien(Plateau plateau) {
        this.plateau = plateau;
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
    public void execEvent(PionJoueur pionJoueur) {

    }

//    @Override
//    public void effet(Optional<IEffetType> effetType) throws Exception, EffetManquantException {
//        if (effetType.isPresent()) {
//            EffetTypePontAerienImpl effetTypePontAerienImpl = (EffetTypePontAerienImpl) effetType.get();
//            if (effetTypePontAerienImpl.isAutorisationDuJoueur()) {
//                plateau.getVilles().get(effetTypePontAerienImpl.getPionJoueur().getVilleActuelle().getNomVille()).getListePionsJoueursPresents().remove(effetTypePontAerienImpl.getPionJoueur());
//                plateau.getVilles().get(effetTypePontAerienImpl.getVilleDestination().getNomVille()).getListePionsJoueursPresents().add(effetTypePontAerienImpl.getPionJoueur());
//                effetTypePontAerienImpl.getPionJoueur().setVilleActuelle(effetTypePontAerienImpl.getVilleDestination());
//            } else {
//                throw new DeplacementRefuseException();
//            }
//        } else {
//            throw new EffetManquantException();
//        }
//    }
}
