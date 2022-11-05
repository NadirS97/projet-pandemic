package modele.elements.cartes.evenements;

import modele.elements.Plateau;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.effets.evenements.EffetTypePrevisionImpl;
import modele.elements.enums.NomsEvenement;
import modele.elements.cartes.effets.IEffetType;
import modele.exceptions.EffetManquantException;

import java.util.LinkedHashMap;
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
        if (effetType.isPresent()) {
            EffetTypePrevisionImpl effetTypePrevision = (EffetTypePrevisionImpl) effetType.get();
            LinkedHashMap<Integer, CarteJoueur> aReorganiser = new LinkedHashMap<>();
            aReorganiser.put(1, plateau.piocherCarteJoueur());
            aReorganiser.put(2, plateau.piocherCarteJoueur());
            aReorganiser.put(3, plateau.piocherCarteJoueur());
            aReorganiser.put(4, plateau.piocherCarteJoueur());
            aReorganiser.put(5, plateau.piocherCarteJoueur());
            aReorganiser.put(6, plateau.piocherCarteJoueur());
            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte1()));
            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte2()));
            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte3()));
            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte4()));
            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte5()));
            plateau.ajouterDansPiocheCarteJoueur(aReorganiser.remove(effetTypePrevision.getChoixCarte6()));
        } else {
            throw new EffetManquantException();
        }
    }
}
