package modele.elements.cartes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.Ville;
import modele.exceptions.DefaitePartieTermineException;
import modele.exceptions.NbCubesAAjouterInvalideException;
import modele.exceptions.PropagationImpossibleCarSpecialisteQuarantaineException;
import modele.exceptions.VilleDejaEclosException;
import modele.utils.DonneesVariablesStatiques;

import java.io.Serializable;
import java.util.List;


@Getter
@ToString
@Setter
@NoArgsConstructor
public class CarteEpidemie extends CarteJoueur implements IEffet, Serializable {
    private void applicationEffetIntensification(Plateau plateau){
        plateau.melangerPaquet(plateau.getDefausseCartePropagation());
        plateau.getDefausseCartePropagation().forEach(cartePropagation -> {
            plateau.getPiocheCartePropagation().add(0, cartePropagation );
        });
        plateau.viderDefausseCartePropagation();
    }

    private void applicationEffetInfection(Plateau plateau) throws VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, DefaitePartieTermineException {
        List<CartePropagation> piocheCartePropagation = plateau.getPiocheCartePropagation();
        Ville ville = plateau.piocherCartePropagation(piocheCartePropagation.size() - 1);
        plateau.propagationMaladie(ville, DonneesVariablesStatiques.nbCubesEffetInfectionCarteEpidemie);
    }

    private void applicationEffetAcceleration(Plateau plateau){
        if (plateau.getMarqueurVitessePropagation() < DonneesVariablesStatiques.tabMarqueurVitesseDePropagation.length){
            plateau.avancerMarqueurVitesse();
        }
    }

    @Override
    public void execEffet(PionJoueur pionJoueur) throws VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, DefaitePartieTermineException {
        applicationEffetAcceleration(pionJoueur.getPlateau());
        applicationEffetInfection(pionJoueur.getPlateau());
        applicationEffetIntensification(pionJoueur.getPlateau());
    }
}
