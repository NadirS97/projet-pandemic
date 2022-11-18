package modele.facade;

import modele.elements.*;

import modele.elements.actions.IAction;
import modele.elements.actions.construire_une_station.ConstruireUneStation;
import modele.elements.actions.decouvrir_remede.DecouvrirRemede;
import modele.elements.actions.deplacement.DeplacementNavette;
import modele.elements.actions.deplacement.DeplacementVoiture;
import modele.elements.actions.deplacement.DeplacementVolCharter;
import modele.elements.actions.deplacement.DeplacementVolDirect;
import modele.elements.actions.partager_connaissance.DonnerConnaissance;
import modele.elements.actions.partager_connaissance.PrendreConnaissance;
import modele.elements.actions.traiter_maladie.TraiterMaladie;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CartePropagation;
import modele.elements.cartes.CarteVille;
import modele.elements.cartes.evenements.*;
import modele.elements.cartes.roles.CarteChercheuse;
import modele.elements.cartes.roles.CarteMedecin;
import modele.elements.cartes.roles.CarteScientifique;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.EtatVirus;
import modele.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacadePandemic9ImplTest {

    private FacadePandemic9Impl instance;
    private PionJoueur pionJoueur;
    private Ville atlanta;
    private Ville chicago;
    private Ville paris;
    private Ville alger;
    private Ville milan;
    private Ville tokyo;
    private Ville istanbul;
    private Ville miami;
    private Plateau plateau;



    @BeforeEach
    void setUp() throws Exception {
        instance = new FacadePandemic9Impl(4);
        plateau = instance.partie.getPlateau();
        pionJoueur = new PionJoueur( plateau);
        atlanta = plateau.getVilleByName("Atlanta");
        chicago = plateau.getVilleByName("Chicago");
        paris = plateau.getVilleByName("Paris");
        alger = plateau.getVilleByName("Alger");
        milan = plateau.getVilleByName("Milan");
        tokyo = plateau.getVilleByName("Tokyo");
        istanbul = plateau.getVilleByName("Istanbul");
        miami = plateau.getVilleByName("Miami");
        pionJoueur.setVilleActuelle(atlanta);
    }



//=============================================================================================================================
//                                                ACTION DeplacementVoiture
//=============================================================================================================================

    @Test
    void jouerTourActionDeplacementVoitureOK() {
        IAction action = new DeplacementVoiture(chicago);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action));
        assertEquals(pionJoueur.getVilleActuelle(), chicago);
    }

    @Test
    void jouerTourActionDeplacementVoitureVilleDestinationEstVilleActuelle() {
        IAction action = new DeplacementVoiture(atlanta);
        Assertions.assertThrows(VilleDestinationEstVilleActuelleException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionDeplacementVoitureNbTourMaxAtteint() {
        IAction action = new DeplacementVoiture(chicago);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action));
        IAction action1 = new DeplacementVoiture(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action1));
        IAction action2 = new DeplacementVoiture(chicago);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action2));
        IAction action3 = new DeplacementVoiture(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action3));
        IAction action4 = new DeplacementVoiture(chicago);
        Assertions.assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.jouerAction(pionJoueur,action4));
    }

    @Test
    void jouerTourActionDeplacementVoitureVilleIntrouvable() {
        IAction action = new DeplacementVoiture(new Ville("Introuvable"));
        Assertions.assertThrows(VilleIntrouvableException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionDeplacementVoitureVilleNonVoisine() {
        IAction action = new DeplacementVoiture(paris);
        Assertions.assertThrows(VilleNonVoisineException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

//=============================================================================================================================
//                                                ACTION DeplacementVolDirect
//=============================================================================================================================

    @Test
    void jouerTourActionDeplacementVolDirectOK() {
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        IAction action = new DeplacementVolDirect(paris);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action));
        assertEquals(pionJoueur.getVilleActuelle(), paris);
    }

    @Test
    void jouerTourActionDeplacementVolDirectVilleDestinationEstVilleActuelle() {
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DeplacementVolDirect(atlanta);
        Assertions.assertThrows(VilleDestinationEstVilleActuelleException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionDeplacementVolDirectVilleIntrouvable() {
        IAction action = new DeplacementVolDirect(new Ville("ville_introuvable"));
        Assertions.assertThrows(VilleIntrouvableException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void actionSeDeplacerVolDirectVilleInexistanteDeckJoueur() {
        IAction action = new DeplacementVolDirect(paris);
        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

//=============================================================================================================================
//                                                ACTION DeplacementVolCharter
//=============================================================================================================================

    @Test
    void jouerTourActionDeplacementVolCharterOK() {
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DeplacementVolCharter(paris);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action));
        assertEquals(pionJoueur.getVilleActuelle(), paris);
    }

    @Test
    void jouerTourActionDeplacementVolCharterPasDeCarteVilleActuel() {
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        IAction action = new DeplacementVolCharter(milan);
        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,() -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionDeplacementVolCharterVilleIntrouvable() {
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        IAction action = new DeplacementVolCharter(new Ville("ae"));
        Assertions.assertThrows(VilleIntrouvableException.class,() -> this.instance.jouerAction(pionJoueur,action));
    }

//=============================================================================================================================
//                                                ACTION ConstruireUneStation
//=============================================================================================================================

    @Test
    void jouerTourActionConstruireUneStationOK() {
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new ConstruireUneStation();
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action));
        assertTrue(pionJoueur.getVilleActuelle().isStationDeRechercheVille());
        assertFalse(pionJoueur.isVilleOfCarteVilleDeckJoueur(atlanta));
    }

    @Test
    void jouerTourActionConstruireUneStationDeplacementStationOK(){
        atlanta.setStationDeRechercheVille(false);
        chicago.setStationDeRechercheVille(true);
        paris.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        milan.setStationDeRechercheVille(true);
        tokyo.setStationDeRechercheVille(true);
        istanbul.setStationDeRechercheVille(true);
        pionJoueur.setVilleActuelle(atlanta);
        IAction action = new ConstruireUneStation(chicago);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action));
        assertTrue(atlanta.isStationDeRechercheVille());
        assertFalse(chicago.isStationDeRechercheVille());
    }

    @Test
    void jouerTourActionConstruireUneStationNbActionsMaxTourAtteint() {
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(alger));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        IAction action1 = new DeplacementVolDirect(chicago);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action1));
        IAction action2 = new DeplacementVolDirect(paris);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action2));
        IAction action3 = new DeplacementVolDirect(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action3));
        IAction action4 =  new DeplacementVolDirect(milan);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action4));
        IAction action5 = new ConstruireUneStation();
        Assertions.assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.jouerAction(pionJoueur,action5));
    }

    @Test
    void jouerTourActionConstruireUneStationDeplacementStationVilleAvecAucuneStationDeRecherche(){
        atlanta.setStationDeRechercheVille(false);
        istanbul.setStationDeRechercheVille(false);
        chicago.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        paris.setStationDeRechercheVille(true);
        milan.setStationDeRechercheVille(true);
        tokyo.setStationDeRechercheVille(true);
        miami.setStationDeRechercheVille(true);
        pionJoueur.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new ConstruireUneStation(istanbul);
        Assertions.assertThrows(VilleAvecAucuneStationDeRechercheException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionConstruireUneStationDeplacementStationVilleActuellePossedeDejaUneStationDeRecherche(){
        atlanta.setStationDeRechercheVille(true);
        chicago.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        paris.setStationDeRechercheVille(true);
        milan.setStationDeRechercheVille(true);
        tokyo.setStationDeRechercheVille(true);
        miami.setStationDeRechercheVille(true);
        pionJoueur.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new ConstruireUneStation(chicago);
        Assertions.assertThrows(VilleActuellePossedeDejaUneStationDeRechercheException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionConstruireUneStationCarteVilleInexistanteDansDeckJoueur(){
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        IAction action = new ConstruireUneStation();
        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionConstruireUneStationVilleActuellePossedeDejaUneStationDeRecherche(){
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new ConstruireUneStation();
        Assertions.assertThrows(VilleActuellePossedeDejaUneStationDeRechercheException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

//=============================================================================================================================
//                                                 ACTION DeplacementNavette
//=============================================================================================================================

    @Test
    void jouerTourActionDeplacementNavetteOK() {
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        IAction action = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action));
        assertEquals(pionJoueur.getVilleActuelle(), alger);
    }

    @Test
    void jouerTourActionDeplacementNavetteVilleAvecAucuneStationDeRechercheVilleDestination() {
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(false);
        IAction action = new DeplacementNavette(alger);
        Assertions.assertThrows(VilleAvecAucuneStationDeRechercheException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionDeplacementNavetteVilleAvecAucuneStationDeRechercheVilleActuelle() {
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(false);
        pionJoueur.setRoleJoueur(new CarteMedecin(CouleurPionsRole.BLANC));
        IAction action = new DeplacementNavette(alger);
        Assertions.assertThrows(VilleAvecAucuneStationDeRechercheException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionDeplacementNavetteVilleDestinationEstVilleActuelle() {
        atlanta.setStationDeRechercheVille(true);
        IAction action = new DeplacementNavette(atlanta);
        Assertions.assertThrows(VilleDestinationEstVilleActuelleException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionDeplacementNavetteNbActionsMaxTourAtteint() {
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        IAction action1 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action1));
        IAction action2 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action2));
        IAction action3 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action3));
        IAction action4 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action4));
        IAction action5 = new DeplacementNavette(alger);
        Assertions.assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.jouerAction(pionJoueur,action5));
    }

//=============================================================================================================================
//                                                 ACTION TRAITER MALADIE
//=============================================================================================================================

    @Test
    void jouerTourActionTraiterMaladieNonTraiteOK(){

        // pour simplifier le test on choisis la ville qui se propage plutot que de tester la propagation random
        Assertions.assertDoesNotThrow(() -> this.pionJoueur.getPlateau().propagationMaladie(atlanta, 1));

        Virus virusBleu = plateau.getLesVirus().get("BLEU");
        IAction traiter = new TraiterMaladie(virusBleu);
        pionJoueur.setVilleActuelle(atlanta);
        Assertions.assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,traiter));

    }

