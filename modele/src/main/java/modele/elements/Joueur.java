package modele.elements;

import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VilleNonVoisineException;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;
import modele.elements.actions.deplacement.Deplacement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.CarteVille;
import modele.elements.enums.CouleursPion;

import java.util.ArrayList;
import java.util.List;

public class Joueur {

    private String pseudoJoueur;
    private CarteRole roleJoueur;
    private CouleursPion couleursPionJoueur; // Ca va être roleJoueur.getCouleurPionRole()
    private List<CarteJoueur> deckJoueur;
    private Ville villeActuelle;
    private Deplacement deplacement;
    private Plateau plateau;

    public Joueur() {

    }

    /**
     * Fonction permettant de savoir si le joueur possède une carteVille dans sa main correspondant à la ville en paramètre
     * @param ville
     * @return True si le joueur possède la carte en main, False sinon
     */
    public Boolean isVilleOfCarteVilleDeckJoueur(Ville ville) throws VilleInexistanteDansDeckJoueurException {
        List<Ville> listeVillesDeckJoueur = new ArrayList<>();
        for(CarteJoueur carteJoueur : deckJoueur){
            if(carteJoueur instanceof CarteVille){
                listeVillesDeckJoueur.add(((CarteVille) carteJoueur).getVilleCarteVille());
            }
        }
        return listeVillesDeckJoueur.contains(ville);
    }

    public void seDeplacer(String modeDeplacement, String villeDestinationString) throws VilleIntrouvableException, VilleNonVoisineException, VilleInexistanteDansDeckJoueurException, VilleAvecAucuneStationDeRechercheException {
        if(plateau.isVille(villeDestinationString)) {
            Ville villeDestination = plateau.getVilleByName(villeDestinationString);
            switch(modeDeplacement){
                case "VOITURE":
                    // Déplacer le pion entre deux villes reliées par une ligne
                    if(plateau.isVilleVoisine(villeActuelle,villeDestination)){
                        deplacement.seDeplacer(villeActuelle, villeDestination);
                        villeActuelle = villeDestination;
                    }
                    break;
                case "NAVETTE":
                    // Déplacer le pion entre deux stations de recherche
                    if(plateau.isVilleStationDeRecherche(villeDestination)){
                        deplacement.seDeplacer(villeActuelle, villeDestination);
                        villeActuelle = villeDestination;
                    }
                    break;
                case "VOL_DIRECT":
                    // Défausser une carte ville pour déplacer le pion sur la ville de la carte défaussée
                    // Pour cela on vérifie que le Joueur possède dans sa main/son deck la carte Ville correspondant à la villeDestination
                    if(isVilleOfCarteVilleDeckJoueur(villeDestination)){
                        deplacement.seDeplacer(villeActuelle, villeDestination);
                        villeActuelle = villeDestination;
                    }
                    break;
                case "VOL_CHARTER":
                    // Défausser la carte ville correspondant à la ville où se trouve le pion pour atteindre n’importe quelle autre ville du plateau
                    // Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondante à la villeActuelle
                    if(isVilleOfCarteVilleDeckJoueur(villeActuelle)){
                        deplacement.seDeplacer(villeActuelle, villeDestination);
                        villeActuelle = villeDestination;
                    }
                    break;
            }
        }
    }

    public void construireStation(){
//        LE JOUEUR DEFAUSSE LA CARTE DE LA VILLE OU IL SE SITUE ET CONSTRUIT UNE STATION
        plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
//        TODO : Il faut une classe deck surement, plutot que la liste
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }
}
