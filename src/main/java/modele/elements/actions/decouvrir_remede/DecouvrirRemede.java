package modele.elements.actions.decouvrir_remede;

import modele.elements.PionJoueur;

import modele.elements.Virus;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteJoueur;
import modele.elements.enums.EtatVirus;
import modele.elements.enums.NomsRoles;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.NbCartesVilleDansDeckJoueurInvalideException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VirusDejaTraiteException;
import modele.utils.DonneesVariablesStatiques;

public class DecouvrirRemede implements IAction {

    /**
     * Fonction permettant d'executer l'action DecouvrirRemede :
     * Pour une action le Joueur peut découvrir un remède sur n’importe quelle station de recherche
     * à partir du moment où le joueur fournit 5 cartes de la même couleur.
     * Attention, certains rôles permettent de ne fournir que 4 cartes !
     * @param pionJoueur
     * @throws NbActionsMaxTourAtteintException
     * @throws VilleAvecAucuneStationDeRechercheException
     * @throws VirusDejaTraiteException
     * @throws NbCartesVilleDansDeckJoueurInvalideException
     */
    @Override
    public void execAction(PionJoueur pionJoueur) throws NbActionsMaxTourAtteintException, VilleAvecAucuneStationDeRechercheException, VirusDejaTraiteException, NbCartesVilleDansDeckJoueurInvalideException {
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if(!pionJoueur.getVilleActuelle().isStationDeRechercheVille())
            throw new VilleAvecAucuneStationDeRechercheException("Vous devez être sur une ville possédant une station de recherche afin d'exécuter cette action. Or, la ville actuelle n'en possède pas.");
        if((pionJoueur.recupNbMaxCarteVilleMemeCouleurDeckJoueur().getValue()>= DonneesVariablesStatiques.nbCartesMemeCouleurDecouvrirRemedeScientifique && pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.SCIENTIFIQUE))
                || pionJoueur.recupNbMaxCarteVilleMemeCouleurDeckJoueur().getValue()>=DonneesVariablesStatiques.nbCartesMemeCouleurDecouvrirRemede) {
            String couleurVirus = pionJoueur.recupNbMaxCarteVilleMemeCouleurDeckJoueur().getKey();
            Virus virus = pionJoueur.getPlateau().getLesVirus().get(couleurVirus);
            if(virus.getEtatVirus().equals(EtatVirus.NON_TRAITE)){
                virus.setEtatVirus(EtatVirus.TRAITE);
                pionJoueur.recupLesCartesVilleDeMemeCouleurDeDeckJoueur(couleurVirus).forEach(carteVille ->{
                    CarteJoueur carteJoueur = pionJoueur.defausseCarteVilleDeDeckJoueur(carteVille.getVilleCarteVille());
                    pionJoueur.getPlateau().defausserCarteJoueur(carteJoueur);
                });
            }else{
                throw new VirusDejaTraiteException("Le remède pour ce virus est déjà trouvé.");
            }
        }else{
            throw new NbCartesVilleDansDeckJoueurInvalideException("Le nombre de cartes villes de même couleur dans votre deck est insuffisant pour exécution de cette action.");
        }
    }
}
