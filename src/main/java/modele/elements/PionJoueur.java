package modele.elements;

import lombok.Getter;
import lombok.Setter;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteEvenement;
import modele.exceptions.*;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.CarteVille;
import modele.utils.DonneesVariablesStatiques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private boolean permissionPontAerien;

    public PionJoueur(String pseudoJoueur, Plateau plateau) {
        this.pseudoJoueur = pseudoJoueur;
        this.plateau = plateau;
        this.deckJoueur = new ArrayList<>();
        this.nbActions = DonneesVariablesStatiques.nbActionsMaxParTour;
    }

    public PionJoueur(){
        this.deckJoueur = new ArrayList<>();
    }

    /**
     * Fonction permettant de savoir si le joueur possède une carteVille dans sa main correspondant à la ville en paramètre
     *
     * @param ville
     * @return True si le joueur possède la carte en main, False sinon
     */
    public Boolean isVilleOfCarteVilleDeckJoueur(Ville ville) {
        List<Ville> listeVillesDeckJoueur = new ArrayList<>();
        for (CarteJoueur carteJoueur : deckJoueur) {
            if (carteJoueur instanceof CarteVille) {
                listeVillesDeckJoueur.add(((CarteVille) carteJoueur).getVilleCarteVille());
            }
        }
        return listeVillesDeckJoueur.contains(ville); //True
    }

    public Map.Entry<String, Integer> getNbMaxCarteVilleMemeCouleurDeckJoueur(){
        Map<String, Integer> maMap= new HashMap<>();
        plateau.getLesVirus().keySet().forEach(s -> {
            maMap.put(s, 0);
        });
        deckJoueur.forEach(carteJoueur -> {
            if(carteJoueur instanceof CarteVille){
                String couleurVirusVille = ((CarteVille) carteJoueur).getVilleCarteVille().getCouleurVirusVille();
                int nbCarteVille = maMap.get(couleurVirusVille);
                maMap.put(couleurVirusVille, nbCarteVille + 1);
            }
        });
        Map.Entry<String, Integer> maxEntry = maMap.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null);
        return maxEntry;
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

    public void jouerCarteEvenement(CarteEvenement carteEvenement) throws Exception {
        if (!deckJoueur.contains(carteEvenement))
            throw new CarteEvenementNotFoundInDeckException();
        carteEvenement.execEffet(this);
        deckJoueur.remove(carteEvenement);
    }

    public void piocherCartes() {
        deckJoueur.add(plateau.getPiocheCarteJoueur().remove(0));
        deckJoueur.add(plateau.getPiocheCarteJoueur().remove(0));
    }

    public void ajouterCarteVilleDeckJoueur(CarteVille carteVille) {
        deckJoueur.add(carteVille);
    }

    public void setVilleActuelle(Ville villeActuelle) {
        this.villeActuelle = villeActuelle;
    }

    public void executerAction() throws Exception {
        this.action.execAction(this);
        this.nbActions--;
    }


}

