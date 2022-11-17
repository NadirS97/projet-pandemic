package modele.elements;


import lombok.Getter;
import modele.elements.cartes.CarteRole;
import modele.exceptions.NbJoueursPartieIncorrectException;

import javax.management.relation.Role;
import java.util.*;

@Getter
public class Partie {

//    Map<PionJoueur,CarteRole> joueurs;
    List<PionJoueur> joueurs;
    List<Role> roles;
    Plateau plateau;
    int nbJoueurs;

    public Partie(int nbJoueurs) throws Exception {
        if (nbJoueurs <= 0 || nbJoueurs > 4)
            throw new NbJoueursPartieIncorrectException();
        // marqueur eclosion et propagation placé à 0 lors de la création du plateau
       this.plateau = new Plateau();
       this.nbJoueurs = nbJoueurs;
       joueurs = new ArrayList<>();
       attributionRolesRandom(nbJoueurs);
       miseEnPlaceJeuCartePropagation();
    }

    /*
        MISE EN PLACE DU JEU
        1. Rôles distribués aléatoirement de telle sorte que chaque joueur ait un seul rôle
     */
    private void attributionRolesRandom(int nbJoueurs){
        for (int i = 0 ; i < nbJoueurs ; i++){
            int randomIndex = new Random().nextInt(plateau.getToutesLesCartesRolesExistante().size());
            CarteRole carteRole = plateau.getToutesLesCartesRolesExistante().remove(randomIndex);
            PionJoueur joueur = new PionJoueur();
            joueur.setRoleJoueur(carteRole);
           joueurs.add(joueur);
        }
    }

    /**
     * Les 3 premières cartes retournées seront les 3 villes contaminées
     * chacune par 3 cubes de sa couleur (chaque carte propagation a une couleur fixe liée à un
     * virus). Les 3 cartes suivantes déterminent alors les trois prochaines villes infectées par deux
     * cubes de leur couleur et enfin 3 dernières cartes pour contaminer les trois dernières villes
     * avec un cube de leur couleur. Les cartes utilisées sont alors déposées face visible dans la
     * défausse « propagation ».
     */
    private void miseEnPlaceJeuCartePropagation(){
        // 3 premiere, 3 cubes
        for (int i = 0 ; i < 3 ; i++){
           Ville villeContamine =  plateau.piocherCartePropagation(0);
           Virus virus = plateau.getLesVirus().get(villeContamine.getCouleurVirusVille());
           plateau.getVilleByName(villeContamine.getNomVille()).getNbCubeVirusVille().put(virus,3);
        }
        for (int i = 0 ; i < 3 ; i++){
            Ville villeContamine =  plateau.piocherCartePropagation(0);
            Virus virus = plateau.getLesVirus().get(villeContamine.getCouleurVirusVille());
            plateau.getVilleByName(villeContamine.getNomVille()).getNbCubeVirusVille().put(virus,2);
        }
        for (int i = 0 ; i < 3 ; i++){
            Ville villeContamine =  plateau.piocherCartePropagation(0);
            Virus virus = plateau.getLesVirus().get(villeContamine.getCouleurVirusVille());
            plateau.getVilleByName(villeContamine.getNomVille()).getNbCubeVirusVille().put(virus,1);
        }
    }


}
