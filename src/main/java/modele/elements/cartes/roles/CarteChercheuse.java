package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class CarteChercheuse extends CarteRole  {

    public CarteChercheuse(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.CHERCHEUSE);
        super.setDescriptionRole(
                "Lorsque vous partagez des connaissances, vous pouvez donner n'importe quelle carte Ville de " +
                        "votre main. La carte n'a pas à correspondre à la ville où vous êtes. Durant son tour, " +
                        "un joueur qui partage des connaissance avec vous peut vous prendre n'importe quelle carte.");
    }




}
