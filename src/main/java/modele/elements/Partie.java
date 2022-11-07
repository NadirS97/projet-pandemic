package modele.elements;


import lombok.Getter;
import modele.elements.cartes.CarteRole;
import modele.exceptions.NbJoueursPartieIncorrectException;

import javax.management.relation.Role;
import java.util.*;

@Getter
public class Partie {

    Map<PionJoueur,CarteRole> joueurs;
    List<Role> roles;
    Plateau plateau;

    public Partie(int nbJoueurs) throws Exception {
        if (nbJoueurs <= 0 || nbJoueurs > 4)
            throw new NbJoueursPartieIncorrectException();
       this.plateau = new Plateau();
       joueurs = new HashMap<>();
       attributionRolesRandom(nbJoueurs);
    }

    /*
        MISE EN PLACE DU JEU
        1. Rôles distribués aléatoirement de telle sorte que chaque joueur ait un seul rôle
     */
    public void attributionRolesRandom(int nbJoueurs){
        for (int i = 0 ; i < nbJoueurs ; i++){
            int randomIndex = new Random().nextInt(plateau.getToutesLesCartesRolesExistante().size());
            CarteRole carteRole = plateau.getToutesLesCartesRolesExistante().remove(randomIndex);
            joueurs.put(new PionJoueur(), carteRole);
        }
    }
}
