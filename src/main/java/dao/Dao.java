package dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import modele.elements.Partie;
import modele.exceptions.EvenementInnexistantException;
import modele.exceptions.RoleIntrouvableException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VirusIntrouvableException;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.FileNotFoundException;

public class Dao {

    private static final MongoClient mongoClient = MongoClients.create("mongodb://172.17.0.2:27017");
    private static final CodecRegistry pojoCodeRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static final MongoDatabase db = mongoClient.getDatabase("pandemic").withCodecRegistry(pojoCodeRegistry);

    public static void creerPartieQuatreJoueurs(String pseudo, String codePartie) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = Partie.creerPartieQuatreJoueurs(codePartie);
        //partie.ajouterPartieJoueur(new PartieJoueur(pseudo, true));
        partieMongoCollection.insertOne(partie);
    }
}
