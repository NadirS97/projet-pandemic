package modele.elements;

import lombok.Getter;
import modele.elements.actions.deplacement.*;
import modele.elements.enums.ModesDeplacements;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VilleNonVoisineException;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.CarteVille;
import modele.elements.enums.CouleursPion;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Joueur {

    private String pseudoJoueur;
    private CarteRole roleJoueur;
    private CouleursPion couleursPionJoueur; // Ca va être roleJoueur.getCouleurPionRole()
    private List<CarteJoueur> deckJoueur;

    private Ville villeActuelle;
    private Deplacement deplacement;
    private Plateau plateau;

    public Joueur(String pseudoJoueur){
        this.pseudoJoueur = pseudoJoueur;
    }
    public Joueur(List<CarteJoueur> deckJoueur) {
        this.deckJoueur = deckJoueur;
    }

    public Ville setVilleActuelle(Ville villeActuelle){
        return this.villeActuelle = villeActuelle;
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

    public CarteJoueur defausseCarteVilleDeDeckJoueur(Ville ville){
        for (CarteJoueur carteJoueur : deckJoueur){
            if (carteJoueur instanceof CarteVille){
                if (((CarteVille) carteJoueur).getVilleCarteVille().equals(ville)) {
                    deckJoueur.remove(carteJoueur);
                    return carteJoueur;
                }
            }
        }
        return null;
    }

    public Deplacement choixDeplacement(ModesDeplacements modesDeplacements){
        switch (modesDeplacements) {
            case VOITURE -> deplacement = new DeplacementVoiture();
            case NAVETTE -> deplacement = new DeplacementNavette();
            case VOL_DIRECT -> deplacement = new DeplacementVolDirect();
            case VOL_CHARTER -> deplacement = new DeplacementVolCharter();
        }
        return null;
    }

    public void construireStation() throws VilleInexistanteDansDeckJoueurException {
//        LE JOUEUR DEFAUSSE LA CARTE DE LA VILLE OU IL SE SITUE ET CONSTRUIT UNE STATION
        if (isVilleOfCarteVilleDeckJoueur(villeActuelle)){
            plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
        }
//        TODO : Il faut une classe deck surement, plutot que la liste
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }


}
