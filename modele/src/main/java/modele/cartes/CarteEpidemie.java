package modele.cartes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
public class CarteEpidemie extends Carte{
    private List<String> actionsCarteEpidemie;
    //Pour l'instant on met une liste de String mais c'est à modifier si on crée une classe d'Actions ou d'Effet
}
