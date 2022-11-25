package modele.elements.actions.traiter_maladie;

import lombok.AllArgsConstructor;
import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.Virus;
import modele.elements.actions.IAction;
import modele.elements.enums.EtatVirus;
import modele.elements.enums.NomsRoles;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VirusDejaEradiqueException;
import modele.exceptions.VirusInexistantDansLaVilleActuelException;
import modele.utils.DonneesVariablesStatiques;

@AllArgsConstructor

public class TraiterMaladie implements IAction {

    private Virus choixVirus;

    /**
     * Fonction permettant d'executer l'action TraiterMaladie :
     * il peut traiter une maladie en retirant un cube si la maladie n’a pas encore été traitée ou alors
     * tous les cubes d’une couleur d’une maladie traitée. Si le dernier cube d’une couleur a été
     * supprimé et que la maladie concernée a été traitée, la maladie est alors éradiquée.
     * @param pionJoueur
     * @throws NbActionsMaxTourAtteintException
     * @throws VirusDejaEradiqueException
     */
    @Override
    public void execAction(PionJoueur pionJoueur) throws NbActionsMaxTourAtteintException, VirusDejaEradiqueException {

        // Verif nb actions
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        // Le joueur choisis un virus a traite parmis les 3 potentiel present dans la ville
        int nbCubesVirusVilleActuel = pionJoueur.getVilleActuelle().getNbCubeVirusVille().get(choixVirus);
        // retirer du sac les cubes pour les 2 cas
        // Cas maladie non traite : retire 1 cube
        if (choixVirus.getEtatVirus().equals(EtatVirus.ERADIQUE))
            throw new VirusDejaEradiqueException();
        if (choixVirus.getEtatVirus().equals(EtatVirus.NON_TRAITE)) {
            if (pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.MEDECIN)) {
                int nbCubesPresentVille = pionJoueur.getVilleActuelle().getNbCubeVirusVille().get(choixVirus);
                pionJoueur.getVilleActuelle().getNbCubeVirusVille().put(choixVirus, 0);
                choixVirus.rajouterCubesSac(nbCubesPresentVille);
            }
            else {
                pionJoueur.getVilleActuelle().getNbCubeVirusVille().put(choixVirus, nbCubesVirusVilleActuel - 1);
                choixVirus.rajouterCubesSac(1);
            }
        }
        //  cas maladie traite : retire tout cube
        //  cas maladie traite et dernier cube d'une couleur suppr, il faut check si parmis toutes les villes il n'y a plus aucun virus de cette couleur
        if (choixVirus.getEtatVirus().equals(EtatVirus.TRAITE)) {
            int nbCubesPresentVille = pionJoueur.getVilleActuelle().getNbCubeVirusVille().get(choixVirus);
            pionJoueur.getVilleActuelle().getNbCubeVirusVille().put(choixVirus, 0);
            choixVirus.rajouterCubesSac(nbCubesPresentVille);

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
