package modele.elements.cartes;

import lombok.Getter;
import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.enums.NomsEffetsEpidemie;
import modele.exceptions.NbCubesAAjouterInvalideException;
import modele.exceptions.NomsEffetsEpidemieInvalideException;
import modele.exceptions.VilleDejaEclosException;
import modele.utils.DonneesVariablesStatiques;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
public class CarteEpidemie extends CarteJoueur implements IEffet {
    private Map<NomsEffetsEpidemie,String> effetsEpidemie;

    public CarteEpidemie() {
        effetsEpidemie = new HashMap<>();
        effetsEpidemie.put(NomsEffetsEpidemie.ACCELERATION, "La description de Acceleration");
        effetsEpidemie.put(NomsEffetsEpidemie.INFECTION, "La description de Infection");
        effetsEpidemie.put(NomsEffetsEpidemie.INTENSIFICATION, "La description de Intensification");
    }

    private void applicationEffetIntensification(Plateau plateau){
        plateau.melangerPaquet(plateau.getDefausseCartePropagation());
        plateau.getDefausseCartePropagation().forEach(cartePropagation -> {
            plateau.getPiocheCartePropagation().add(0, cartePropagation );
        });
    }

    private void applicationEffetInfection(Plateau plateau) throws VilleDejaEclosException, NbCubesAAjouterInvalideException {
        List<CartePropagation> piocheCartePropagation = plateau.getPiocheCartePropagation();
        CartePropagation cartePropagation = piocheCartePropagation.get(piocheCartePropagation.size() - 1);
        piocheCartePropagation.remove(cartePropagation);
        plateau.getDefausseCartePropagation().add(cartePropagation);
        plateau.propagationMaladie(cartePropagation.getVilleCartePropagation(), DonneesVariablesStatiques.nbCubeMaxAvantEclosion);
    }

    private void applicationEffetAcceleration(Plateau plateau){
       // plateau.getMarqueurVitessePropagation()
        // TODO: EffetAcceleration à faire
    }

    @Override
    public void execEffet(PionJoueur pionJoueur) throws Exception {
        for(NomsEffetsEpidemie nomEffet : effetsEpidemie.keySet()){
            switch (nomEffet) {
                case INFECTION ->
                    applicationEffetInfection(pionJoueur.getPlateau());
                case ACCELERATION ->
                    applicationEffetAcceleration(pionJoueur.getPlateau());
                case INTENSIFICATION ->
                    applicationEffetAcceleration(pionJoueur.getPlateau());
                default -> throw new NomsEffetsEpidemieInvalideException("Le nom de l'effet pour la carte épidémie est invalide.");
            }
        }
    }
}
