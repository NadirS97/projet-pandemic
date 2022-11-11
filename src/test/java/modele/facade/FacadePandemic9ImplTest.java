package modele.facade;

import modele.elements.PionJoueur;
import modele.elements.Plateau;

import modele.elements.Ville;
import modele.elements.Virus;
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
import modele.elements.cartes.evenements.CarteParUneNuitTranquille;
import modele.elements.cartes.evenements.CartePontAerien;
import modele.elements.cartes.evenements.CartePopulationResiliente;
import modele.elements.cartes.evenements.CartePrevision;
import modele.elements.cartes.roles.CarteScientifique;
import modele.elements.enums.CouleurPionsRole;
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
        pionJoueur = new PionJoueur("joueur", plateau);
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
        atlanta.setStationDeRechercheVille(false);
        alger.setStationDeRechercheVille(true);
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
        System.out.println(atlanta);
        // pour simplifier le test on choisis la ville qui se propage plutot que de tester la propagation random
        Assertions.assertDoesNotThrow(() -> this.pionJoueur.getPlateau().propagationMaladie(atlanta, 1));
        System.out.println(atlanta);
        Virus virusBleu = plateau.getLesVirus().get("BLEU");
        IAction traiter = new TraiterMaladie(virusBleu);
        pionJoueur.setVilleActuelle(atlanta);
        Assertions.assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,traiter));
        System.out.println(atlanta);
    }

//=============================================================================================================================
//                                                 ACTION PARTAGE_CONAISSANCE
//=============================================================================================================================

    @Test
    void donnerConaissanceOk(){
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction donnerConaissance = new DonnerConnaissance(joueur2);

        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,donnerConaissance));
        assertFalse(pionJoueur.isVilleOfCarteVilleDeckJoueur(atlanta));
        assertTrue(joueur2.isVilleOfCarteVilleDeckJoueur(atlanta));
    }

    @Test
    void prendreConaissanceOk(){
        PionJoueur joueur2 = new PionJoueur();
        joueur2.setVilleActuelle(atlanta);
        joueur2.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction donnerConaissance = new PrendreConnaissance(joueur2);

        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,donnerConaissance));
        assertFalse(joueur2.isVilleOfCarteVilleDeckJoueur(atlanta));
        assertTrue(pionJoueur.isVilleOfCarteVilleDeckJoueur(atlanta));

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
        IAction action = new DecouvrirRemede();
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action));
    }
    @Test
    void jouerTourActionDecouvrirRemedeScientifiqueOK() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(milan));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new DecouvrirRemede();
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action));
    }

//=============================================================================================================================
//                                                 AUTRES TESTS
//=============================================================================================================================

    @Test
    void piocherCartes(){
        System.out.println(pionJoueur.getDeckJoueur());
        int tailleDeckInitial = pionJoueur.getDeckJoueur().size();
        instance.piocherCartes(pionJoueur);
        assertEquals(tailleDeckInitial+2,pionJoueur.getDeckJoueur().size());
        System.out.println(pionJoueur.getDeckJoueur());
    }

    @Test
    void propagation()  {
        Assertions.assertDoesNotThrow(() -> this.pionJoueur.getPlateau().propagationMaladie(atlanta, 1));
        Assertions.assertDoesNotThrow(() -> this.pionJoueur.getPlateau().propagationMaladie(atlanta, 1));
        Assertions.assertDoesNotThrow(() -> this.pionJoueur.getPlateau().propagationMaladie(atlanta, 1));
        Assertions.assertEquals(3,this.pionJoueur.getPlateau().getVilles().get("Atlanta").getNbCubeVirusVille().get(plateau.getLesVirus().get("BLEU")));
    }

    @Test
    void creationPartieRoleRandom4JoueursOk(){
        System.out.println(instance.partie.getJoueurs());
        Assertions.assertEquals(4,this.instance.partie.getJoueurs().size());
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
        assertDoesNotThrow(() -> instance.jouerEvent(pionJoueur,carteEvenementPopulationResiliente));
    }

    @Test
    void jouerCarteEventPontAerien(){
        PionJoueur pionJoueur2 = new PionJoueur();
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur2.setPermissionPontAerien(true);
        System.out.println(pionJoueur2.getVilleActuelle());
        CartePontAerien cartePontAerien = new CartePontAerien();
        cartePontAerien.setPionChoisis(pionJoueur2);
        cartePontAerien.setVilleChoisis(paris);
        pionJoueur.getDeckJoueur().add(cartePontAerien);
        assertDoesNotThrow(()-> instance.jouerEvent(pionJoueur,cartePontAerien));
        System.out.println(pionJoueur2.getVilleActuelle());
    }

    @Test
    void jouerCarteEventPrevision() {
        PionJoueur pionJoueur = new PionJoueur();
        Plateau plateauTest = instance.partie.getPlateau();
        LinkedList<CarteJoueur> cartesJoueurTests = new LinkedList<>();
        cartesJoueurTests.add(new CarteVille(Ville.builder().nomVille("Test1").build()));
        cartesJoueurTests.add(new CarteVille(Ville.builder().nomVille("Test2").build()));
        cartesJoueurTests.add(new CarteVille(Ville.builder().nomVille("Test3").build()));
        cartesJoueurTests.add(new CarteVille(Ville.builder().nomVille("Test4").build()));
        cartesJoueurTests.add(new CarteVille(Ville.builder().nomVille("Test5").build()));
        cartesJoueurTests.add(new CarteVille(Ville.builder().nomVille("Test6").build()));
        plateauTest.setPiocheCarteJoueur(cartesJoueurTests);
        pionJoueur.setPlateau(plateauTest);

        CartePrevision cartePrevision = new CartePrevision();
        cartePrevision.execEffet(pionJoueur);
        List<CarteJoueur> mainAMelanger = pionJoueur.getMainAReorganiser();
        mainAMelanger.forEach(c -> System.out.println("Nom de carte : " + c.getNomCarte()));
        Collections.shuffle(mainAMelanger);
        cartePrevision.ajouterDansPiocheJoueurs(pionJoueur, mainAMelanger);
        plateauTest.getPiocheCarteJoueur().forEach(c -> System.out.println("Nom de carte réorganisée : " + c.getNomCarte()));
    }
}