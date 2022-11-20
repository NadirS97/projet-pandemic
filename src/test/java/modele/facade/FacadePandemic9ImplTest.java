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
import modele.elements.actions.planificateur_urgence.EntreposerEvenementRolePlanificateur;
import modele.elements.actions.traiter_maladie.TraiterMaladie;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CartePropagation;
import modele.elements.cartes.CarteVille;
import modele.elements.cartes.evenements.*;
import modele.elements.cartes.roles.*;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.EtatVirus;
import modele.exceptions.*;
import modele.utils.DonneesVariablesStatiques;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacadePandemic9ImplTest {

    private FacadePandemic9Impl instance;
    private PionJoueur pionJoueur;
    private PionJoueur pionJoueur2;
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
    void setUp() throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        instance = new FacadePandemic9Impl();
        instance.creerPartieQuatreJoueurs();
        plateau = instance.partie.getPlateau();
        pionJoueur = instance.partie.getJoueurActuel();
        pionJoueur2 = instance.partie.getJoueurSuivant();
        atlanta = plateau.getVilleByName("Atlanta");
        chicago = plateau.getVilleByName("Chicago");
        paris = plateau.getVilleByName("Paris");
        alger = plateau.getVilleByName("Alger");
        milan = plateau.getVilleByName("Milan");
        tokyo = plateau.getVilleByName("Tokyo");
        istanbul = plateau.getVilleByName("Istanbul");
        miami = plateau.getVilleByName("Miami");


        // trop de conflit avec les initialisations des cartes distribué aux joueurs créés et les assert/throw qui check si
        // le joueur possèdent la carte dite
        // donc on vide les deck des deux joueurs qui possèdent chacun 2 cartes à la base pour une partie de 4.
        pionJoueur.getDeckJoueur().remove(0);
        pionJoueur.getDeckJoueur().remove(0);
        pionJoueur2.getDeckJoueur().remove(0);
        pionJoueur2.getDeckJoueur().remove(0);
    }


    @Test
    void creationPartie4Joueurs(){
        Assertions.assertDoesNotThrow(()-> this.instance.creerPartieQuatreJoueurs());
        // on verifie que l'on a bien 4 pionsJoueur creer dans la partie
        assertEquals(4,instance.partie.getJoueurs().size());
        // que les 4 cartes roles ont bien été distribué parmis les 7 présente dans le plateau de base lors de la création
        assertEquals(3,instance.partie.getPlateau().getToutesLesCartesRolesExistante().size());
        // que la piocheCartePropagation contient 48 cartes - les 9 retourné à l'initialisation du jeu
        assertEquals(39,instance.partie.getPlateau().getPiocheCartePropagation().size());
    }


//=============================================================================================================================
//                                                ACTION DeplacementVoiture
//=============================================================================================================================

//------------ 1- Tests jouerAction() avec l'action DeplacementVoiture OK
    @Test
    void jouerTourActionDeplacementVoitureOK() {
        IAction action = new DeplacementVoiture(chicago);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action));
        assertEquals(pionJoueur.getVilleActuelle(), chicago);
    }

//------------ 2- Tests jouerAction() avec l'action DeplacementVoiture KO
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

//------------ 1- Tests jouerAction() avec l'action DeplacementVolDirect OK
    @Test
    void jouerTourActionDeplacementVolDirectOK() {
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(paris));
        IAction action = new DeplacementVolDirect(paris);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action));
        assertEquals(pionJoueur.getVilleActuelle(), paris);
    }

//------------ 2- Tests jouerAction() avec l'action DeplacementVolDirect KO
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

//------------ 1- Tests jouerAction() avec l'action DeplacementVolCharter OK

    @Test
    void jouerTourActionDeplacementVolCharterOK() {
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DeplacementVolCharter(paris);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action));
        assertEquals(pionJoueur.getVilleActuelle(), paris);
    }

//------------ 2- Tests jouerAction() avec l'action DeplacementVolCharter KO

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

//-----------------------------------------------------------------------------------------------------------------------------
// - Les roles sont distribués aléatoirement entre les joueurs en début de partie
// - Etant une action qui s'execute differemment si le role du joueur est : "EXPERT_AUX_OPERATIONS"
// Alors, pour effectuer certains tests on est contraint de set un role autre que "EXPERT_AUX_OPERATIONS"
// (Tests fonctionnent avec n'importe quel autre role mis à part EXPERT_AUX_OPERATIONS, qui lui a un/des tests propres)
//-----------------------------------------------------------------------------------------------------------------------------

