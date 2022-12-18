package modele.elements.actions.partager_connaissance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.enums.NomsRoles;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.DonneeManquanteException;
import modele.exceptions.JoueursNonPresentMemeVilleException;
import modele.exceptions.NbActionsMaxTourAtteintException;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class PrendreConnaissance implements IAction, Serializable {

    PionJoueur pionJoueur2;
    Ville villeChoisis;

    public PrendreConnaissance(PionJoueur pionJoueur2) {
        this.pionJoueur2 = pionJoueur2;
    }

    public PrendreConnaissance(PionJoueur pionJoueur2, Ville villeChoisis) {
        this.pionJoueur2 = pionJoueur2;
        this.villeChoisis = villeChoisis;
    }

    /**
     * Fonction permettant d'executer l'action PrendreConnaissance :
     * prendre d’un autre joueur la carte ville où les deux joueurs sont au même moment
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
        if(!pionJoueur2.getRoleJoueur().getNomRole().equals(NomsRoles.CHERCHEUSE)){
            if (!pionJoueur2.estVilleOfCarteVilleDeckJoueur(pionJoueur2.getVilleActuelle()))
                throw new CarteVilleInexistanteDansDeckJoueurException("Le joueur ne possède pas la carte ville dans sa main");
            pionJoueur.getDeckJoueur().add(pionJoueur2.defausseCarteVilleDeDeckJoueur(pionJoueur2.getVilleActuelle()));
        }else{
            if(villeChoisis == null)
                throw new DonneeManquanteException();
            if (!pionJoueur2.estVilleOfCarteVilleDeckJoueur(villeChoisis))
                throw new CarteVilleInexistanteDansDeckJoueurException("Le joueur ne possède pas la carte ville dans sa main");

            pionJoueur.getDeckJoueur().add(pionJoueur2.defausseCarteVilleDeDeckJoueur(villeChoisis));
        }
    }
}
