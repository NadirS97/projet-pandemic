package modele.elements.actions.construire_une_station;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;


public class ConstruireUneStation implements IAction {

    private Ville ville;

    public ConstruireUneStation() {
    }
    public ConstruireUneStation(Ville ville) {
        this.ville = ville;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        if(pionJoueur.getPlateau().getNbStationsDeRechercheConstruites() < 6) {
            pionJoueur.construireStation();
            pionJoueur.defausseCarteVilleDeDeckJoueur(pionJoueur.getVilleActuelle());
        }else{
            pionJoueur.deplacerStationDeRecherche(ville);
        }
    }
}
