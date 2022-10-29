package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;

public class DeplacementNavette implements Deplacement{

    /**
     * Déplacer le pion du joueur entre deux stations de recherche
     * Pour cela on vérifie que la villeActuelle et la villeDestination choisis par le joueur possède bien une station de recherche
     * @param pionJoueur
     * @param villeDestination
     * @return la nouvelle villeActuelle
     * @throws VilleAvecAucuneStationDeRechercheException
     */
    @Override
    public Ville seDeplacer(PionJoueur pionJoueur, Ville villeDestination) throws VilleAvecAucuneStationDeRechercheException {
        if(pionJoueur.getPlateau().isVilleStationDeRecherche(villeDestination) && pionJoueur.getPlateau().isVilleStationDeRecherche(pionJoueur.getVilleActuelle())){
            pionJoueur.setVilleActuelle(villeDestination);
        }
        return pionJoueur.getVilleActuelle();
    }

}
