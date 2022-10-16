package modele.elements.actions.deplacement;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;

public class DeplacementNavette implements Deplacement{

    /**
     * Déplacer le pion du joueur entre deux stations de recherche
     * Pour cela on vérifie que la villeActuelle et la villeDestination choisis par le joueur possède bien une station de recherche
     * @param joueur
     * @param villeDestination
     * @return la nouvelle villeActuelle
     * @throws VilleAvecAucuneStationDeRechercheException
     */
    @Override
    public Ville seDeplacer(Joueur joueur, Ville villeDestination) throws VilleAvecAucuneStationDeRechercheException {
        if(joueur.getPlateau().isVilleStationDeRecherche(villeDestination) && joueur.getPlateau().isVilleStationDeRecherche(joueur.getVilleActuelle())){
            joueur.setVilleActuelle(villeDestination);
        }
        return joueur.getVilleActuelle();
    }

}
