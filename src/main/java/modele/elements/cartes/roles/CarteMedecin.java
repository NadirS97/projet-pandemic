package modele.elements.cartes.roles;

import modele.elements.PionJoueur;
import modele.elements.Virus;
import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.EtatVirus;
import modele.elements.enums.NomsRoles;

import java.util.HashMap;
import java.util.List;

public class CarteMedecin extends CarteRole  {

    public CarteMedecin(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.MEDECIN);
        super.setDescriptionRole(
                "Retirez tous les cubes d'une couleur lorsque vous traitez une maladie. Dans la " +
                        "ville où vous êtes retirez automatiquement tous les cubes de la couleur d'une maladie guérie " +
                        "(et empêchez d'autres cubes d'une maladie guérie d'y être placés).");
    }

    public void effetCarteMedecin(PionJoueur pionJoueur) throws Exception {
        List<Virus> virusDejaGueris = pionJoueur.getPartie().getPlateau()
                .getLesVirus()
                .values()
                .stream()
                .filter(virus -> virus.getEtatVirus().equals(EtatVirus.TRAITE))
                .toList();

        virusDejaGueris.forEach(pionJoueur.getPartie()
                .getJoueurs()
                .stream()
                .filter(joueur -> joueur.getRoleJoueur().getNomRole().equals(NomsRoles.MEDECIN))
                .findFirst()
                .orElseThrow()
                .getVilleActuelle()
                .getNbCubeVirusVille()
                .keySet()::remove);

        virusDejaGueris.forEach(v -> {
            HashMap<String, Virus> listeVaccinationContreVirus = null;
            listeVaccinationContreVirus = pionJoueur.getPartie().getJoueurs()
                    .stream()
                    .filter(joueur -> joueur.getRoleJoueur().getNomRole().equals(NomsRoles.MEDECIN))
                    .findFirst()
                    .orElseThrow()
                    .getVilleActuelle()
                    .getListeVaccinationContreVirus();

            if (!listeVaccinationContreVirus.containsKey(v.getVirusCouleur()))
                listeVaccinationContreVirus.put(v.getVirusCouleur(), v);
        });
    }

    @Override
    public void execEffet(PionJoueur pionJoueur) throws Exception {

    }
}
