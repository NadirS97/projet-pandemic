package modele.elements.actions.planificateur_urgence;

import lombok.Getter;
import lombok.Setter;
import modele.elements.PionJoueur;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.roles.CartePlanificateurDurgence;
import modele.elements.enums.NomsRoles;
import modele.exceptions.CarteEvenementNonTrouveDansDefausseException;
import modele.exceptions.MauvaisRoleException;


public class EntreposerEvenementRolePlanificateur implements IAction {


    private CarteEvenement carteEvenementChoisis;
    private CarteEvenement carteEvenementEntrepose;

    public EntreposerEvenementRolePlanificateur(CarteEvenement carteEvenementChoisis) {
        this.carteEvenementChoisis = carteEvenementChoisis;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws MauvaisRoleException, CarteEvenementNonTrouveDansDefausseException {
        // prendre une carte evenement de la defausse et l'entreposer sur cette carte
        if (!pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.PLANIFICATEUR_D_URGENCE))
            throw new MauvaisRoleException();


        for (CarteJoueur carteJoueur : pionJoueur.getPlateau().getDefausseCarteJoueur())
            if (carteJoueur instanceof CarteEvenement)
                if (carteJoueur.equals(carteEvenementChoisis)) {
                   carteEvenementEntrepose = carteEvenementChoisis;
                   pionJoueur.getPlateau().getDefausseCarteJoueur().remove(carteEvenementChoisis);
                   break;
                }

        if (carteEvenementEntrepose == null)
            throw new CarteEvenementNonTrouveDansDefausseException();

        pionJoueur.setCartePlanificateurUrgenceEntrepose(carteEvenementEntrepose);
    }
}
