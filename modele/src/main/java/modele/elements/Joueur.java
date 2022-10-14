package modele.elements;

import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VilleNonVoisineException;
import modele.elements.actions.deplacement.Deplacement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.CarteVille;
import modele.elements.enums.CouleursPion;

import java.util.List;

public class Joueur {

    private String pseudoJoueur;
    private CarteRole roleJoueur;
    private CouleursPion couleursPionJoueur; // Ca va être roleJoueur.getCouleurPionRole()
    private List<CarteJoueur> deck;

    private Ville villeActuelle;
    private Deplacement deplacement;
    private Plateau plateau;

    public Joueur() {

    }

    public void seDeplacer(String modeDeplacement, String villeDestinationString) throws VilleIntrouvableException, VilleNonVoisineException {
//        TODO: les verif de chaque deplacement , par ex si voiture check si la villedestination est bien à coté de la ville d'origine (modif ça dans la stratdeplacementVoiture)

        if(plateau.isVille(villeDestinationString)) {
            Ville villeDestination = plateau.getVilleByName(villeDestinationString);
            switch(modeDeplacement){
                case "VOITURE":
                    if(plateau.isVilleVoisine(villeActuelle,villeDestination)){
                        deplacement.seDeplacer(villeActuelle, villeDestination);
                        villeActuelle = villeDestination;
                    }
                    break;
                case "NAVETTE":
                    if(plateau.isVilleStationDeRecherche(villeDestination)){
                        deplacement.seDeplacer(villeActuelle, villeDestination);
                        villeActuelle = villeDestination;
                    }
                    break;
                case "VOL_DIRECT":
                    CarteJoueur carteVilleDestinationJoueur = new CarteVille(villeDestination);
                    if(deck.contains(carteVilleDestinationJoueur)){
                        deplacement.seDeplacer(villeActuelle, villeDestination);
                        villeActuelle = villeDestination;
                    }
                    break;
                case "VOL_CHARTER":

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
