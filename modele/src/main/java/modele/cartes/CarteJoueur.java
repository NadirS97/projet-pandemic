package modele.cartes;

public abstract class CarteJoueur {

/*
        Lors de la distribution initial, on retire les carte EPIDEMIE du paquet
        Le paquet contient toutes les cartes villes (les mêmes que celle du paquet propagation) en second exemplaire AINSI que des cartes EVENEMENT
        Distribution initial :
            4 joueurs : 2 cartes chacun
            3 joueurs : 3 cartes
            2 joueurs : 4 cartes
        Rajouter les 5 cartes epidemies au paquet une fois distribué
        Mélanger et placer dans pioche Joueur

 */

    String nomCarte;

   abstract String getNomCarte();
}
