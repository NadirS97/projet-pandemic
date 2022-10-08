package facade;

import modele.Couleurs;
import modele.Ville;
import modele.enums.Virus;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Ville> villes = new ArrayList<>();
        List<Virus> virus = new ArrayList<>();

        virus.add(new Virus("Ebola" , Couleurs.BLEU));
        virus.add(new Virus("Covid19" , Couleurs.ROUGE));
        virus.add(new Virus("SIDA" , Couleurs.JAUNE));
        virus.add(new Virus("GRIPPE" , Couleurs.NOIR));


      villes.add(new Ville("ville1", new ArrayList<>() , 90,90, virus.get(0)));
      villes.add(new Ville("ville2", new ArrayList<>() , 90,90, virus.get(1)));
      villes.add(new Ville("ville3", new ArrayList<>() , 90,90, virus.get(2)));
      villes.add(new Ville("ville4", new ArrayList<>() , 90,90, virus.get(3)));

      for (Ville ville : villes){
          System.out.println(ville);
      }
    }
}