//=============================================================================================================================
//                                                 ACTION PARTAGE_CONAISSANCE
//=============================================================================================================================

    @Test
    void donnerConnaissanceOk(){
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        joueur2.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.BLANC));
        IAction donnerConaissance = new DonnerConnaissance(joueur2);

        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,donnerConaissance));
        assertFalse(pionJoueur.isVilleOfCarteVilleDeckJoueur(atlanta));
        assertTrue(joueur2.isVilleOfCarteVilleDeckJoueur(atlanta));
    }

    @Test
    void donnerConnaissanceChercheuseOk(){
        pionJoueur.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new DonnerConnaissance(joueur2, chicago);
        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,action));
        assertFalse(pionJoueur.isVilleOfCarteVilleDeckJoueur(chicago));
        assertTrue(joueur2.isVilleOfCarteVilleDeckJoueur(chicago));
    }

    @Test
    void donnerConnaissanceChercheuseDonneeManquante(){
        pionJoueur.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new DonnerConnaissance(joueur2);
        Assertions.assertThrows(DonneeManquanteException.class,
                () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void donnerConnaissanceChercheuseCarteVilleInexistanteDansDeckJoueur(){
        pionJoueur.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DonnerConnaissance(joueur2, chicago);
        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void donnerConnaissanceNbActionsMaxTourAtteint(){
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action1 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action1));
        IAction action2 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action2));
        IAction action3 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action3));
        IAction action4 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action4));
        IAction action5 = new DonnerConnaissance(joueur2);
        Assertions.assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.jouerAction(pionJoueur,action5));
    }

    @Test
    void donnerConnaissanceJoueursNonPresentMemeVille(){
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(chicago);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DonnerConnaissance(joueur2);
        Assertions.assertThrows(JoueursNonPresentMemeVilleException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void donnerConnaissanceCarteVilleInexistanteDansDeckJoueur(){
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        IAction action = new DonnerConnaissance(joueur2);
        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void prendreConnaissanceOk(){
        // pionJoueur : Joueur qui va prendre
        // joueur2 : Joueur qui va donner
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setRoleJoueur(new CarteMedecin(CouleurPionsRole.ORANGE));
        joueur2.setVilleActuelle(atlanta);
        joueur2.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction prendreConnaissance = new PrendreConnaissance(joueur2);
        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,prendreConnaissance));
        assertFalse(joueur2.isVilleOfCarteVilleDeckJoueur(atlanta));
        assertTrue(pionJoueur.isVilleOfCarteVilleDeckJoueur(atlanta));
    }

    @Test
    void prendreConnaissanceChercheuseOk(){
        // pionJoueur : Joueur qui va prendre
        // joueur2 : Role CHERCHEUSE : Joueur qui va donner
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        joueur2.setVilleActuelle(atlanta);
        joueur2.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction prendreConnaissance = new PrendreConnaissance(joueur2, chicago);
        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,prendreConnaissance));
        assertTrue(pionJoueur.isVilleOfCarteVilleDeckJoueur(chicago));
        assertFalse(joueur2.isVilleOfCarteVilleDeckJoueur(chicago));
    }

    @Test
    void prendreConnaissanceChercheuseDonneeManquante(){
        // pionJoueur : Joueur qui va prendre
        // joueur2 : Role CHERCHEUSE : Joueur qui va donner
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        joueur2.setVilleActuelle(atlanta);
        joueur2.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new PrendreConnaissance(joueur2);
        Assertions.assertThrows(DonneeManquanteException.class,
                () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void prendreConnaissanceChercheuseCarteVilleInexistanteDansDeckJoueur(){
        // pionJoueur : Joueur qui va prendre
        // joueur2 : Role CHERCHEUSE : Joueur qui va donner
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        joueur2.setVilleActuelle(atlanta);
        joueur2.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new PrendreConnaissance(joueur2, atlanta);
        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void prendreConnaissanceNbActionsMaxTourAtteint(){
        // pionJoueur : Joueur qui va prendre
        // joueur2 : Joueur qui va donner
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        joueur2.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action1 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action1));
        IAction action2 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action2));
        IAction action3 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action3));
        IAction action4 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action4));
        IAction action5 = new PrendreConnaissance(joueur2);
        Assertions.assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.jouerAction(pionJoueur,action5));
    }

    @Test
    void prendreConnaissanceJoueursNonPresentMemeVille(){
        // pionJoueur : Joueur qui va prendre
        // joueur2 : Joueur qui va donner
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(chicago);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new PrendreConnaissance(joueur2);
        Assertions.assertThrows(JoueursNonPresentMemeVilleException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void prendreConnaissanceCarteVilleInexistanteDansDeckJoueur(){
        // pionJoueur : Joueur qui va prendre
        // joueur2 : Joueur qui va donner
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        joueur2.setRoleJoueur(new CarteMedecin(CouleurPionsRole.ORANGE));
        IAction action = new PrendreConnaissance(joueur2);
        Assertions.assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

//=============================================================================================================================
//                                                 ACTION DecouvrirRemede
//=============================================================================================================================

    @Test
    void jouerTourActionDecouvrirRemedeOK() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("New_York")));
        int tailleDeckApresAjoutDesCartes = pionJoueur.getDeckJoueur().size();
        int tailleDefausseInitial = pionJoueur.getPlateau().getDefausseCarteJoueur().size();
        IAction action = new DecouvrirRemede();
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action));
        assertEquals(tailleDeckApresAjoutDesCartes - 5, pionJoueur.getDeckJoueur().size());
        assertEquals(tailleDefausseInitial + 5, pionJoueur.getPlateau().getDefausseCarteJoueur().size());
        String couleurVirus = atlanta.getCouleurVirusVille();
        assertEquals(pionJoueur.getPlateau().getLesVirus().get(couleurVirus).getEtatVirus(), EtatVirus.TRAITE);
    }

    @Test
    void jouerTourActionDecouvrirRemedeScientifiqueOK() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        int tailleDeckInitial = pionJoueur.getDeckJoueur().size();
        int tailleDefausseInitial = pionJoueur.getPlateau().getDefausseCarteJoueur().size();
        IAction action = new DecouvrirRemede();
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action));
        assertEquals(tailleDeckInitial - 4, pionJoueur.getDeckJoueur().size());
        assertEquals(tailleDefausseInitial + 4, pionJoueur.getPlateau().getDefausseCarteJoueur().size());
        String couleurVirus = atlanta.getCouleurVirusVille();
        assertEquals(pionJoueur.getPlateau().getLesVirus().get(couleurVirus).getEtatVirus(), EtatVirus.TRAITE);
    }

    @Test
    void jouerTourActionDecouvrirRemedeVilleAvecAucuneStationDeRecherche() {
        atlanta.setStationDeRechercheVille(false);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("New_York")));
        IAction action = new DecouvrirRemede();
        Assertions.assertThrows(VilleAvecAucuneStationDeRechercheException.class, () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void jouerTourActionDecouvrirRemedeScientifiqueVilleAvecAucuneStationDeRecherche() {
        atlanta.setStationDeRechercheVille(false);
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new DecouvrirRemede();
        Assertions.assertThrows(VilleAvecAucuneStationDeRechercheException.class, () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void jouerTourActionDecouvrirRemedeNombreDeCartesVilleDansDeckJoueurInvalide() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DecouvrirRemede();
        Assertions.assertThrows(NombreDeCartesVilleDansDeckJoueurInvalideException.class, () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void jouerTourActionDecouvrirRemedeScientifiqueNombreDeCartesVilleDansDeckJoueurInvalide() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DecouvrirRemede();
        Assertions.assertThrows(NombreDeCartesVilleDansDeckJoueurInvalideException.class, () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void jouerTourActionDecouvrirRemedeNbActionsMaxTourAtteintException() {
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("New_York")));
        IAction action1 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action1));
        IAction action2 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action2));
        IAction action3 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action3));
        IAction action4 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action4));
        IAction action5 = new DecouvrirRemede();
        Assertions.assertThrows(NbActionsMaxTourAtteintException.class, () -> this.instance.jouerAction(pionJoueur, action5));
    }

    @Test
    void jouerTourActionDecouvrirRemedeScientifiqueNbActionsMaxTourAtteintException() {
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action1 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action1));
        IAction action2 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action2));
        IAction action3 = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action3));
        IAction action4 = new DeplacementNavette(atlanta);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action4));
        IAction action5 = new DecouvrirRemede();
        Assertions.assertThrows(NbActionsMaxTourAtteintException.class, () -> this.instance.jouerAction(pionJoueur, action5));
    }

    @Test
    void jouerTourActionDecouvrirRemedeVirusDejaTraite() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("New_York")));
        IAction action1 = new DecouvrirRemede();
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action1));

        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(plateau.getVilleByName("New_York")));
        IAction action2 = new DecouvrirRemede();
        Assertions.assertThrows(VirusDejaTraiteException.class, () -> this.instance.jouerAction(pionJoueur, action2));
    }

    @Test
    void jouerTourActionDecouvrirScientifiqueRemedeVirusDejaTraite() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action1 = new DecouvrirRemede();
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action1));

        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action2 = new DecouvrirRemede();
        Assertions.assertThrows(VirusDejaTraiteException.class, () -> this.instance.jouerAction(pionJoueur, action2));
    }

