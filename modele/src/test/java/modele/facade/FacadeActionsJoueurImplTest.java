package modele.facade;

import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.Ville;
import modele.exceptions.NbActionsMaxTourAtteintException;
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
        plateau.initialisationPlateau("src/main/resources/DonneesPlateau.json");
    }

    @Test
    void actionSeDeplacerVoitureVilleOK() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Chicago"));
        Ville villeIntrouvable = new Ville("Atlanta");

        Assertions.assertDoesNotThrow(() -> this.instance.actionSeDeplacerVoiture(pionJoueur,villeIntrouvable));
    }

    @Test
    void actionSeDeplacerVoitureVilleIntrouvable() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Atlanta"));
        Ville villeIntrouvable = new Ville("Introuvable");

        Assertions.assertThrows(VilleIntrouvableException.class,
                () -> this.instance.actionSeDeplacerVoiture(pionJoueur,villeIntrouvable));
    }

    @Test
    void actionSeDeplacerVoitureNbAction() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        pionJoueur.setNbActions(4);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Atlanta"));
        Ville chicago = new Ville("Chicago");

        Assertions.assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.actionSeDeplacerVoiture(pionJoueur,chicago));
    }
}