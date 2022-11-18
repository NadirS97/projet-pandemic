package modele.facade;

import modele.elements.Virus;
import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.Partie;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.enums.EtatVirus;
import modele.elements.enums.NomsRoles;
import modele.exceptions.*;
import modele.elements.PionJoueur;

import java.util.HashMap;
import java.util.List;

public class FacadePandemic9Impl implements FacadePandemic9 {

    Partie partie;

    public FacadePandemic9Impl(int nbJoueurs) throws Exception {
        partie = new Partie(nbJoueurs);
    }



    @Override
    public void jouerTour(List<IAction> listeAction) throws Exception, TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException {
        for (IAction action : listeAction){
            jouerAction(partie.getJoueurActuel(),action);
        }
            piocherCartes(partie.getJoueurActuel());
            propagation(partie.getJoueurActuel());

            effetCarteMedecin();

            partie.joueurSuivant();

           partie.isVictoire();
    }



    @Override
    public void jouerAction(PionJoueur joueurActuel, IAction action) throws Exception {
        joueurActuel.setAction(action);
        joueurActuel.executerAction();
    }

    @Override
    public void jouerEvent(PionJoueur joueur, CarteEvenement carteEvenement) throws Exception {

        joueur.jouerCarteEvenement(carteEvenement);
    }

    @Override
    public void piocherCartes(PionJoueur joueurActuel) throws TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException {

            joueurActuel.piocherCartes();

    }

    public void defausserCartesJoueur(PionJoueur joueurActuel, List<CarteJoueur> cartesJoueursDefausser) throws CarteJoueurInexistanteDansDeckException {
        joueurActuel.defausserListeCarteJoueurEnTrop(cartesJoueursDefausser);
    }

    @Override
    public void propagation(PionJoueur joueurActuel) throws VilleDejaEclosException, NuitTranquilleException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException {
        joueurActuel.getPlateau().initialiserPropagation();
    }


    @Override
    public void repartiteurActionDeplacementAutrePion(PionJoueur joueurActuel, PionJoueur joueurCible, IAction action) throws Exception {
        joueurActuel.setAction(action);
        joueurActuel.repartiteurActionDeplacementAutrePion(joueurCible);
    }

    public void repartiteurDeplacementPion(PionJoueur joueurActuel,PionJoueur joueurCible, Ville villeDestination) throws AucunJoueurDansVilleDestinationException, AutorisationManquanteException {
        joueurActuel.repartiteurDeplacementPion(joueurCible,villeDestination);
    }
    public void effetCarteMedecin(){
        List<Virus> virusDejaGueris = partie.getPlateau()
                .getLesVirus()
                .values()
                .stream()
                .filter(virus -> virus.getEtatVirus().equals(EtatVirus.TRAITE))
                .toList();

        virusDejaGueris.forEach(partie
                .getJoueurs()
                .stream()
                .filter(pionJoueur -> pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.MEDECIN))
                .findFirst()
                .orElseThrow()
                .getVilleActuelle()
                .getNbCubeVirusVille()
                .keySet()::remove);

        virusDejaGueris.forEach(v -> {
            HashMap<String, Virus> listeVaccinationContreVirus = null;
            listeVaccinationContreVirus = partie.getJoueurs()
                    .stream()
                    .filter(pionJoueur -> pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.MEDECIN))
                    .findFirst()
                    .orElseThrow()
                    .getVilleActuelle()
                    .getListeVaccinationContreVirus();

            if (!listeVaccinationContreVirus.containsKey(v.getVirusCouleur()))
                listeVaccinationContreVirus.put(v.getVirusCouleur(), v);
        });
    }
}