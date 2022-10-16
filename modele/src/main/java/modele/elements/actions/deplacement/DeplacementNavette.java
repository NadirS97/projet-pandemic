package modele.elements.actions.deplacement;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;

public class DeplacementNavette implements Deplacement{



    @Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleAvecAucuneStationDeRechercheException {
        if(joueur.getPlateau().isVilleStationDeRecherche(villeDestination) && joueur.getPlateau().isVilleStationDeRecherche(joueur.getVilleActuelle())){
            joueur.setVilleActuelle(villeDestination);
        }
        return joueur.getVilleActuelle();
    }
}
