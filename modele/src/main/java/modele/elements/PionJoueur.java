package modele.elements;

import lombok.Getter;
import lombok.Setter;
import modele.elements.actions.IAction;
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

    public PionJoueur(String pseudoJoueur, Plateau plateau,int nbActions){
        this.pseudoJoueur = pseudoJoueur;
        this.plateau = plateau;
        this.deckJoueur = new ArrayList<>();
        this.nbActions = nbActions;
    }

    /**
     * Fonction permettant de savoir si le joueur possède une carteVille dans sa main correspondant à la ville en paramètre
     * @param ville
     * @return True si le joueur possède la carte en main, False sinon
     */
    public Boolean isVilleOfCarteVilleDeckJoueur(Ville ville) {
        List<Ville> listeVillesDeckJoueur = new ArrayList<>();
        for(CarteJoueur carteJoueur : deckJoueur){
            if(carteJoueur instanceof CarteVille){
                listeVillesDeckJoueur.add(((CarteVille) carteJoueur).getVilleCarteVille());
            }
        }
        return listeVillesDeckJoueur.contains(ville); //True
    }

    public void defausseCarteVilleDeDeckJoueur(Ville ville) {
        for (CarteJoueur carteJoueur : deckJoueur) {
            if (carteJoueur instanceof CarteVille) {
                if (((CarteVille) carteJoueur).getVilleCarteVille().equals(ville)) {
                    deckJoueur.remove(carteJoueur);
                    break;
                }
            }
        }
    }

    public void ajouterCarteVilleDeckJoueur(CarteVille carteVille){
        deckJoueur.add(carteVille);
    }

    public Ville actionSeDeplacerVolCharter(Ville villeDestination) throws VilleIntrouvableException, CarteVilleInexistanteDansDeckJoueurException, NbActionsMaxTourAtteintException {
        if (nbActions >= 4)
            throw new NbActionsMaxTourAtteintException();
        if (!plateau.isVille(villeDestination.getNomVille()))
            throw new VilleIntrouvableException(villeDestination.getNomVille()+"non trouvé");
        if (!isVilleOfCarteVilleDeckJoueur(villeActuelle))
            throw new CarteVilleInexistanteDansDeckJoueurException("La carte ville correspondante à " + villeDestination + " n'est pas présente dans votre deck.");
        defausseCarteVilleDeDeckJoueur(villeActuelle);
        setVilleActuelle(villeDestination);
        nbActions++;
        return villeActuelle;
    }

    public void construireStation() throws CarteVilleInexistanteDansDeckJoueurException, VilleActuellePossedeDejaUneStationDeRechercheException {
//        LE JOUEUR DEFAUSSE LA CARTE DE LA VILLE OU IL SE SITUE ET CONSTRUIT UNE STATION
        if (!isVilleOfCarteVilleDeckJoueur(villeActuelle)) {
            throw new CarteVilleInexistanteDansDeckJoueurException("La carte ville correspondante à " + villeActuelle + " n'est pas présente dans votre deck.");
        }else{
            if(plateau.isVilleStationDeRecherche(villeActuelle)){
                throw new VilleActuellePossedeDejaUneStationDeRechercheException("Impossible de rajouter une station de recherche, la ville " + villeActuelle.toString() + " possède déjà une station de recherche.");
            }
            else{
                plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
            }
        }
    }

    public void deplacerStationDeRecherche(Ville villeStationDeRecherche) throws VilleAvecAucuneStationDeRechercheException, VilleActuellePossedeDejaUneStationDeRechercheException {
        if (!plateau.isVilleStationDeRecherche(villeActuelle) && plateau.isVilleStationDeRecherche(villeStationDeRecherche)) {
            plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
            plateau.getVilles().get(villeStationDeRecherche.getNomVille()).setStationDeRechercheVille(false);
        }else{
            if (plateau.isVilleStationDeRecherche(villeActuelle)){
                throw new VilleActuellePossedeDejaUneStationDeRechercheException("Impossible de rajouter une station de recherche, la ville " + villeActuelle.toString() + " possède déjà une station de recherche.");
            }
            if(!plateau.isVilleStationDeRecherche(villeStationDeRecherche)){
                throw new VilleAvecAucuneStationDeRechercheException("La ville " + villeStationDeRecherche + " ne possède aucune station de recherche.");
            }
        }
    }

    public void setVilleActuelle(Ville villeActuelle) {
        this.villeActuelle = villeActuelle;
    }

    public void executerAction() throws Exception {

            this.action.execAction(this);
            this.nbActions--;
        }


    }

