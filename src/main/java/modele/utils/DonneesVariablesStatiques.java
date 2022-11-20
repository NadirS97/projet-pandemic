package modele.utils;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class DonneesVariablesStatiques {

    /**
     * Classe permettant de configurer les variables du jeu
     */

    // Nombre d'actions max autorisées par tour
    public static int nbActionsMaxParTour = 4;

    // Nombre de stations de recherche max autorisées à construire
    public static int nbStationsRechercheMaxAutorise = 6;

    //Nombre de cubes à retirer lors d'un traitement "classique" d'un virus
    public static int nbCubesARetirerLorsDuTraitementVirus = 1;

    //Nombre de cubes existant de base par Virus
    public static int nbCubesExistantParVirus = 24;

    //Nombre de cubes initialement présent dans chaque ville
    public static int nbCubesInitialementPresentDansChaqueVille = 0;

    //Nombre de cubes autorisées avant le déclenchement d'une éclosion
    public static int nbCubeMaxAvantEclosion = 3;

    //Nombre de cubes à rajouter lors de l'execution de l'effet "Infection" de la carte épidémie
    public static int nbCubesEffetInfectionCarteEpidemie = 3;

    //Nombre de cartes épidémie à créer (à rajouter au jeu)
    public static int nbCartesEpidemieACreer = 5;

    //Nombre de cartes de même couleur nécessaire pour jouer l'action DécouvrirRemede
    public static int nbCartesMemeCouleurDecouvrirRemede = 5;

    //Nombre de cartes de même couleur nécessaire pour jouer l'action DécouvrirRemede lorsque le role du pion est Scientifique
    public static int nbCartesMemeCouleurDecouvrirRemedeScientifique = 4;

    //Nombre de cartes à piocher lors de l'exécution de l'effet de l'événement "Prévision"
    public static int nbCartesJoueurAPiocherEffetEvenementPrevision = 6;

    //Tableau statique pour marqueurVitesseDePropagation
    public static int[] tabMarqueurVitesseDePropagation = {2,2,2,3,3,4,4};
}
