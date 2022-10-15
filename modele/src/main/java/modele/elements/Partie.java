package modele.elements;


import lombok.Getter;
import modele.exceptions.CasCouleurVilleIncorrectException;
import modele.facade.FacadePandemic9Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter

public class Partie {

    private String codePartie;
    private List<Joueur> joueurs;
    private Plateau plateau;

    public Partie(String joueur) throws CasCouleurVilleIncorrectException {
        this.codePartie = UUID.randomUUID().toString();
        this.plateau = new Plateau();
        this.joueurs = new ArrayList<>();
        this.joueurs.add(new Joueur(joueur));

    }

    public boolean isJoueurDejaDansPartie(String pseudo){
        for (Joueur joueur : joueurs){
            if (joueur.getPseudoJoueur().equals(pseudo))
                return true;
        }
        return false;
    }


}
