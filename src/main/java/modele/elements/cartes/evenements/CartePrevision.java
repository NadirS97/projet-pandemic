package modele.elements.cartes.evenements;

import lombok.Getter;
import modele.elements.PionJoueur;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.enums.NomsEvenement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
public class CartePrevision extends CarteEvenement {

    private final NomsEvenement nomEvenement = NomsEvenement.PREVISION;
    private String nomCarte = nomEvenement.toString();
    private final String description = "Piochez, consultez et réorganisez dans l'ordre de votre choix les 6 premières cartes du paquet Propagation. Replacez-les ensuite sur le dessus du paquet.";

//    @Override
//    public void effet(Optional<IEffetType> effetType) throws Exception {
//        if (effetType.isPresent()) {
//            EffetTypePrevisionImpl effetTypePrevision = (EffetTypePrevisionImpl) effetType.get();
//            LinkedHashMap<Integer, CarteJoueur> aReorganiser = new LinkedHashMap<>();
//            aReorganiser.put(1, plateau.piocherCarteJoueur());
//            aReorganiser.put(2, plateau.piocherCarteJoueur());
//            aReorganiser.put(3, plateau.piocherCarteJoueur());
//            aReorganiser.put(4, plateau.piocherCarteJoueur());
//            aReorganiser.put(5, plateau.piocherCarteJoueur());
//            aReorganiser.put(6, plateau.piocherCarteJoueur());
//            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte1()));
//            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte2()));
//            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte3()));
//            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte4()));
//            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte5()));
//            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte6()));
//        } else {
//            throw new EffetManquantException();
//        }
//    }

    @Override
    public void execEffet(PionJoueur pionJoueur) {
        for (int i = 0; i < 6; i++) {
            pionJoueur.getMainAReorganiser().add(pionJoueur.getPlateau().piocherCarteJoueur());
        }
    }

    public void ajouterDansPiocheJoueurs(PionJoueur pionJoueur, List<CarteJoueur> cartesReorganisees) {
        cartesReorganisees.forEach(c -> pionJoueur.getPlateau().ajouterDansPiocheCarteJoueur(c));
        pionJoueur.setMainAReorganiser(new ArrayList<>());
    }
}
