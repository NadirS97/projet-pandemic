package modele.facade;

import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.action.IAction;
import modele.elements.action.deplacement.DeplacementVoiture;
import modele.exceptions.RoleIntrouvableException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VirusIntrouvableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FacadePandemic9ImplTest {


    private FacadePandemic9Impl instance;
    private Plateau plateau;

    @BeforeEach
    void setUp() throws VilleIntrouvableException, VirusIntrouvableException, FileNotFoundException, RoleIntrouvableException {
        this.instance = new FacadePandemic9Impl();
        plateau = new Plateau();
        plateau.initialisationPlateau("src/main/resources/DonneesPlateau.json");
    }



//ACTION DEPLACEMENT_VOITURE
    @Test
    void jouerTour() {
        PionJoueur pionJoueur = new PionJoueur("jo",plateau,4);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Chicago"));
        IAction action = new DeplacementVoiture(plateau.getVilleByName("Atlanta"));
        Assertions.assertDoesNotThrow(() -> instance.jouerTour(pionJoueur,action));
        IAction action2 = new DeplacementVoiture(plateau.getVilleByName("Chicago"));
        Assertions.assertDoesNotThrow(() -> instance.jouerTour(pionJoueur,action2));
        IAction action3 = new DeplacementVoiture(plateau.getVilleByName("Atlanta"));
        Assertions.assertDoesNotThrow(() -> instance.jouerTour(pionJoueur,action3));
        IAction action4 = new DeplacementVoiture(plateau.getVilleByName("Chicago"));
        Assertions.assertDoesNotThrow(() -> instance.jouerTour(pionJoueur,action4));
//        IAction action5 = new DeplacementVoiture(plateau.getVilleByName("Atlanta"));
//        Assertions.assertDoesNotThrow(() -> instance.jouerTour(pionJoueur,action5));
        System.out.println(pionJoueur.getNbActions());

//        assertEquals(pionJoueur.getVilleActuelle(), plateau.getVilleByName("Atlanta"));
    }
}