package modele.elements.actions.decouvrir_remede;

import modele.elements.PionJoueur;

import modele.elements.actions.IAction;
import modele.elements.enums.NomsRoles;

public class DecouvrirRemede implements IAction {

    @Override
    public void execAction(PionJoueur pionJoueur) {
        if(pionJoueur.getNbMaxCarteVilleMemeCouleurDeckJoueur().getValue()>=4 && pionJoueur.getRoleJoueur().getNomRole() == NomsRoles.SCIENTIFIQUE) {

        }
        if(pionJoueur.getNbMaxCarteVilleMemeCouleurDeckJoueur().getValue()>=5){

        }
    }
}
