package modele.facade;

import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.Ville;
import modele.elements.Virus;
import modele.elements.cartes.CarteVille;
import modele.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    /**
     *
     *
     *  TESTS DEPLACEMENT VOL VOITURE
     *
     */
    @Test
    void actionSeDeplacerVoitureVilleOK() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Chicago"));
        Ville atlanta = new Ville("Atlanta");

        Assertions.assertDoesNotThrow(() -> this.instance.actionSeDeplacerVoiture(pionJoueur,atlanta));
        assertEquals(pionJoueur.getVilleActuelle(), atlanta);
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
    void actionSeDeplacerVoitureNbActionMaxAtteint() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        pionJoueur.setNbActions(4);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Atlanta"));
        Ville chicago = new Ville("Chicago");

        Assertions.assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.actionSeDeplacerVoiture(pionJoueur,chicago));
    }

    @Test
    void actionSeDeplacerVoitureVillesNonVoisine() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Atlanta"));
        Ville villeNonVoisine = new Ville("Los_Angeles");

        Assertions.assertThrows(VilleNonVoisineException.class,
                () -> this.instance.actionSeDeplacerVoiture(pionJoueur,villeNonVoisine));
    }


    /**
     *
     *
     *   DEPLACEMENT VOL DIRECT
     *
     */

    @Test
    void actionSeDeplacerVolDirectOK() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Atlanta"));
        Ville madrid = new Ville("Madrid",40,40,new Virus("rojo"));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(madrid));

        Assertions.assertDoesNotThrow(() -> this.instance.actionSeDeplacerVolDirect(pionJoueur,madrid));
        assertEquals(pionJoueur.getVilleActuelle(), madrid);
    }

    @Test
    void actionSeDeplacerVolDirectVilleIntrouvable() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Atlanta"));
        Ville villeIntrouvable = new Ville("qdq",40,40,new Virus("rojo"));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(villeIntrouvable));

        Assertions.assertThrows(VilleIntrouvableException.class,
                () -> this.instance.actionSeDeplacerVolDirect(pionJoueur,villeIntrouvable));

    }

    @Test
    void actionSeDeplacerVolDirectVilleInexistanteDeckJoueur() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        pionJoueur.setVilleActuelle(plateau.getVilleByName("Atlanta"));
        Ville madrid = new Ville("Madrid",40,40,new Virus("rojo"));
        Ville paris = new Ville("Paris",40,40,new Virus("blue"));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));

        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.actionSeDeplacerVolDirect(pionJoueur,madrid));

    }

    /**
     *
     * VOL CHARTER
     */

    @Test
    void actionSeDeplacerVolCharterOK() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        Ville atlanta = plateau.getVilleByName("Atlanta");
        pionJoueur.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));

        Assertions.assertDoesNotThrow(() -> this.instance.actionSeDeplacerVolCharter(pionJoueur,plateau.getVilleByName("Paris")));
        assertEquals(pionJoueur.getVilleActuelle(), plateau.getVilleByName("Paris"));
    }

    @Test
    void actionSeDeplacerVolCharterPasDeCarteVilleActuel() {

        PionJoueur pionJoueur = new PionJoueur("jo",plateau);
        Ville atlanta = plateau.getVilleByName("Atlanta");
        pionJoueur.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("Paris")));

        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.actionSeDeplacerVolCharter(pionJoueur,plateau.getVilleByName("Paris")));

    }

}