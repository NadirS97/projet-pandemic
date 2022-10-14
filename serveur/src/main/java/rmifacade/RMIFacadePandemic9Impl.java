package rmifacade;

import modele.facade.FacadePandemic9;
import modele.facade.FacadePandemic9Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIFacadePandemic9Impl extends UnicastRemoteObject implements RMIFacadePandemic9 {
    FacadePandemic9 facadePandemic9;

    protected RMIFacadePandemic9Impl() throws RemoteException {
        super();
        this.facadePandemic9 = new FacadePandemic9Impl();
    }

    public static RMIFacadePandemic9 getInstance() throws RemoteException{
        return new RMIFacadePandemic9Impl();
    }

    @Override
    public String inscrireJoueur(String pseudo, String mdp) throws PseudoDejaExistantException, PseudoInvalideException, MdpInvalideException, RemoteException {
        return this.facadePandemic9.inscrireJoueur(pseudo, mdp);
    }

    @Override
    public String connexion(String pseudo, String mdp) throws PseudoInexistantException, MdpIncorrectException, DejaConnecteException, RemoteException {
        return this.facadePandemic9.connexion(pseudo, mdp);
    }

    @Override
    public void deconnexion(String pseudo) throws PasConnecteException, RemoteException {
        this.facadePandemic9.deconnexion(pseudo);
    }

    @Override
    public String creerSalonPartie(String pseudo) throws PseudoDejaEnPartieException, RemoteException, PasConnecteException {
        return this.facadePandemic9.creerSalonPartie(pseudo);
    }

    @Override
    public void sauvegarderPartie(String pseudo) throws PasCreateurException, PartiePasCommenceeException, PasConnecteException, RemoteException, PartieInexistanteException {
        this.facadePandemic9.sauvegarderPartie(pseudo);
    }

    @Override
    public void rejoindreSalonPartie(String pseudo, String codePartie) throws PartieInexistanteException, PseudoDejaEnPartieException, PasConnecteException, RemoteException, AssezDeJoueursException {
        this.facadePandemic9.rejoindreSalonPartie(pseudo, codePartie);
    }

    @Override
    public void partirSalonPartie(String pseudo) throws PseudoPartieInexistantException, RemoteException, PasConnecteException {
        this.facadePandemic9.partirSalonPartie(pseudo);
    }

    @Override
    public Boolean partieCommencee(String pseudo) throws RemoteException, PasConnecteException, PasDansUnePartie {
        return this.facadePandemic9.partieCommencee(pseudo);
    }

    @Override
    public void notifierPret(String pseudo) throws RemoteException, PasConnecteException, PasDansUnePartie {
        this.facadePandemic9.notifierPret(pseudo);
    }

    @Override
    public SalonDTO getParticipants(String pseudo) throws RemoteException, PasConnecteException, PasDansUnePartie {
        return this.facadePandemic9.getParticipants(pseudo);
    }

    @Override
    public String getCodePartie(String pseudo) throws RemoteException, PasConnecteException, PasDansUnePartie {
        return this.facadePandemic9.getCodePartie(pseudo);
    }

    @Override
    public List<SaveDTO> getSaves(String pseudo) throws RemoteException, PasConnecteException, PasDansUnePartie {
        return this.facadePandemic9.getSaves(pseudo);
    }

    @Override
    public void sendIdSave(String pseudo, String id) throws PasConnecteException, RemoteException, PasDansUnePartie {
        this.facadePandemic9.sendIdSave(pseudo, id);
    }
}
