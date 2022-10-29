package modele.elements;

import lombok.Getter;
import lombok.Setter;
import modele.action.IAction;
import modele.action.deplacement.DeplacementVoiture;
import modele.elements.enums.ModesDeplacements;
import modele.exceptions.*;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.CarteVille;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PionJoueur {

    private IAction action;
    private int nbActions;

    private String pseudoJoueur;
    private CarteRole roleJoueur;
    private String couleurPion;
    private List<CarteJoueur> deckJoueur;

    private Ville villeActuelle;


    private Plateau plateau;

    public PionJoueur(String pseudoJoueur, Plateau plateau){
        this.pseudoJoueur = pseudoJoueur;
        this.plateau = plateau;
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


    public Ville actionSeDeplacerVoiture(Ville villeDestination) throws VilleIntrouvableException, VilleNonVoisineException, NbActionsMaxTourAtteintException{
        if (nbActions >= 4)
            throw new NbActionsMaxTourAtteintException();
        if (!this.plateau.isVille(villeDestination.getNomVille()))
           throw new VilleIntrouvableException(villeDestination.getNomVille()+"non trouvé");
        if (!this.plateau.isVilleVoisine(getVilleActuelle(),villeDestination))
            throw new VilleNonVoisineException();

        action = new DeplacementVoiture(this,villeDestination);
        action.execAction();
        nbActions++;
        return villeActuelle;
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



    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public void executerAction(){
        if (this.nbActions > 0){
            this.action.execAction();
            this.nbActions --;
        }

    }

    public void setVilleActuelle(Ville villeActuelle) {
        this.villeActuelle = villeActuelle;
    }
}