//------------ 1- Tests jouerAction() avec l'action ConstruireUneStation OK

    @Test
    void jouerTourActionConstruireUneStationOK() {
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur.setVilleActuelle(alger);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(alger));
        IAction action = new ConstruireUneStation();
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action));
        assertTrue(pionJoueur.getVilleActuelle().isStationDeRechercheVille());
        assertFalse(pionJoueur.isVilleOfCarteVilleDeckJoueur(alger));
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

//------------ 2- Tests jouerAction() avec l'action ConstruireUneStation KO

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
        pionJoueur.setVilleActuelle(alger);
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

//-----------------------------------------------------------------------------------------------------------------------------
// - Les roles sont distribués aléatoirement entre les joueurs en début de partie
// - Etant une action qui s'execute differemment si le role du joueur est : "EXPERT_AUX_OPERATIONS"
// Alors, pour effectuer certains tests on est contraint de set un role autre que "EXPERT_AUX_OPERATIONS"
// (Tests fonctionnent avec n'importe quel autre role mis à part EXPERT_AUX_OPERATIONS, qui lui a un/des tests propres)
//-----------------------------------------------------------------------------------------------------------------------------

//------------ 1- Tests jouerAction() avec l'action DeplacementNavette OK

    @Test
    void jouerTourActionDeplacementNavetteOK() {
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        IAction action = new DeplacementNavette(alger);
        Assertions.assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur, action));
        assertEquals(pionJoueur.getVilleActuelle(), alger);
    }

//------------ 2- Tests jouerAction() avec l'action DeplacementNavette KO

    @Test
    void jouerTourActionDeplacementNavetteVilleAvecAucuneStationDeRechercheVilleDestination() {
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(false);
        pionJoueur.setRoleJoueur(new CarteMedecin(CouleurPionsRole.ORANGE));
        IAction action = new DeplacementNavette(alger);
        Assertions.assertThrows(VilleAvecAucuneStationDeRechercheException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void jouerTourActionDeplacementNavetteVilleAvecAucuneStationDeRechercheVilleActuelle() {
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(false);
        pionJoueur.setRoleJoueur(new CarteMedecin(CouleurPionsRole.ORANGE));
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
//                                                 ACTION TraiterMaladie
//=============================================================================================================================

//-----------------------------------------------------------------------------------------------------------------------------
// - Les roles sont distribués aléatoirement entre les joueurs en début de partie
// - Etant une action qui s'execute differemment si le role du joueur est : "MEDECIN"
// Alors, pour effectuer certains tests on est contraint de set un role autre que "MEDECIN"
// (Tests fonctionnent avec n'importe quel autre role mis à part MEDECIN, qui lui a un/des tests propres)
//-----------------------------------------------------------------------------------------------------------------------------

//------------ 1- Tests jouerAction() avec l'action TraiterMaladie OK

    @Test
    void jouerTourActionTraiterMaladieNonTraiteOK(){
        // pour simplifier le test, on choisit la ville qui se propage plutôt que de tester la propagation random
        Assertions.assertDoesNotThrow(() -> this.pionJoueur.getPlateau().propagationMaladie(atlanta, 1));
        Virus virusBleu = plateau.getLesVirus().get("BLEU");
        IAction traiter = new TraiterMaladie(virusBleu);
        pionJoueur.setVilleActuelle(atlanta);
        Assertions.assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,traiter));
    }

//------------ 2- Tests jouerAction() avec l'action TraiterMaladie OK (test spécifique au Role : "MEDECIN")

    @Test
    void jouerTourActionTraiterAvecMedecinMaladieNonTraiteOK() throws VilleIntrouvableException {
        Assertions.assertDoesNotThrow(() -> this.pionJoueur.getPlateau().propagationMaladie(atlanta, 1));
        Virus virusBleu = plateau.getLesVirus().get("BLEU");
        IAction traiter = new TraiterMaladie(virusBleu);
        PionJoueur pionJoueur3 = new PionJoueur(instance.partie);
        pionJoueur3.setRoleJoueur(new CarteMedecin(CouleurPionsRole.ORANGE));
        pionJoueur3.setVilleActuelle(atlanta);
        Assertions.assertDoesNotThrow(() -> instance.jouerAction(pionJoueur3, traiter));
        Assertions.assertEquals(0, instance.partie.getPlateau().getVilleByName(atlanta.getNomVille()).getNbCubeVirusVille().get(virusBleu));
    }

//------------ 4- Tests jouerAction() avec l'action TraiterMaladie KO

    //TODO

//------------ 3- Tests jouerAction() avec l'action TraiterMaladie KO (tests spécifiques au Role : "MEDECIN")

    //TODO

//=============================================================================================================================
//                                          ACTION DonnerConnaissance (PartagerConnaissance)
//=============================================================================================================================

//-----------------------------------------------------------------------------------------------------------------------------
// - Les roles sont distribués aléatoirement entre les joueurs en début de partie
// - Etant une action qui s'execute differemment si le role du joueur est : "CHERCHEUSE"
// Alors, pour effectuer certains tests on est contraint de set un role autre que "CHERCHEUSE"
// (Tests fonctionnent avec n'importe quel autre role mis à part CHERCHEUSE, qui lui a un/des tests propres)
//-----------------------------------------------------------------------------------------------------------------------------

//------------ 1- Tests jouerAction() avec l'action DonnerConnaissance OK

    @Test
    void donnerConnaissanceOk(){
        // pionJoueur : Joueur qui va donner
        // pionJoueur2 : Joueur qui va prendre
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.setRoleJoueur(new CarteMedecin(CouleurPionsRole.ORANGE));
        IAction donnerConaissance = new DonnerConnaissance(pionJoueur2);
        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,donnerConaissance));
        assertFalse(pionJoueur.isVilleOfCarteVilleDeckJoueur(atlanta));
        assertTrue(pionJoueur2.isVilleOfCarteVilleDeckJoueur(atlanta));
    }

