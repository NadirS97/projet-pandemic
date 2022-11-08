package modele.elements.cartes.evenements;

import lombok.Getter;
import modele.elements.PionJoueur;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;

@Getter
public class CarteSubventionPublique extends CarteEvenement {

    private final NomsEvenement nomEvenement = NomsEvenement.SUBVENTION_PUBLIQUE;
    private final String description = "Placez 1 station de recherche dans la ville de votre choix (sans avoir à défausser une carte Ville).";

//    @Override
//    public void effet(Optional<IEffetType> effetType) throws Exception, EffetManquantException {
//        if (effetType.isPresent()) {
//            EffetTypeSubventionPubliqueImpl effetTypeSubventionPubliqueImpl = (EffetTypeSubventionPubliqueImpl) effetType.get();
//            plateau.getVilles().get(effetTypeSubventionPubliqueImpl.getVille().getNomVille()).setStationDeRechercheVille(true);
//        } else {
//            throw new EffetManquantException();
//        }
//    }

    @Override
    public void execEffet(PionJoueur pionJoueur) {

    }
}
