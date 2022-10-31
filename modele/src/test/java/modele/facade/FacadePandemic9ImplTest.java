package modele.facade;

import modele.elements.PionJoueur;
import modele.elements.Plateau;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.actions.construire_une_station.ConstruireUneStation;
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


//=============================================================================================================================
//                                                ACTION DeplacementVoiture
//=============================================================================================================================
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

//=============================================================================================================================
//                                                ACTION DeplacementVolDirect
//=============================================================================================================================
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

//=============================================================================================================================
//                                                ACTION ConstruireUneStation
//=============================================================================================================================

    @Test
    void jouerTourActionConstruireUneStationOK() {
        PionJoueur pionJoueur = new PionJoueur("nadir", plateau, 4);

        Ville villeActuelle = plateau.getVilleByName("Atlanta");

        pionJoueur.setVilleActuelle(villeActuelle);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(villeActuelle));

        IAction action = new ConstruireUneStation();
        Assertions.assertDoesNotThrow(() -> this.instance.jouerTour(pionJoueur, action));

        assertTrue(pionJoueur.getVilleActuelle().isStationDeRechercheVille());
        assertFalse(pionJoueur.isVilleOfCarteVilleDeckJoueur(villeActuelle));
    }

    @Test
    void jouerTourActionConstruireUneStationDeplacementStationOK(){
        PionJoueur pionJoueur = new PionJoueur("nadir", plateau, 4);

        Ville villeSansStationRecherche = plateau.getVilleByName("Atlanta");
        villeSansStationRecherche.setStationDeRechercheVille(false);

        Ville ville1 = plateau.getVilleByName("Chicago");
        ville1.setStationDeRechercheVille(true);
        Ville ville2 = plateau.getVilleByName("Alger");
        ville2.setStationDeRechercheVille(true);
        Ville ville3 = plateau.getVilleByName("Paris");
        ville3.setStationDeRechercheVille(true);
        Ville ville4 = plateau.getVilleByName("Milan");
        ville4.setStationDeRechercheVille(true);
        Ville ville5 = plateau.getVilleByName("Tokyo");
        ville5.setStationDeRechercheVille(true);
        Ville ville6 = plateau.getVilleByName("Miami");
        ville6.setStationDeRechercheVille(true);

        pionJoueur.setVilleActuelle(villeSansStationRecherche);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(villeSansStationRecherche));

        IAction action = new ConstruireUneStation(ville1);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerTour(pionJoueur, action));

        assertTrue(villeSansStationRecherche.isStationDeRechercheVille());
        assertFalse(ville1.isStationDeRechercheVille());
    }

    @Test
    void jouerTourActionConstruireUneStationDeplacementStationVilleAvecAucuneStationDeRecherche(){
        PionJoueur pionJoueur = new PionJoueur("nadir", plateau, 4);

        Ville villeSansStationRecherche = plateau.getVilleByName("Atlanta");
        villeSansStationRecherche.setStationDeRechercheVille(false);

        Ville villeSansStationRecherche2 = plateau.getVilleByName("Istanbul");
        villeSansStationRecherche2.setStationDeRechercheVille(false);

        Ville ville1 = plateau.getVilleByName("Chicago");
        ville1.setStationDeRechercheVille(true);
        Ville ville2 = plateau.getVilleByName("Alger");
        ville2.setStationDeRechercheVille(true);
        Ville ville3 = plateau.getVilleByName("Paris");
        ville3.setStationDeRechercheVille(true);
        Ville ville4 = plateau.getVilleByName("Milan");
        ville4.setStationDeRechercheVille(true);
        Ville ville5 = plateau.getVilleByName("Tokyo");
        ville5.setStationDeRechercheVille(true);
        Ville ville6 = plateau.getVilleByName("Miami");
        ville6.setStationDeRechercheVille(true);

        pionJoueur.setVilleActuelle(villeSansStationRecherche);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(villeSansStationRecherche));

        IAction action = new ConstruireUneStation(villeSansStationRecherche2);
        Assertions.assertThrows(VilleAvecAucuneStationDeRechercheException.class,
                () -> this.instance.jouerTour(pionJoueur,action));
    }

    @Test
    void jouerTourActionConstruireUneStationDeplacementStationVilleActuellePossedeDejaUneStationDeRecherche(){
        PionJoueur pionJoueur = new PionJoueur("nadir", plateau, 4);

        Ville villeSansStationRecherche = plateau.getVilleByName("Atlanta");
        villeSansStationRecherche.setStationDeRechercheVille(true);

        Ville ville1 = plateau.getVilleByName("Chicago");
        ville1.setStationDeRechercheVille(true);
        Ville ville2 = plateau.getVilleByName("Alger");
        ville2.setStationDeRechercheVille(true);
        Ville ville3 = plateau.getVilleByName("Paris");
        ville3.setStationDeRechercheVille(true);
        Ville ville4 = plateau.getVilleByName("Milan");
        ville4.setStationDeRechercheVille(true);
        Ville ville5 = plateau.getVilleByName("Tokyo");
        ville5.setStationDeRechercheVille(true);
        Ville ville6 = plateau.getVilleByName("Miami");
        ville6.setStationDeRechercheVille(true);

        pionJoueur.setVilleActuelle(villeSansStationRecherche);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(villeSansStationRecherche));

        IAction action = new ConstruireUneStation(ville1);
        Assertions.assertThrows(VilleActuellePossedeDejaUneStationDeRechercheException.class,
                () -> this.instance.jouerTour(pionJoueur,action));
    }

    @Test
    void jouerTourActionConstruireUneStationCarteVilleInexistanteDansDeckJoueur(){
        PionJoueur pionJoueur = new PionJoueur("nadir", plateau, 4);

        Ville villeActuelle = plateau.getVilleByName("Atlanta");

        pionJoueur.setVilleActuelle(villeActuelle);

        IAction action = new ConstruireUneStation();
        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerTour(pionJoueur,action));
    }

    @Test
    void jouerTourActionConstruireUneStationVilleActuellePossedeDejaUneStationDeRecherche(){
        PionJoueur pionJoueur = new PionJoueur("nadir", plateau, 4);

        Ville ville = plateau.getVilleByName("Atlanta");
        ville.setStationDeRechercheVille(true);

        pionJoueur.setVilleActuelle(ville);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(ville));

        IAction action = new ConstruireUneStation();
        Assertions.assertThrows(VilleActuellePossedeDejaUneStationDeRechercheException.class,
                () -> this.instance.jouerTour(pionJoueur,action));
    }


//=============================================================================================================================
//                                                 ACTION
//=============================================================================================================================

}