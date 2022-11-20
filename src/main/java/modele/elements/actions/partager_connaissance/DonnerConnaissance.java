package modele.elements.actions.partager_connaissance;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.enums.NomsRoles;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.DonneeManquanteException;
import modele.exceptions.JoueursNonPresentMemeVilleException;
import modele.exceptions.NbActionsMaxTourAtteintException;


public class DonnerConnaissance implements IAction {
    PionJoueur pionJoueur2;
    Ville villeChoisis;

    public DonnerConnaissance(PionJoueur pionJoueur2) {
        this.pionJoueur2 = pionJoueur2;
    }

    public DonnerConnaissance(PionJoueur pionJoueur2, Ville villeChoisis) {
        this.pionJoueur2 = pionJoueur2;
        this.villeChoisis = villeChoisis;
    }

    /**
     * Fonction permettant d'executer l'action DonnerConnaissance :
     * donner à un autre joueur la carte ville où les deux joueurs sont au même moment
     * @param pionJoueur
     * @throws NbActionsMaxTourAtteintException
     * @throws JoueursNonPresentMemeVilleException
     * @throws CarteVilleInexistanteDansDeckJoueurException
     * @throws DonneeManquanteException
     */
    @Override
    public void execAction(PionJoueur pionJoueur) throws NbActionsMaxTourAtteintException, JoueursNonPresentMemeVilleException, CarteVilleInexistanteDansDeckJoueurException, DonneeManquanteException {
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur2.getVilleActuelle().equals(pionJoueur.getVilleActuelle()))
            throw new JoueursNonPresentMemeVilleException();
        if(!pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.CHERCHEUSE)){
            if(!pionJoueur.isVilleOfCarteVilleDeckJoueur(pionJoueur.getVilleActuelle()))
                throw new CarteVilleInexistanteDansDeckJoueurException("Le joueur ne possède pas la carte ville dans sa main");
            pionJoueur2.getDeckJoueur().add(pionJoueur.defausseCarteVilleDeDeckJoueur(pionJoueur.getVilleActuelle()));
        }else{
            if(villeChoisis == null)
                throw new DonneeManquanteException();
            if(!pionJoueur.isVilleOfCarteVilleDeckJoueur(villeChoisis))
                throw new CarteVilleInexistanteDansDeckJoueurException("Le joueur ne possède pas la carte ville dans sa main");
            pionJoueur2.getDeckJoueur().add(pionJoueur.defausseCarteVilleDeDeckJoueur(villeChoisis));
        }
    }
}
