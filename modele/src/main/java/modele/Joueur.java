package modele;

import modele.actions.deplacement.Deplacement;
import modele.cartes.CarteJoueur;
import modele.cartes.CarteRole;
import modele.enums.CouleursPion;

import java.util.List;

public class Joueur {

    private String pseudoJoueur;
    private CarteRole roleJoueur;
    private CouleursPion couleursPionJoueur; // Ca va être roleJoueur.getCouleurPionRole()
    private List<CarteJoueur> deck;
    private Ville caseActuel;
    private Deplacement deplacement;
    private Plateau plateau;


    public void seDeplacer(String villeDestinationString){

//        TODO: les verif de chaque deplacement , par ex si voiture check si la villedestination est bien à coté de la ville d'origine (modif ça dans la stratdeplacementVoiture)
        Ville villeDestination = plateau.getVilleByName(villeDestinationString);
      deplacement.seDeplacer(caseActuel,villeDestination);
      caseActuel = villeDestination;
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }
}
