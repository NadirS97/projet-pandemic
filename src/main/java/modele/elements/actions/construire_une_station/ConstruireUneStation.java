package modele.elements.actions.construire_une_station;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.PionJoueur;
import modele.elements.Plateau;
import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteJoueur;
import modele.elements.enums.NomsRoles;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleActuellePossedeDejaUneStationDeRechercheException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.utils.DonneesVariablesStatiques;

import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
public class ConstruireUneStation implements IAction, Serializable {

    private Ville villeStationDeRecherche;

    public ConstruireUneStation(Ville villeStationDeRecherche) {
        this.villeStationDeRecherche = villeStationDeRecherche;
    }

    /**
     * Fonction permettant d'executer l'action ConstruireUneStation :
     * Pour une action le joueur peut construire une station de recherche en défaussant la carte correspondant
     * à la ville où le joueur se situe. Une fois que 6 stations ont été construites, le joueur ne peut plus
     * en créer, mais il peut en déplacer une autre déjà existante sur sa case.
     * @param pionJoueur
     * @throws NbActionsMaxTourAtteintException
     * @throws VilleActuellePossedeDejaUneStationDeRechercheException
     * @throws CarteVilleInexistanteDansDeckJoueurException
     * @throws VilleAvecAucuneStationDeRechercheException
     */
    @Override
    public void execAction(PionJoueur pionJoueur) throws NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteVilleInexistanteDansDeckJoueurException, VilleAvecAucuneStationDeRechercheException {
        Ville villeActuelle = pionJoueur.getVilleActuelle();
        Plateau plateau = pionJoueur.getPlateau();
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        // construire une station de recherche quand la limite n'a pas été atteinte
        if (plateau.addNbStationsDeRechercheConstruites() < DonneesVariablesStatiques.nbStationsRechercheMaxAutorise) {
            if (plateau.isVilleStationDeRecherche(villeActuelle))
                throw new VilleActuellePossedeDejaUneStationDeRechercheException("Impossible de rajouter une station de recherche, la ville " + villeActuelle.getNomVille() + " possède déjà une station de recherche.");
            // SI JOUEUR = EXPERT AUX OPERATIONS, PAS DE DE DE DEFAUSSE NECESSAIRE
            if (pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.EXPERT_AUX_OPERATIONS)){
                plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
            }
            else {
                if (!pionJoueur.estVilleOfCarteVilleDeckJoueur(villeActuelle))
                    throw new CarteVilleInexistanteDansDeckJoueurException("La carte ville correspondante à " + villeActuelle.getNomVille() + " n'est pas présente dans votre deck.");
                CarteJoueur carteJoueur = pionJoueur.defausseCarteVilleDeDeckJoueur(villeActuelle);
                pionJoueur.getPlateau().defausserCarteJoueur(carteJoueur);
                plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
            }
            // limite atteinte, on ne peut pas construire en revanche on peut déplacer la station
        } else {
            if (plateau.isVilleStationDeRecherche(villeActuelle))
                throw new VilleActuellePossedeDejaUneStationDeRechercheException("Impossible de rajouter une station de recherche, la ville " + villeActuelle.getNomVille() + " possède déjà une station de recherche.");
            if (!plateau.isVilleStationDeRecherche(villeStationDeRecherche))
                throw new VilleAvecAucuneStationDeRechercheException("La ville " + villeStationDeRecherche.getNomVille() + " ne possède aucune station de recherche.");
            if (!plateau.isVilleStationDeRecherche(villeActuelle) && plateau.isVilleStationDeRecherche(villeStationDeRecherche)) {
                plateau.getVilles().get(villeActuelle.getNomVille()).setStationDeRechercheVille(true);
                plateau.getVilles().get(villeStationDeRecherche.getNomVille()).setStationDeRechercheVille(false);
            }
        }
    }
}
