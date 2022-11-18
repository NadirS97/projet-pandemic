package modele.elements.actions;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.exceptions.VilleIntrouvableException;

public interface Deplacement {



    void conditionRoleAffectantDeplacement(PionJoueur pionJoueur, Ville villeDepart, Ville villeArrive) throws VilleIntrouvableException;
}
