package modele;

import modele.actions.deplacement.Deplacement;
import modele.cartes.CarteEpidemie;
import modele.cartes.CarteJoueur;
import modele.cartes.CarteRole;
import modele.cartes.CarteVille;
import modele.enums.CouleursPion;

import java.util.ArrayList;
import java.util.List;

public class Joueur {

    private String pseudoJoueur;
    private CarteRole roleJoueur;
    private CouleursPion couleursPionJoueur; // Ca va être roleJoueur.getCouleurPionRole()
    private List<CarteJoueur> deck;

    private Ville caseActuel;
    private Deplacement deplacement;
    private Plateau plateau;

    public Joueur() {
        deck = new ArrayList<>();
        deck.add(new CarteVille());
        deck.add(new CarteEpidemie());
    }

    public void seDeplacer(String villeDestinationString){

//        TODO: les verif de chaque deplacement , par ex si voiture check si la villedestination est bien à coté de la ville d'origine (modif ça dans la stratdeplacementVoiture)
        Ville villeDestination = plateau.getVilleByName(villeDestinationString);
      deplacement.seDeplacer(caseActuel,villeDestination);
      caseActuel = villeDestination;
    }
    public void construireStation(){
//        LE JOUEUR DEFAUSSE LA CARTE DE LA VILLE OU IL SE SITUE ET CONSTRUIT UNE STATION
        plateau.getVilles().get(caseActuel.getNomVille()).setStationDeRechercheVille(true);
//        TODO : Il faut une classe deck surement, plutot que la liste
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }
}
