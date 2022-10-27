package modele.elements;

import lombok.Getter;
import lombok.Setter;
import modele.action.IAction;
import modele.elements.actions.deplacement.*;
import modele.elements.enums.ModesDeplacements;
import modele.exceptions.*;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.CarteVille;
import modele.elements.enums.CouleursPion;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Joueur {

    private IAction action;
    private int nbActions;

    private String pseudoJoueur;
    private CarteRole roleJoueur;
    private CouleursPion couleursPionJoueur; // Ca va être roleJoueur.getCouleurPionRole()
    private List<CarteJoueur> deckJoueur;

    private Ville villeActuelle;
    private Deplacement deplacement;

    private Plateau plateau;
    private int nbActionsTour;

    public Joueur(String pseudoJoueur,int nbActions){
        this.nbActions=nbActions;
        this.pseudoJoueur = pseudoJoueur;

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
        if(!listeVillesDeckJoueur.contains(ville)){
            throw new VilleInexistanteDansDeckJoueurException();
        }
        return listeVillesDeckJoueur.contains(ville); //True
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

    public Deplacement choixDeplacement(ModesDeplacements modesDeplacements) throws ModeDeplacementInexistantException {
        switch (modesDeplacements) {
            case VOITURE -> deplacement = new DeplacementVoiture();
            case NAVETTE -> deplacement = new DeplacementNavette();
            case VOL_DIRECT -> deplacement = new DeplacementVolDirect();
            case VOL_CHARTER -> deplacement = new DeplacementVolCharter();
            default -> throw new ModeDeplacementInexistantException();
        }
        return null;
    }

    public Ville seDeplacer(Ville villeDestination) throws VilleAvecAucuneStationDeRechercheException, VilleNonVoisineException, VilleInexistanteDansDeckJoueurException {
        return deplacement.seDeplacer(this,villeDestination);
    }

    public void setVilleActuelle(Ville villeActuelle) {
        this.villeActuelle = villeActuelle;
    }


    public void construireStation() throws VilleInexistanteDansDeckJoueurException, VilleAvecAucuneStationDeRechercheException {
//        LE JOUEUR DEFAUSSE LA CARTE DE LA VILLE OU IL SE SITUE ET CONSTRUIT UNE STATION
        if (isVilleOfCarteVilleDeckJoueur(villeActuelle) && !plateau.isVilleStationDeRecherche(villeActuelle)){
            plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
        }
    }

    public void deplacerStationDeRecherche(Ville villeStationDeRecherche) throws VilleAvecAucuneStationDeRechercheException, VilleActuellePossedeDejaUneStationDeRechercheException {
        if (!plateau.isVilleStationDeRecherche(villeActuelle) && plateau.isVilleStationDeRecherche(villeStationDeRecherche)) {
            plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
            plateau.getVilles().get(villeStationDeRecherche.getNomVille()).setStationDeRechercheVille(false);
        }else{
            if (plateau.isVilleStationDeRecherche(villeActuelle)){
                throw new VilleActuellePossedeDejaUneStationDeRechercheException();
            }
        }
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public void executerAction(){
        if (this.nbActions > 0){
            this.action.execAction();
            this.nbActions --;
        }

    }
}
