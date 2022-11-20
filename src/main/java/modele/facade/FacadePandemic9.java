package modele.facade;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.exceptions.*;
import modele.elements.PionJoueur;

import java.io.FileNotFoundException;
import java.util.List;

public interface FacadePandemic9 {

    /**
     * Trois fonctions de créations de parties pour 2,3 ou 4 joueurs qui initialisent chacune une partie, comprenant un plateau initialisé
     * avec tous les éléments du plateau généré lors de la création d'un nouvel objet plateau, une liste de 2,3 ou 4joueurs, les premières cartes
     * joueurs distribués à chaque joueur de la partie, un attribut joueurCourant dont le premier est determiné selon la carte ville possèdant la plus forte population.
     * @throws RoleIntrouvableException
     * @throws VilleIntrouvableException
     * @throws EvenementInnexistantException
     * @throws VirusIntrouvableException
     * @throws FileNotFoundException
     */
    void creerPartieDeuxJoueurs() throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException;

    void creerPartieTroisJoueurs() throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException;

    void creerPartieQuatreJoueurs() throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException;


    /**
     *
     * @param listeAction
     * La fonction jouerTour va prendre une liste d'actions, nous avons choisi d'implementer le design pattern strategy pour les actions des joueurs
     * un tour est divisé en trois phases, les 4 actions (ce pourquoi on choisit une liste d'actions dans cette fonction jouerTour) qui va pour chaque action
     * appeler la fonction jouerAction de cette même façade
     * Le joueur pioche ensuite 2 cartes
     * Puis la propagation des maladies se fait
     * Une fois les trois phases terminé, c'est au joueur suivant de jouer,
     * on vérifie si il y'a condition de victoire (cad si les 4 remèdes ont été découverts à la fin du tour)
     * CHAQUE exception des différentes actions disponibles seront catch dans le futur controleur et la vue réagira en fonction des différents cas
     */
    void jouerTour(List<IAction> listeAction) throws CarteVilleInexistanteDansDeckJoueurException, NombreDeCartesVilleDansDeckJoueurInvalideException, VirusDejaEradiqueException, VilleNonVoisineException, NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteEvenementNonTrouveDansDefausseException, JoueursNonPresentMemeVilleException, VirusDejaTraiteException, VilleIntrouvableException, VilleDestinationEstVilleActuelleException, MauvaisRoleException, VirusInexistantDansLaVilleActuelException, VilleAvecAucuneStationDeRechercheException, DonneeManquanteException, TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException, NuitTranquilleException, VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException;

    void jouerAction(PionJoueur joueurActuel, IAction action) throws CarteVilleInexistanteDansDeckJoueurException, NombreDeCartesVilleDansDeckJoueurInvalideException, VirusDejaEradiqueException, VilleNonVoisineException, NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteEvenementNonTrouveDansDefausseException, JoueursNonPresentMemeVilleException, VirusDejaTraiteException, VilleIntrouvableException, VilleDestinationEstVilleActuelleException, MauvaisRoleException, VirusInexistantDansLaVilleActuelException, VilleAvecAucuneStationDeRechercheException, DonneeManquanteException;

    /**
     *La fonction jouerEvent sera jouable à n'importe quel moment de la partie, même si ce n'est pas au tour du joueur qui la joue
     */
    void jouerEvent(PionJoueur joueur, CarteEvenement carteEvenement) throws VilleDejaEclosException, CarteEvenementNotFoundInDeckException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, PermissionNonAccordeException, CartePropagationNotInDefausseException;

    void piocherCartes(PionJoueur joueurActuel) throws TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException, VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException;

    void defausserCartesJoueur(PionJoueur joueurActuel, List<CarteJoueur> cartesJoueursDefausser) throws CarteJoueurInexistanteDansDeckException;

    void propagation(PionJoueur joueurActuel) throws VilleDejaEclosException, NuitTranquilleException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException;


    void repartiteurActionDeplacementAutrePion(PionJoueur joueurActuel, PionJoueur joueurCible, IAction action) throws AutorisationManquanteException, CarteVilleInexistanteDansDeckJoueurException, NombreDeCartesVilleDansDeckJoueurInvalideException, VirusDejaEradiqueException, VilleNonVoisineException, NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteEvenementNonTrouveDansDefausseException, JoueursNonPresentMemeVilleException, ActionNonDeplacementException, VirusDejaTraiteException, VilleIntrouvableException, VilleDestinationEstVilleActuelleException, MauvaisRoleException, VirusInexistantDansLaVilleActuelException, VilleAvecAucuneStationDeRechercheException, DonneeManquanteException;

    void repartiteurDeplacementPion(PionJoueur joueurActuel, PionJoueur joueurCible, Ville villeDestination) throws AucunJoueurDansVilleDestinationException, AutorisationManquanteException;





}
