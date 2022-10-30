package modele.facade;

import modele.elements.PionJoueur;
import modele.elements.Plateau;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.actions.deplacement.DeplacementVoiture;
import modele.elements.actions.deplacement.DeplacementVolDirect;
import modele.elements.cartes.CarteVille;
import modele.exceptions.*;
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
    void jouerTourActionDeplacementVoitureOK() {
        PionJoueur pionJoueur = new PionJoueur("jo",plateau,4);
        Ville atlanta = plateau.getVilleByName("Atlanta");
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Chicago"));
        IAction action = new DeplacementVoiture(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerTour(pionJoueur,action));

        assertEquals(pionJoueur.getVilleActuelle(), plateau.getVilleByName("Atlanta"));
    }

    @Test
    void jouerTourActionDeplacementVoitureNbTourMaxAtteint() {
        PionJoueur pionJoueur = new PionJoueur("jo",plateau,4);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Chicago"));
        IAction action = new DeplacementVoiture(plateau.getVilleByName("Atlanta"));
        Assertions.assertDoesNotThrow(() -> this.instance.jouerTour(pionJoueur,action));
        IAction action1 = new DeplacementVoiture(plateau.getVilleByName("Chicago"));
        Assertions.assertDoesNotThrow(() -> this.instance.jouerTour(pionJoueur,action1));
        IAction action2 = new DeplacementVoiture(plateau.getVilleByName("Atlanta"));
        Assertions.assertDoesNotThrow(() -> this.instance.jouerTour(pionJoueur,action2));
        IAction action3 = new DeplacementVoiture(plateau.getVilleByName("Chicago"));
        Assertions.assertDoesNotThrow(() -> this.instance.jouerTour(pionJoueur,action3));
        IAction action4 = new DeplacementVoiture(plateau.getVilleByName("Atlanta"));
        Assertions.assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.jouerTour(pionJoueur,action4));

    }

    @Test
    void jouerTourActionDeplacementVoitureVilleIntrouvable() {
        PionJoueur pionJoueur = new PionJoueur("jo",plateau,4);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Chicago"));
        IAction action = new DeplacementVoiture(new Ville("Introuvable"));
        Assertions.assertThrows(VilleIntrouvableException.class,
                () -> this.instance.jouerTour(pionJoueur,action));

    }

    @Test
    void jouerTourActionDeplacementVoitureVilleNonVoisine() {
        PionJoueur pionJoueur = new PionJoueur("jo",plateau,4);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Chicago"));
        IAction action = new DeplacementVoiture(plateau.getVilleByName("Paris"));
        Assertions.assertThrows(VilleNonVoisineException.class,
                () -> this.instance.jouerTour(pionJoueur,action));

    }

    //ACTION DEPLACEMENT_VOL_DIRECT
    @Test
    void jouerTourActionDeplacementVolDirectOK() {
        PionJoueur pionJoueur = new PionJoueur("jo",plateau,4);
        Ville madrid = plateau.getVilleByName("Madrid");
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Chicago"));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(madrid));
        IAction action = new DeplacementVolDirect(madrid);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerTour(pionJoueur,action));

        assertEquals(pionJoueur.getVilleActuelle(), madrid);
    }
}