//=============================================================================================================================
//                                                 AUTRES TESTS
//=============================================================================================================================

    @Test
    void piocherCartes(){

        int tailleDeckInitial = pionJoueur.getDeckJoueur().size();
        Assertions.assertDoesNotThrow(() -> this.instance.piocherCartes(pionJoueur));
        assertEquals(tailleDeckInitial+2,pionJoueur.getDeckJoueur().size());
    }





//=============================================================================================================================
//                                                 EFFET Evenement
//=============================================================================================================================

    @Test
    void jouerCarteEventParUneNuitTranquille(){
        CarteEvenement carteEvenementNuitTranquille = new CarteParUneNuitTranquille();
        pionJoueur.getDeckJoueur().add(carteEvenementNuitTranquille);

        assertDoesNotThrow(() -> instance.jouerEvent(pionJoueur, carteEvenementNuitTranquille));
        assertThrows(NuitTranquilleException.class, () -> instance.propagation(pionJoueur));
        assertTrue(pionJoueur.getPlateau().isEffetParUneNuitTranquilleActif());
    }

    @Test
    void jouerCarteEventPopulationResiliente(){
        CartePropagation cartePropagation = new CartePropagation(atlanta);
        pionJoueur.getPlateau().getDefausseCartePropagation().add(cartePropagation);
        CartePopulationResiliente carteEvenementPopulationResiliente = new CartePopulationResiliente();
        pionJoueur.getDeckJoueur().add(carteEvenementPopulationResiliente);
        carteEvenementPopulationResiliente.setCartePropagationChoisis(cartePropagation);
        assertDoesNotThrow(() -> instance.jouerEvent(pionJoueur, carteEvenementPopulationResiliente));
    }

    @Test
    void jouerCarteEventPontAerien(){
        PionJoueur pionJoueur2 = new PionJoueur();
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur2.setPermissionPontAerien(true);

        CartePontAerien cartePontAerien = new CartePontAerien();
        cartePontAerien.setPionChoisis(pionJoueur2);
        cartePontAerien.setVilleChoisis(paris);
        pionJoueur.getDeckJoueur().add(cartePontAerien);
        assertDoesNotThrow(()-> instance.jouerEvent(pionJoueur, cartePontAerien));

    }

    @Test
    void jouerCarteEventPrevision() {
        PionJoueur pionJoueur = new PionJoueur();
        Plateau plateauTest = instance.partie.getPlateau();
        List<CartePropagation> cartesPropagationTests = new LinkedList<>();
        cartesPropagationTests.add(new CartePropagation(Ville.builder().nomVille("Test1").build()));
        cartesPropagationTests.add(new CartePropagation(Ville.builder().nomVille("Test2").build()));
        cartesPropagationTests.add(new CartePropagation(Ville.builder().nomVille("Test3").build()));
        cartesPropagationTests.add(new CartePropagation(Ville.builder().nomVille("Test4").build()));
        cartesPropagationTests.add(new CartePropagation(Ville.builder().nomVille("Test5").build()));
        cartesPropagationTests.add(new CartePropagation(Ville.builder().nomVille("Test6").build()));
        plateauTest.setPiocheCartePropagation(cartesPropagationTests);
        pionJoueur.setPlateau(plateauTest);

        CartePrevision cartePrevision = new CartePrevision();
        cartePrevision.execEffet(pionJoueur);
        List<CartePropagation> mainAMelanger = pionJoueur.getMainAReorganiser();
        Collections.shuffle(mainAMelanger);
        cartePrevision.ajouterDansPiochePropagation(pionJoueur, mainAMelanger);
        pionJoueur.getDeckJoueur().add(cartePrevision);
        assertEquals(mainAMelanger, pionJoueur.getPlateau().getPiocheCartePropagation());
    }

    @Test
    void jouerCarteSubventionPublique() throws VilleIntrouvableException {
        PionJoueur pionJoueur = new PionJoueur();
        Plateau plateauTest = plateau;
        pionJoueur.setPlateau(plateauTest);
        CarteSubventionPublique carteSubventionPublique = new CarteSubventionPublique();
        carteSubventionPublique.placerStationRecherche(pionJoueur, "Atlanta");
        pionJoueur.getDeckJoueur().add(carteSubventionPublique);
        assertDoesNotThrow(()-> instance.jouerEvent(pionJoueur, carteSubventionPublique));
    }

}