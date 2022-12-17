import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dao.Dao;
import modele.exceptions.EvenementInnexistantException;
import modele.exceptions.RoleIntrouvableException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VirusIntrouvableException;
import modele.facade.FacadePandemic9;
import modele.facade.FacadePandemic9Impl;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.FileNotFoundException;

public class MongoMain {
    /**
     * Normalement juste docker desktop installé sur son pc
     * + le plugin le docker sur intellij
     * run le docker-compose et c'est bon
     * docker compass ou alors fenetre database d'intellij pour l'interface mongodb
     */
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            FacadePandemic9Impl instance = new FacadePandemic9Impl();
            instance.creerPartieQuatreJoueurs("123abcd");
//            instance.creerPartieTroisJoueurs("123abcd");
//            instance.creerPartieDeuxJoueurs("123abcd");
            instance.getPartie().setDefaite(true);
            instance.sauvegarderPartie();
            instance.getPartie().setDefaite(false);
            System.out.println(instance.getPartie().isDefaite());

            // sauvegarde partie OK , mais impossible de recup la partie depuis la bdd
            // multiples erreur de codec
//           Dao.reprendrePartie();

            // on arrive cependant a recup joueur depuis la bdd
            //            Dao.inscription("truc","ah");
//            System.out.println(Dao.reprendrejoueur());



            System.out.println("connexion success");

        } catch (MongoException me) {
            System.err.println("Une erreur a eu lieu lors de la connexion");
        } catch (RoleIntrouvableException | VilleIntrouvableException | EvenementInnexistantException |
                 VirusIntrouvableException | FileNotFoundException e) {
            throw new RuntimeException(e);


        }
    }
}