//------------ 2- Tests jouerAction() avec l'action DonnerConnaissance OK (test spécifique au Role : "CHERCHEUSE")

    @Test
    void donnerConnaissanceChercheuseOk(){
        // pionJoueur : Role: CHERCHEUSE : Joueur qui va donner
        // pionJoueur2 : Joueur qui va prendre
        pionJoueur.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new DonnerConnaissance(pionJoueur2, chicago);
        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,action));
        assertFalse(pionJoueur.isVilleOfCarteVilleDeckJoueur(chicago));
        assertTrue(pionJoueur2.isVilleOfCarteVilleDeckJoueur(chicago));
    }

//------------ 3- Tests jouerAction() avec l'action DonnerConnaissance KO

    @Test
    void donnerConnaissanceNbActionsMaxTourAtteint(){
        pionJoueur2.setVilleActuelle(atlanta);
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action1 = new DeplacementNavette(alger);
        assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action1));
        IAction action2 = new DeplacementNavette(atlanta);
        assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action2));
        IAction action3 = new DeplacementNavette(alger);
        assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action3));
        IAction action4 = new DeplacementNavette(atlanta);
        assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action4));
        IAction action5 = new DonnerConnaissance(pionJoueur2);
        assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.jouerAction(pionJoueur,action5));
    }

    @Test
    void donnerConnaissanceJoueursNonPresentMemeVille(){
        pionJoueur2.setVilleActuelle(chicago);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DonnerConnaissance(pionJoueur2);
        assertThrows(JoueursNonPresentMemeVilleException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void donnerConnaissanceCarteVilleInexistanteDansDeckJoueur(){
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur2.setRoleJoueur(new CarteMedecin(CouleurPionsRole.ORANGE));
        IAction action = new DonnerConnaissance(pionJoueur2,atlanta);
        assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

//------------ 4- Tests jouerAction() avec l'action DonnerConnaissance KO (tests spécifiques au Role : "CHERCHEUSE")

    @Test
    void donnerConnaissanceChercheuseDonneeManquante(){
        pionJoueur.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new DonnerConnaissance(pionJoueur2);
        assertThrows(DonneeManquanteException.class,
                () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void donnerConnaissanceChercheuseCarteVilleInexistanteDansDeckJoueur(){
        pionJoueur.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DonnerConnaissance(pionJoueur2, chicago);
        assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur, action));
    }

//=============================================================================================================================
//                                          ACTION PrendreConnaissance (PartagerConnaissance)
//=============================================================================================================================

//-----------------------------------------------------------------------------------------------------------------------------
// - Les roles sont distribués aléatoirement entre les joueurs en début de partie
// - Etant une action qui s'execute differemment si le role du joueur est : "CHERCHEUSE"
// Alors, pour effectuer certains tests on est contraint de set un role autre que "CHERCHEUSE"
// (Tests fonctionnent avec n'importe quel autre role mis à part CHERCHEUSE, qui lui a un/des tests propres)
//-----------------------------------------------------------------------------------------------------------------------------

//------------ 1- Tests jouerAction() avec l'action PrendreConnaissance OK

    @Test
    void prendreConnaissanceOk(){
        // pionJoueur : Joueur qui va prendre
        // pionJoueur2 : Joueur qui va donner
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur2.setRoleJoueur(new CarteMedecin(CouleurPionsRole.ORANGE));
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur2.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction prendreConnaissance = new PrendreConnaissance(pionJoueur2);
        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,prendreConnaissance));
        assertFalse(pionJoueur2.isVilleOfCarteVilleDeckJoueur(atlanta));
        assertTrue(pionJoueur.isVilleOfCarteVilleDeckJoueur(atlanta));
    }

//------------ 2- Tests jouerAction() avec l'action PrendreConnaissance OK (test spécifique au Role : "CHERCHEUSE")

    @Test
    void prendreConnaissanceChercheuseOk(){
        // pionJoueur : Joueur qui va prendre
        // pionJoueur2 : Role CHERCHEUSE : Joueur qui va donner
        pionJoueur2.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur2.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction prendreConnaissance = new PrendreConnaissance(pionJoueur2, chicago);
        assertDoesNotThrow(() -> instance.jouerAction(pionJoueur,prendreConnaissance));
        assertTrue(pionJoueur.isVilleOfCarteVilleDeckJoueur(chicago));
        assertFalse(pionJoueur2.isVilleOfCarteVilleDeckJoueur(chicago));
    }

//------------ 3- Tests jouerAction() avec l'action PrendreConnaissance KO

    @Test
    void prendreConnaissanceNbActionsMaxTourAtteint(){
        // pionJoueur : Joueur qui va prendre
        // pionJoueur2 : Joueur qui va donner
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur2.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        atlanta.setStationDeRechercheVille(true);
        alger.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action1 = new DeplacementNavette(alger);
        assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action1));
        IAction action2 = new DeplacementNavette(atlanta);
        assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action2));
        IAction action3 = new DeplacementNavette(alger);
        assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action3));
        IAction action4 = new DeplacementNavette(atlanta);
        assertDoesNotThrow(() -> this.instance.jouerAction(pionJoueur,action4));
        IAction action5 = new PrendreConnaissance(pionJoueur2);
        assertThrows(NbActionsMaxTourAtteintException.class,
                () -> this.instance.jouerAction(pionJoueur,action5));
    }

    @Test
    void prendreConnaissanceJoueursNonPresentMemeVille(){
        // pionJoueur : Joueur qui va prendre
        // pionJoueur2 : Joueur qui va donner
        pionJoueur2.setVilleActuelle(chicago);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new PrendreConnaissance(pionJoueur2);
        Assertions.assertThrows(JoueursNonPresentMemeVilleException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

    @Test
    void prendreConnaissanceCarteVilleInexistanteDansDeckJoueur(){
        // pionJoueur : Joueur qui va prendre
        // pionJoueur2 : Joueur qui va donner
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur2.setRoleJoueur(new CarteMedecin(CouleurPionsRole.ORANGE));
        IAction action = new PrendreConnaissance(pionJoueur2);
        assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur,action));
    }

