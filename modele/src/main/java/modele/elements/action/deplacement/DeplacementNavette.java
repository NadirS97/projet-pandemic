package modele.elements.action.deplacement;

import modele.elements.PionJoueur;
import modele.elements.action.IAction;
import modele.elements.Ville;

public class DeplacementNavette implements IAction {

    private Ville villeStationDeRecherche;

    public DeplacementNavette(Ville villeStationDeRecherche) {
        this.villeStationDeRecherche = villeStationDeRecherche;
    }



    @Override
    public void execAction(PionJoueur pionJoueur) {

        //
    /*    Plateau plateau;
        if (!plateau.isVilleStationDeRecherche(villeActuelle) && plateau.isVilleStationDeRecherche(villeStationDeRecherche)) {
            plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
            plateau.getVilles().get(villeStationDeRecherche.getNomVille()).setStationDeRechercheVille(false);
        }else{
            if (plateau.isVilleStationDeRecherche(villeActuelle)){
                throw new VilleActuellePossedeDejaUneStationDeRechercheException();
            }
        }
*/
    }
}
