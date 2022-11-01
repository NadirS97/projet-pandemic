package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VilleDestinationEstVilleActuelleException;

public class DeplacementNavette implements IAction {

    private Ville villeStationDeRecherche;

    public DeplacementNavette(Ville villeStationDeRecherche) {
        this.villeStationDeRecherche = villeStationDeRecherche;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        if(pionJoueur.getVilleActuelle().equals(villeStationDeRecherche))
            throw new VilleDestinationEstVilleActuelleException("Vous ne pouvez pas vous déplacer vers votre ville actuelle.");
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if(pionJoueur.getVilleActuelle().isStationDeRechercheVille() && pionJoueur.getPlateau().isVilleStationDeRecherche(villeStationDeRecherche)){
            pionJoueur.setVilleActuelle(villeStationDeRecherche);
        }else{
            if(!pionJoueur.getVilleActuelle().isStationDeRechercheVille()){
                throw new VilleAvecAucuneStationDeRechercheException("La ville actuelle: " + pionJoueur.getVilleActuelle().getNomVille() + " ne possède pas de station de recherche.");
            }else{
                throw new VilleAvecAucuneStationDeRechercheException("La ville de destination: " + villeStationDeRecherche.getNomVille() + " ne possède pas de station de recherche.");
            }
        }
    }
}
