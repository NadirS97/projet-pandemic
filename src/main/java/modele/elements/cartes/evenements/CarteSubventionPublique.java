package modele.elements.cartes.evenements;

import lombok.Getter;
import modele.elements.PionJoueur;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;
import modele.exceptions.VilleIntrouvableException;

@Getter
public class CarteSubventionPublique extends CarteEvenement {

    private final NomsEvenement nomEvenement = NomsEvenement.SUBVENTION_PUBLIQUE;
    private final String description = "Placez 1 station de recherche dans la ville de votre choix (sans avoir à défausser une carte Ville).";

    @Override
    public void execEffet(PionJoueur pionJoueur) {

    }

    public void placerStationRecherche(PionJoueur pionJoueur, String villeChoisi) throws VilleIntrouvableException {
        if (pionJoueur.getPlateau().getVilleByName(villeChoisi) == null) {
            throw new VilleIntrouvableException(villeChoisi);
        }
        pionJoueur.getPlateau().getVilleByName(villeChoisi).setStationDeRechercheVille(true);
    }
}
