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
    private List<Joueur> joueursPartie;
    private Plateau plateau;

    public Partie(String joueur) throws CasCouleurVilleIncorrectException {
        this.codePartie = UUID.randomUUID().toString();
        this.plateau = new Plateau();
        this.joueursPartie = new ArrayList<>();
        this.joueursPartie.add(new Joueur(joueur));

    }

    public boolean isJoueurDejaDansPartie(String pseudo){
        for (Joueur joueur : joueursPartie){
            if (joueur.getPseudoJoueur().equals(pseudo))
                return true;
        }
        return false;
    }


}
