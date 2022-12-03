package modele.elements;



import org.bson.codecs.pojo.annotations.BsonProperty;

public class Joueur {

    @BsonProperty("_id")
    private String pseudo;

    private String mdp;

    public Joueur(String pseudo, String mdp){
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
