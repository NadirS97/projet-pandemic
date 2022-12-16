package modele.elements;



import lombok.Getter;
import lombok.Setter;
import modele.exceptions.EvenementInnexistantException;
import modele.exceptions.RoleIntrouvableException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VirusIntrouvableException;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.FileNotFoundException;

@Getter
@Setter
public class Joueur {

    @BsonProperty("_id")
    private String pseudo;


    private String mdp;

    public Joueur(String pseudo, String mdp) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }




}
