package modele.elements.actions.construire_une_station;

import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteJoueur;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleActuellePossedeDejaUneStationDeRechercheException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.utils.DonneesVariablesStatiques;


public class ConstruireUneStation implements IAction {

    private Ville villeStationDeRecherche;

    public ConstruireUneStation() {
    }

    public ConstruireUneStation(Ville villeStationDeRecherche) {
        this.villeStationDeRecherche = villeStationDeRecherche;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        Ville villeActuelle = pionJoueur.getVilleActuelle();
        Plateau plateau = pionJoueur.getPlateau();
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (plateau.getNbStationsDeRechercheConstruites() < DonneesVariablesStatiques.nbStationsRechercheMaxAutorise) {
            if (!pionJoueur.isVilleOfCarteVilleDeckJoueur(villeActuelle))
                throw new CarteVilleInexistanteDansDeckJoueurException("La carte ville correspondante à " + villeActuelle.getNomVille() + " n'est pas présente dans votre deck.");
            if (plateau.isVilleStationDeRecherche(villeActuelle)) {
                throw new VilleActuellePossedeDejaUneStationDeRechercheException("Impossible de rajouter une station de recherche, la ville " + villeActuelle.getNomVille() + " possède déjà une station de recherche.");
            } else {
                plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
            }
            CarteJoueur carteJoueur = pionJoueur.defausseCarteVilleDeDeckJoueur(villeActuelle);
            pionJoueur.getPlateau().defausserCarteJoueur(carteJoueur);
        } else {
            if (plateau.isVilleStationDeRecherche(villeActuelle))
                throw new VilleActuellePossedeDejaUneStationDeRechercheException("Impossible de rajouter une station de recherche, la ville " + villeActuelle.getNomVille() + " possède déjà une station de recherche.");
            if (!plateau.isVilleStationDeRecherche(villeStationDeRecherche))
                throw new VilleAvecAucuneStationDeRechercheException("La ville " + villeStationDeRecherche.getNomVille() + " ne possède aucune station de recherche.");
            if (!plateau.isVilleStationDeRecherche(villeActuelle) && plateau.isVilleStationDeRecherche(villeStationDeRecherche)) {
                plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
                plateau.getVilles().get(villeStationDeRecherche.getNomVille()).setStationDeRechercheVille(false);
            }
        }
    }
}
