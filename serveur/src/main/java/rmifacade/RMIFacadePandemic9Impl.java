package rmifacade;

import modele.facade.FacadePandemic9;
import modele.facade.FacadePandemic9Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    public void accederUnePartie(String idPartie, String pseudo) throws RemoteException, partieDejaTermineException, partieInexistantException, PartiePleinExecption {
        this.facadePandemic9.accederUnePartie(idPartie,pseudo);
    }

    @Override
    public void deplacementCarte(String idPartie, String pseudo, Carte carte, List<Carte> cartes, ModeDeplacement modeDeplacement) throws RemoteException, CarteInexistantException, CarteDejaException, PartieTermineException, PartieSuspenduException, RessourcesInsuffisantesException, CarteDejaPossederException {
        this.facadePandemic9.deplacementCarte(idPartie, pseudo, carte, cartes, modeDeplacement);
    }

    @Override
    public Collection<Carte> getLesCartesSurPlateau(String idPartie, String pseudo) throws RemoteException {
        return this.facadePandemic9.getLesCartesSurPlateau(idPartie, pseudo);
    }

    @Override
    public Collection<Carte> getLesCartesPropagationDefausses(String idPartie) throws RemoteException {
        return this.facadePandemic9.getLesCartesPropagationDefausses(idPartie);
    }

    @Override
    public void distribution(String idPartie) throws RemoteException {
        this.facadePandemic9.distribution(idPartie);
    }

    @Override
    public Boolean partieCommence(String idPartie) throws RemoteException {
        return this.facadePandemic9.partieCommence(idPartie);
    }

    @Override
    public synchronized void notification(String idPartie) throws RemoteException {
        this.facadePandemic9.notification(idPartie);
    }

    @Override
    public void inscription(String pseudo, String mdp) throws RemoteException{
        this.facadePandemic9.inscription(pseudo,mdp);
    }

    @Override
    public boolean connexion(String pseudo, String mdp)throws RemoteException{
        return this.facadePandemic9.connexion(pseudo,mdp);
    }

    @Override
    public void setNouvellePartie(String pseudo, String ticket) throws RemoteException, PartiePleinExecption {
        this.facadePandemic9.setNouvellePartie(pseudo, ticket);
    }

    @Override
    public boolean quitter(String idPartie, String pseudo)throws RemoteException {
        return this.facadePandemic9.quitter(idPartie, pseudo);
    }

    @Override
    public boolean peutQuitter(String idPartie) throws RemoteException{
        return this.facadePandemic9.peutQuitter(idPartie);
    }

    @Override
    public Collection<PartieDTO> getLesParties() throws  RemoteException{
        Collection<PartieDTO> partieDTOCollection = new ArrayList<PartieDTO>();
        for (Partie partie: this.facadePandemic9.getLesParties()){
            PartieDTO partieDTO = new PartieDTO(partie.getId(), partie.getEtatPartie().toString(), partie.getDateCreation());
            for (PartieJoueur partieJoueur : partie.getPartieJoueurs()){
                partieDTO.ajouterNomJoueur(partieJoueur.getJoueur());
            }
            partieDTOCollection.add(partieDTO);
        }
        return partieDTOCollection;
    }

    @Override
    public boolean joueurCreateurDeLaPartie(String idPartie, String pseudo) throws RemoteException {
        return this.facadePandemic9.joueurCreateurDeLaPartie(idPartie,pseudo);
    }

    @Override
    public String getPlateauDuJoueur(String idPartie, String pseudo) throws RemoteException {
        return this.facadePandemic9.getPlateauDuJoueur(idPartie,pseudo);
    }

    @Override
    public String getEtatPartie(String idPartie) throws RemoteException {
        return this.facadePandemic9.getEtatPartie(idPartie);
    }

    @Override
    public String getVainqueur(String idPartie) throws RemoteException {
        return this.facadePandemic9.getVainqueur(idPartie);
    }
}
