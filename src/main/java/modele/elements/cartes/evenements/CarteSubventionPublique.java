package modele.elements.cartes.evenements;

import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;

public class CarteSubventionPublique extends CarteEvenement {

    private Plateau plateau;

    public CarteSubventionPublique(Plateau plateau) {
        this.plateau = plateau;
    }

    private final NomsEvenement NOMEVENEMENT = NomsEvenement.SUBVENTION_PUBLIQUE;
    private final String DESCRIPTION = "Placez 1 station de recherche dans la ville de votre choix (sans avoir à défausser une carte Ville).";

    @Override
    public NomsEvenement getNomEvennement() {
        return NOMEVENEMENT;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

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
