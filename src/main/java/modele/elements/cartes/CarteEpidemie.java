package modele.elements.cartes;

import lombok.Getter;
import lombok.ToString;
import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.Ville;
import modele.exceptions.NbCubesAAjouterInvalideException;
import modele.exceptions.PropagationImpossibleCarSpecialisteQuarantaineException;
import modele.exceptions.VilleDejaEclosException;
import modele.utils.DonneesVariablesStatiques;

import java.util.List;


@Getter
@ToString
public class CarteEpidemie extends CarteJoueur implements IEffet {
    private void applicationEffetIntensification(Plateau plateau){
        plateau.melangerPaquet(plateau.getDefausseCartePropagation());
        plateau.getDefausseCartePropagation().forEach(cartePropagation -> {
            plateau.getPiocheCartePropagation().add(0, cartePropagation );
        });
    }

    private void applicationEffetInfection(Plateau plateau) throws VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException {
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
    public void execEffet(PionJoueur pionJoueur) throws Exception {
        applicationEffetInfection(pionJoueur.getPlateau());
        applicationEffetAcceleration(pionJoueur.getPlateau());
        applicationEffetIntensification(pionJoueur.getPlateau());
    }
}
