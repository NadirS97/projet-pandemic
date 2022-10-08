package facade;

import modele.Couleurs;
import modele.Ville;
import modele.enums.Virus;

import java.util.*;

public class Main {

    public static void main(String[] args) {
       Facade facade = new Facade();
       facade.initialisation();
       facade.displaybullshit();
    }
}
