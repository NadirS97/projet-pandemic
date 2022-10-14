package rmifacade;

import interfaces.exceptions.*;
import elements.dataTransfertObject.SalonDTO;
import elements.dataTransfertObject.SaveDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIFacadePandemic9 extends Remote  {

    /**
     * Méthode permettant à un client d'inscrire un nouveau compte utilisateur dans la BDD.
     * La connexion à ce nouveau compte se fait automatiquement après.
     * @param pseudo Pseudo du nouveau compte
     * @param mdp Mot de passe du compte
     * @return ID du compte utilisateur retouné au format String
     * @throws PseudoDejaExistantException Si le pseudo est déjà utilisé
     * @throws PseudoInvalideException Si le pseudo ne respect pas les règles de création d'un pseudo
     * @throws MdpInvalideException Si le mot de passe ne respect pas les règles de création d'un mot de passe
     * @throws RemoteException Si problème survient lié au RMI
     */
    String inscrireJoueur(String pseudo, String mdp) throws PseudoDejaExistantException, PseudoInvalideException, MdpInvalideException, RemoteException;

    /**
     * Méthode permettant à un client de se connecter à la façade avec un compte déjà enregistré.
     * @param pseudo Pseudo du compte
     * @param mdp Mot de passe du compte
     * @return ID du compte utilisateur retouné au format String
     * @throws PseudoInexistantException Si le pseudo pour authentifier le compte n'existe pas en BDD
     * @throws MdpIncorrectException Si le mot de passe ne correspond pas avec celui stocké en BDD
     * @throws DejaConnecteException Si le compte est déjà connecté à la façade
     * @throws RemoteException Si problème survient lié au RMI
     */
    String connexion(String pseudo, String mdp) throws PseudoInexistantException, MdpIncorrectException, DejaConnecteException, RemoteException;

    /**
     * Méthode permettant de déconnecter de la façade un compte utilisateur connecté
     * @param pseudo Pseudo du compte
     * @throws PasConnecteException Si le compte n'est pas présent dans la liste des utilisateurs connectés
     * @throws RemoteException Si problème survient lié au RMI
     */
    void deconnexion(String pseudo) throws PasConnecteException, RemoteException;

    /**
     * Méthode permettant à un utilisateur de créer une partie qui sera enregistrée dans la façade pour que les autres
     * participants puissent le rejoindre.
     * @param pseudo Pseudo du créateur de la partie
     * @return ID de la partie retouné au format String
     * @throws PseudoDejaEnPartieException Si l'utilisateur est déjà dans un salon pour une partie
     * @throws RemoteException Si problème survient lié au RMI
     * @throws PasConnecteException Si l'utilisateur n'est pas connecté à la façade
     */
    String creerSalonPartie(String pseudo) throws PseudoDejaEnPartieException, RemoteException, PasConnecteException;

    /**
     * Méthode permettant de sauvegarder une partie commencée et non terminée dans la BDD.
     * @param pseudo Pseudo du demandeur de la sauvegarde (créateur de la partie)
     * @throws PasCreateurException Si le pseudo n'est pas celui du créateur
     * @throws PartiePasCommenceeException Si la partie n'est pas une partie commencée ou existante dans la façade
     * @throws PasConnecteException Si l'utilisateur n'est pas connecté à la façade
     * @throws RemoteException Si problème survient lié au RMI
     */
    void sauvegarderPartie(String pseudo) throws PasCreateurException, PartiePasCommenceeException, PasConnecteException, RemoteException, PartieInexistanteException;

    /**
     * Méthode permettant à un joueur de rejoindre une partie en cours de formation à l'aide du code de la partie
     * @param pseudo Pseudo du joueur voulant rejoindre la partie
     * @param codePartie Code de la partie (ID)
     * @throws PartieInexistanteException Si la partie qui est demandée n'existe pas dans les parties de la façade
     * @throws PseudoDejaEnPartieException Si le joueur est déjà dans la partie qu'il essaye de rejoindre
     * @throws PasConnecteException Si l'utilisateur n'est pas connecté à la façade
     * @throws RemoteException Si problème survient lié au RMI
     * @throws AssezDeJoueursException Si la partie contient déjà le nombre maximum de joueur
     */
    void rejoindreSalonPartie(String pseudo, String codePartie) throws PartieInexistanteException, PseudoDejaEnPartieException, PasConnecteException, RemoteException, AssezDeJoueursException;

    /**
     * Méthode permettant de quitter le salon de la partie en formation
     * @param pseudo Pseudo du joueur voulant quitter la partie
     * @throws PseudoPartieInexistantException Si le joueur n'est pas associé avec la partie
     * @throws RemoteException Si problème survient lié au RMI
     * @throws PasConnecteException Si l'utilisateur n'est pas connecté à la façade
     */
    void partirSalonPartie(String pseudo) throws PseudoPartieInexistantException, RemoteException, PasConnecteException;

    /**
     * Méthode permettant au client de savoir si la partie a commencé.
     * @param pseudo Pseudo du créateur voulant lancer la partie
     * @throws RemoteException Si problème survient lié au RMI
     * @throws PasConnecteException Si l'utilisateur n'est pas connecté à la façade
     * @throws PasDansUnePartie Si l'utilisateur n'est pas dans une partie
     */
    Boolean partieCommencee(String pseudo) throws RemoteException, PasConnecteException, PasDansUnePartie;

    /**
     * Méthode permettant au client d'indiquer s'il est prêt à commencer la partie
     * @param pseudo Pseudo du joueur
     * @throws RemoteException Si problème survient lié au RMI
     * @throws PasConnecteException Si l'utilisateur n'est pas connecté à la façade
     * @throws PasDansUnePartie Si l'utilisateur n'est pas dans une partie
     */
    void notifierPret(String pseudo) throws RemoteException,PasConnecteException, PasDansUnePartie;

    /**
     * Méthode permettant au client récupérer la liste des pseudos des participants dans une partie
     * @param pseudo Pseudo du compte
     * @return Liste contenant les pseudos
     * @throws RemoteException Si problème survient lié au RMI
     * @throws PasConnecteException Si l'utilisateur n'est pas connecté à la façade
     * @throws PasDansUnePartie Si l'utilisateur n'est pas dans une partie
     */
    SalonDTO getParticipants(String pseudo) throws RemoteException,PasConnecteException, PasDansUnePartie;

    /**
     * Méthode permettant au client récupérer le code de la partie
     * @param pseudo Pseudo du compte
     * @return Le code d'accès à la partie
     * @throws RemoteException Si problème survient lié au RMI
     * @throws PasConnecteException Si l'utilisateur n'est pas connecté à la façade
     * @throws PasDansUnePartie Si l'utilisateur n'est pas dans une partie
     */
    String getCodePartie(String pseudo) throws RemoteException,PasConnecteException, PasDansUnePartie;

    /**
     * Méthode permettant au client récupérer la liste de parties auxquelles il a joué en fonction des membres de son salon
     * @param pseudo Pseudo du compte
     * @return La liste des sauvegardes
     * @throws RemoteException Si problème survient lié au RMI
     * @throws PasConnecteException Si l'utilisateur n'est pas connecté à la façade
     * @throws PasDansUnePartie Si l'utilisateur n'est pas dans une partie
     */
    List<SaveDTO> getSaves(String pseudo) throws RemoteException,PasConnecteException, PasDansUnePartie;

    /**
     * Méthode permettant au client créateur de la partie d'envoyer les informations permettant d'identifier la sauvegarde
     * à utiliser.
     * @param pseudo Pseudo du compte
     * @param id Id de la save à charger
     */
    void sendIdSave(String pseudo, String id) throws PasConnecteException, RemoteException, PasDansUnePartie;
}
