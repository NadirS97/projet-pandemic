package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.actions.Deplacement;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteJoueur;
import modele.elements.enums.NomsRoles;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleIntrouvableException;

public class DeplacementVolCharter implements IAction, Deplacement {

    /*
     * Défausser la carte ville correspondant à la ville où se trouve le pion pour atteindre n’importe quelle autre ville du plateau
     * Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondante à la villeActuelle
     *
     */

    private Ville villeDestination;

    public DeplacementVolCharter(Ville villeDestination) {
        this.villeDestination = villeDestination;
    }

    /**
     * Fonction permettant d'executer l'action DeplacementVolCharter :
     * défausser la carte ville correspondant à la ville où se trouve le pion pour atteindre n’importe
     * quelle autre ville du plateau
     * @param pionJoueur
     * @throws NbActionsMaxTourAtteintException
     * @throws VilleIntrouvableException
     * @throws CarteVilleInexistanteDansDeckJoueurException
     */
    @Override
    public void execAction(PionJoueur pionJoueur) throws NbActionsMaxTourAtteintException, VilleIntrouvableException, CarteVilleInexistanteDansDeckJoueurException {

        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (Boolean.FALSE.equals(pionJoueur.getPlateau().isVille(villeDestination.getNomVille())))
            throw new VilleIntrouvableException(villeDestination.getNomVille() + " non trouvé");
        if (Boolean.FALSE.equals(pionJoueur.estVilleOfCarteVilleDeckJoueur(pionJoueur.getVilleActuelle())))
            throw new CarteVilleInexistanteDansDeckJoueurException("La carte ville correspondante à " + pionJoueur.getVilleActuelle().getNomVille() + " n'est pas présente dans votre deck.");
        if (pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.SPECIALISTE_EN_MISE_EN_QUARANTAINE)) {
            conditionRoleAffectantDeplacement(pionJoueur, pionJoueur.getVilleActuelle(), villeDestination);
        }
        CarteJoueur carteVille = pionJoueur.defausseCarteVilleDeDeckJoueur(pionJoueur.getVilleActuelle());
        pionJoueur.getPlateau().defausserCarteJoueur(carteVille);
        pionJoueur.setVilleActuelle(villeDestination);

    }

    @Override
    public void conditionRoleAffectantDeplacement(PionJoueur pionJoueur, Ville villeDepart, Ville villeArrive) throws VilleIntrouvableException {
        pionJoueur.getPlateau().specialisteQuarantainePartVille(villeDepart);
        pionJoueur.getPlateau().specialisteQuarantaineArriveVille(villeArrive);
    }
}