//------------ 4- Tests jouerAction() avec l'action PrendreConnaissance KO (tests spécifiques au Role : "CHERCHEUSE")

    @Test
    void prendreConnaissanceChercheuseDonneeManquante(){
        // pionJoueur : Joueur qui va prendre
        // pionJoueur2 : Role CHERCHEUSE : Joueur qui va donner
        pionJoueur2.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur2.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new PrendreConnaissance(pionJoueur2,null);
        assertThrows(DonneeManquanteException.class,
                () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void prendreConnaissanceChercheuseCarteVilleInexistanteDansDeckJoueur(){
        // pionJoueur : Joueur qui va prendre
        // pionJoueur2 : Role CHERCHEUSE : Joueur qui va donner
        pionJoueur2.setRoleJoueur(new CarteChercheuse(CouleurPionsRole.MARRON));
        pionJoueur2.setVilleActuelle(atlanta);
        pionJoueur2.ajouterCarteVilleDeckJoueur(new CarteVille(chicago));
        IAction action = new PrendreConnaissance(pionJoueur2, atlanta);
        assertThrows(CarteVilleInexistanteDansDeckJoueurException.class,
                () -> this.instance.jouerAction(pionJoueur, action));
    }

//=============================================================================================================================
//                                                 ACTION DecouvrirRemede
//=============================================================================================================================

//-----------------------------------------------------------------------------------------------------------------------------
// - Les roles sont distribués aléatoirement entre les joueurs en début de partie
// Alors, pour effectuer certains tests, on devra préciser lorsqu'il s'agit du role "SCIENTIFIQUE"
//-----------------------------------------------------------------------------------------------------------------------------

//------------ 1- Tests jouerAction() avec l'action DecouvrirRemede OK

    @Test
    void jouerTourActionDecouvrirRemedeOK() throws VilleIntrouvableException {
        atlanta.setStationDeRechercheVille(true);
        // pour le test on clear le deck actuel qui contient de base 2cartes distribués
        // pour ajouter 5 cartes de la même couleur
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

//------------ 2- Tests jouerAction() avec l'action DecouvrirRemede OK (test spécifique au Role : "SCIENTIFIQUE")

    @Test
    void jouerTourActionDecouvrirRemedeScientifiqueOK() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        // pour le test on clear le deck actuel qui contient de base 2cartes distribués
        // pour ajouter 5 cartes de la même couleur
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

//------------ 3- Tests jouerAction() avec l'action DecouvrirRemede KO

    @Test
    void jouerTourActionDecouvrirRemedeVilleAvecAucuneStationDeRecherche() throws VilleIntrouvableException {
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
    void jouerTourActionDecouvrirRemedeNombreDeCartesVilleDansDeckJoueurInvalide() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DecouvrirRemede();
        Assertions.assertThrows(NombreDeCartesVilleDansDeckJoueurInvalideException.class, () -> this.instance.jouerAction(pionJoueur, action));
    }

    @Test
    void jouerTourActionDecouvrirRemedeNbActionsMaxTourAtteint() throws VilleIntrouvableException {
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
    void jouerTourActionDecouvrirRemedeVirusDejaTraite() throws VilleIntrouvableException {
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

//------------ 4- Tests jouerAction() avec l'action DecouvrirRemede KO (tests spécifiques au Role : "SCIENTIFIQUE")

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
    void jouerTourActionDecouvrirRemedeScientifiqueNombreDeCartesVilleDansDeckJoueurInvalide() {
        atlanta.setStationDeRechercheVille(true);
        pionJoueur.setRoleJoueur(new CarteScientifique(CouleurPionsRole.BLANC));
        pionJoueur.ajouterCarteVilleDeckJoueur(new CarteVille(atlanta));
        IAction action = new DecouvrirRemede();
        Assertions.assertThrows(NombreDeCartesVilleDansDeckJoueurInvalideException.class, () -> this.instance.jouerAction(pionJoueur, action));
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
//                                                 ROLE EXPERT_AUX_OPERATIONS
//=============================================================================================================================

 /*
 Verif que si le role du joueur est expert aux opérations, l'action construire une station de recherche dans la ville que l'on occupe
 ne defausse aucune carte
  */

    @Test
    void construireStationRechercheExpertOperationOk(){
        pionJoueur.setRoleJoueur(new CarteExpertAuxOperations(CouleurPionsRole.VERT_CLAIR));
        IAction action = new ConstruireUneStation();
        pionJoueur.setVilleActuelle(chicago);
        CarteVille carteChicago = new CarteVille(chicago);
        pionJoueur.getDeckJoueur().add(carteChicago);
        Assertions.assertDoesNotThrow(()-> instance.jouerAction(pionJoueur,action));
        assertEquals(pionJoueur.getDeckJoueur().get(0),carteChicago);
    }


//=============================================================================================================================
//                                                 ROLE PLANIFICATEUR D'URGENCE
//=============================================================================================================================

    @Test
    void planificateurUrgenceOk(){
        CarteEvenement carteEvenement = new CartePontAerien();
        pionJoueur.getPlateau().getDefausseCarteJoueur().add(carteEvenement);
        pionJoueur.setRoleJoueur(new CartePlanificateurDurgence(CouleurPionsRole.BLEU));
        IAction action = new EntreposerEvenementRolePlanificateur(carteEvenement);
        assertDoesNotThrow(()-> instance.jouerAction(pionJoueur,action));
        assertEquals(carteEvenement,pionJoueur.getCartePlanificateurUrgenceEntrepose());
    }

//=============================================================================================================================
//                                                 ROLE SPECIALISTE_MISE_EN_QUARANTAINE
//=============================================================================================================================

    @Test
    void miseEnQuarantaineVille(){
        pionJoueur.setRoleJoueur(new CarteSpecialisteEnMiseEnQuarantaine(CouleurPionsRole.VERT_FONCE));
        IAction actionDeplacement = new DeplacementVoiture(chicago);
        assertDoesNotThrow(()->instance.jouerAction(pionJoueur,actionDeplacement));
        assertThrows((PropagationImpossibleCarSpecialisteQuarantaineException.class),() -> pionJoueur.getPlateau().propagationMaladie(pionJoueur.getVilleActuelle(),2));
    }

//=============================================================================================================================
//                                                 ROLE REPARTITEUR
//=============================================================================================================================

    @Test
    void repartiteurOk() throws VilleIntrouvableException {
        pionJoueur.setRoleJoueur(new CarteRepartiteur(CouleurPionsRole.ROSE));
        PionJoueur pionJoueur3 = new PionJoueur(instance.partie);
        pionJoueur2.setAutorisationDeplacementRepartiteur(true);
        pionJoueur3.setVilleActuelle(alger);
        instance.partie.getJoueurs().add(pionJoueur3);
        assertDoesNotThrow(()->instance.repartiteurDeplacementPion(pionJoueur,pionJoueur2,alger));
    }

//=============================================================================================================================
//                                                 AUTRES TESTS
//=============================================================================================================================

    @Test
    void jouerTourOk() throws VilleIntrouvableException {
        PionJoueur joueurActuel = instance.partie.getJoueurActuel();
        joueurActuel.setRoleJoueur(new CarteRepartiteur(CouleurPionsRole.ROSE));
        IAction action = new DeplacementVoiture(plateau.getVilleByName("Washington"));
        IAction action1 = new DeplacementVoiture(atlanta);
        IAction action2 = new DeplacementVoiture(chicago);
        IAction action3 = new DeplacementVoiture(atlanta);
        List<IAction> listeActions = List.of(action,action1,action2,action3);
        assertDoesNotThrow(()-> instance.jouerTour(listeActions));
        // verif que c'est bien au joueur suivant après ce tour
        assertNotEquals(instance.partie.getJoueurActuel(),joueurActuel);
    }

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
    void jouerCarteEventParUneNuitTranquille() {
        CarteEvenement carteEvenementNuitTranquille = new CarteParUneNuitTranquille();
        pionJoueur.getDeckJoueur().add(carteEvenementNuitTranquille);

        assertDoesNotThrow(() -> instance.jouerEvent(pionJoueur, carteEvenementNuitTranquille));
        assertThrows(NuitTranquilleException.class, () -> instance.propagation(pionJoueur));
        assertTrue(pionJoueur.getPlateau().isEffetParUneNuitTranquilleActif());
    }

    @Test
    void jouerCarteEventPopulationResiliente() {
        CartePropagation cartePropagation = new CartePropagation(atlanta);
        pionJoueur.getPlateau().getDefausseCartePropagation().add(cartePropagation);
        CartePopulationResiliente carteEvenementPopulationResiliente = new CartePopulationResiliente();
        pionJoueur.getDeckJoueur().add(carteEvenementPopulationResiliente);
        carteEvenementPopulationResiliente.setCartePropagationChoisis(cartePropagation);
        assertDoesNotThrow(() -> instance.jouerEvent(pionJoueur, carteEvenementPopulationResiliente));
    }

    @Test
    void jouerCarteEventPontAerien() {
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
        Plateau plateauTest = plateau;
        pionJoueur.setPlateau(plateauTest);
        CarteSubventionPublique carteSubventionPublique = new CarteSubventionPublique();
        carteSubventionPublique.placerStationRecherche(pionJoueur, "Atlanta");
        pionJoueur.getDeckJoueur().add(carteSubventionPublique);
        assertDoesNotThrow(()-> instance.jouerEvent(pionJoueur, carteSubventionPublique));
    }

//=================================================================================================================================================================================
//                                                 EFFET CARTE EPIDEMIE
//=================================================================================================================================================================================

    @Test
    void applicationEffetAcceleration(){
        Plateau plateauTest = plateau;
        assertTrue(plateauTest.getMarqueurVitessePropagation() >= 0,"Le marqueur vitesse de propagation ne peut pas être négatif");
        assertTrue(plateauTest.getMarqueurVitessePropagation() < DonneesVariablesStatiques.tabMarqueurVitesseDePropagation.length,
                "Marqueur vitesse de propagation ne peut pas être supérieur à la taille du tableau marqueur vitesse de propagation");
    }

    @Test
    void applicationEffetIntensification(){

    }

    @Test
    void applicationEffetInfection(){

    }

}