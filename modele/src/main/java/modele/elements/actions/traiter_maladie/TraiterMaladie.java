package modele.elements.actions.traiter_maladie;

import lombok.AllArgsConstructor;
import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.Virus;
import modele.elements.actions.IAction;
import modele.elements.enums.EtatVirus;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VirusDejaEradiqueException;
import modele.exceptions.VirusInexistantDansLaVilleActuelException;
import modele.utils.DonneesVariablesStatiques;

@AllArgsConstructor

public class TraiterMaladie implements IAction {

    private Virus choixVirus;

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {

        // Verif nb actions
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        // Le joueur choisis un virus a traite parmis les 3 potentiel present dans la ville
        if (!pionJoueur.getVilleActuelle().getNbCubeVirusVille().keySet().contains(choixVirus))
            throw new VirusInexistantDansLaVilleActuelException("Le virus dans la ville actuelle:" + choixVirus.getVirusCouleur() + "n'a pas été retrouvé");
        int nbCubesVirusVilleActuel = pionJoueur.getVilleActuelle().getNbCubeVirusVille().get(choixVirus);
        // retirer du sac les cubes pour les 2 cas
        // Cas maladie non traite : retire 1 cube
        if (choixVirus.getEtatVirus().equals(EtatVirus.ERADIQUE))
            throw new VirusDejaEradiqueException();
        if (choixVirus.getEtatVirus().equals(EtatVirus.NON_TRAITE)) {
            pionJoueur.getVilleActuelle().getNbCubeVirusVille().put(choixVirus,
                    nbCubesVirusVilleActuel-1);


        }
        //  cas maladie traite : retire tout cube
        //  cas maladie traite et dernier cube d'une couleur suppr, il faut check si parmis toutes les villes il n'ya plus aucun virus de cette couleur
        if (choixVirus.getEtatVirus().equals(EtatVirus.TRAITE)) {
            pionJoueur.getVilleActuelle().getNbCubeVirusVille().put(choixVirus, 0);

            boolean eradique = true;
            // si on trouve une ville dans le plateau avec le virus choisis qui a toujours des cubes, alors la maladie n'est pas eradiqué
            for (Ville ville : pionJoueur.getPlateau().getVilles().values()) {
                if (!ville.getNbCubeVirusVille().get(choixVirus).equals(0))
                    eradique = false;
            }
            // si virus eradique, changer l'etat dans le plateau la liste des virus
            pionJoueur.getPlateau().getLesVirus().get(choixVirus.getVirusCouleur()).setEtatVirus(EtatVirus.ERADIQUE);

        }


    }
}
