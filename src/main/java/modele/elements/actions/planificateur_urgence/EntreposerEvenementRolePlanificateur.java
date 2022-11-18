package modele.elements.actions.planificateur_urgence;

import modele.elements.PionJoueur;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.roles.CartePlanificateurDUrgence;
import modele.elements.enums.NomsRoles;
import modele.exceptions.CarteEvenementNonTrouveDansDefausseException;
import modele.exceptions.MauvaisRoleException;

public class EntreposerEvenementRolePlanificateur implements IAction {


    private CartePlanificateurDUrgence cartePlanificateurDUrgence;
    private CarteEvenement carteEvenementChoisis;
    private CarteEvenement carteEvenementEntrepose;
    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception, CarteEvenementNonTrouveDansDefausseException {
        // prendre une carte evenement de la defausse et l'entreposer sur cette carte
        if (!pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.PLANIFICATEUR_D_URGENCE))
            throw new MauvaisRoleException();


        for (CarteJoueur carteJoueur : pionJoueur.getPlateau().getDefausseCarteJoueur())
            if (carteJoueur instanceof CarteEvenement)
                if (carteJoueur.equals(carteEvenementChoisis)) {
                   carteEvenementEntrepose = carteEvenementChoisis;
                   pionJoueur.getPlateau().getDefausseCarteJoueur().remove(carteEvenementChoisis);
                }

        if (carteEvenementEntrepose == null)
            throw new CarteEvenementNonTrouveDansDefausseException();

        cartePlanificateurDUrgence.setCarteEvenementEntrepose(carteEvenementEntrepose);
    }
}
