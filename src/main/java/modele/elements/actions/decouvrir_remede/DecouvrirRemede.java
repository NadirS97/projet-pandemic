package modele.elements.actions.decouvrir_remede;

import modele.elements.PionJoueur;

import modele.elements.Virus;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteJoueur;
import modele.elements.enums.EtatVirus;
import modele.elements.enums.NomsRoles;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.NombreDeCartesVilleDansDeckJoueurInvalideException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VirusDejaTraiteException;
import modele.utils.DonneesVariablesStatiques;

public class DecouvrirRemede implements IAction {

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if(!pionJoueur.getVilleActuelle().isStationDeRechercheVille())
            throw new VilleAvecAucuneStationDeRechercheException("Vous devez être sur une ville possédant une station de recherche afin d'exécuter cette action. Or, la ville actuelle n'en possède pas.");
        if((pionJoueur.getNbMaxCarteVilleMemeCouleurDeckJoueur().getValue()>= DonneesVariablesStatiques.nbCartesMemeCouleurDecouvrirRemedeScientifique && pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.SCIENTIFIQUE))
                || pionJoueur.getNbMaxCarteVilleMemeCouleurDeckJoueur().getValue()>=DonneesVariablesStatiques.nbCartesMemeCouleurDecouvrirRemede) {
            String couleurVirus = pionJoueur.getNbMaxCarteVilleMemeCouleurDeckJoueur().getKey();
            Virus virus = pionJoueur.getPlateau().getLesVirus().get(couleurVirus);
            if(virus.getEtatVirus().equals(EtatVirus.NON_TRAITE)){
                virus.setEtatVirus(EtatVirus.TRAITE);
                pionJoueur.getLesCartesVilleDeMemeCouleurDeDeckJoueur(couleurVirus).forEach(carteVille ->{
                    CarteJoueur carteJoueur = pionJoueur.defausseCarteVilleDeDeckJoueur(carteVille.getVilleCarteVille());
                    pionJoueur.getPlateau().defausserCarteJoueur(carteJoueur);
                });
            }else{
                throw new VirusDejaTraiteException("Le remède pour ce virus est déjà trouvé.");
            }
        }else{
            throw new NombreDeCartesVilleDansDeckJoueurInvalideException("Le nombre de cartes villes de même couleur dans votre deck est insuffisant pour exécution de cette action.");
        }
    }
}
