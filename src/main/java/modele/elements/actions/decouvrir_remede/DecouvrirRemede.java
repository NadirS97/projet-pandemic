package modele.elements.actions.decouvrir_remede;

import modele.elements.PionJoueur;

import modele.elements.Virus;
import modele.elements.actions.IAction;
import modele.elements.enums.EtatVirus;
import modele.elements.enums.NomsRoles;
import modele.exceptions.NombreDeCartesVilleDansDeckJoueurInvalideException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.utils.DonneesVariablesStatiques;

public class DecouvrirRemede implements IAction {

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        if(!pionJoueur.getVilleActuelle().isStationDeRechercheVille())
            throw new VilleAvecAucuneStationDeRechercheException("Vous devez être sur une ville possédant une station de recherche afin d'exécuter cette action. Or, la ville actuelle n'en possède pas.");
        if((pionJoueur.getNbMaxCarteVilleMemeCouleurDeckJoueur().getValue()>= DonneesVariablesStatiques.nbCartesMemeCouleurDecouvrirRemedeScientifique && pionJoueur.getRoleJoueur().getNomRole() == NomsRoles.SCIENTIFIQUE)
                || pionJoueur.getNbMaxCarteVilleMemeCouleurDeckJoueur().getValue()>=DonneesVariablesStatiques.nbCartesMemeCouleurDecouvrirRemede) {
            String couleurVirus = pionJoueur.getNbMaxCarteVilleMemeCouleurDeckJoueur().getKey();
            Virus virus = pionJoueur.getPlateau().getLesVirus().get(couleurVirus);
            virus.setEtatVirus(EtatVirus.TRAITE);
            pionJoueur.getLesCartesVilleDeMemeCouleurDeDeckJoueur(couleurVirus).forEach(carteVille ->{
                pionJoueur.defausseCarteVilleDeDeckJoueur(carteVille.getVilleCarteVille());
            });
        }else{
            throw new NombreDeCartesVilleDansDeckJoueurInvalideException("Le nombre de cartes villes de même couleur dans votre deck est insuffisant pour exécution de cette action.");
        }
    }
}
