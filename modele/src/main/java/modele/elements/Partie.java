package modele.elements;


import lombok.Getter;
import modele.exceptions.CasCouleurVilleIncorrectException;
import modele.facade.FacadePandemic9Impl;

import java.util.*;

@Getter
public class Partie {

    private String codePartie;
    private Map<String,Joueur> joueursPartie;

    private List<String> ordreTourDeJeuPseudoJoueur;
    private int indexJoueur =0;
    private Plateau plateauPartie;

    public Partie(String pseudoJoueurPartie) throws CasCouleurVilleIncorrectException {
        this.codePartie = UUID.randomUUID().toString();
        this.plateauPartie = new Plateau();
        this.joueursPartie = new HashMap<>();
        this.joueursPartie.put(pseudoJoueurPartie, new Joueur(pseudoJoueurPartie));
        this.ordreTourDeJeuPseudoJoueur = new ArrayList<>();
        this.ordreTourDeJeuPseudoJoueur.add(pseudoJoueurPartie);
    }

    public String aQuiLeTour(){
        if (indexJoueur == 4)
            indexJoueur = 0;
        String joueur = ordreTourDeJeuPseudoJoueur.get(indexJoueur);
        indexJoueur++;
        return joueur;
    }

    public boolean isJoueurDejaDansPartie(String pseudoJoueurPartie){
        for (Joueur joueur : joueursPartie.values()){
            if (joueur.getPseudoJoueur().equals(pseudoJoueurPartie))
                return true;
        }
        return false;
    }

}
