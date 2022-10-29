package modele.facade;

import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.Ville;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VirusIntrouvableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FacadeActionsJoueurImplTest {

    private FacadeActionsJoueurImpl instance;
    private Plateau plateau;

    @BeforeEach
    void setUp() throws VilleIntrouvableException, VirusIntrouvableException, FileNotFoundException {
        this.instance = new FacadeActionsJoueurImpl();
        plateau = new Plateau();
        plateau.initialisationPlateau("src/test/resources/DonneesPlateau.json");;
    }

//    @Test
//    void seDeplacerVoitureVilleIntrouvable() {
//
//        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
//        pionJoueur.setVilleActuelle(plateau.getVilleByName("Chicago"));
//        Ville villeIntrouvable = new Ville("Atlanta");
//
//        Assertions.assertThrows(VilleIntrouvableException.class,
//                () -> this.instance.seDeplacerVoiture(pionJoueur,villeIntrouvable));
//    }
}