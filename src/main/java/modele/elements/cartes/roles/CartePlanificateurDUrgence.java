package modele.elements.cartes.roles;

import lombok.Getter;
import modele.elements.PionJoueur;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.IEffet;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;
import modele.exceptions.NbCubesAAjouterInvalideException;
import modele.exceptions.PropagationImpossibleCarSpecialisteQuarantaineException;
import modele.exceptions.VilleDejaEclosException;

@Getter
public class CartePlanificateurDUrgence extends CarteRole implements IEffet {


    CarteEvenement carteEvenementEntrepose;



    public CartePlanificateurDUrgence(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.PLANIFICATEUR_D_URGENCE);
        super.setDescriptionRole(
                "Pour une action, prenez une carte Evenement de la défausse et entreposez-la sur cette carte. " +
                        "Lorsque vous jouez la carte Evenement entreprosée. Retirez la de la partie. " +
                        "Limite de 1 carte Evenement sur cette carte. Elle ne fait pas partie de votre main.");
    }



    public void execEffet(PionJoueur pionJoueur) throws VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException {

        // on joue l'effet de la carte entreposé et on la retire de la partie
        carteEvenementEntrepose.execEffet(pionJoueur);
        carteEvenementEntrepose = null;
    }

    public void setCarteEvenementEntrepose(CarteEvenement carteEvenementEntrepose) {
        this.carteEvenementEntrepose = carteEvenementEntrepose;
    }
}
