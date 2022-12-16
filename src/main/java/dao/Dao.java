package dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import modele.elements.Joueur;
import modele.elements.Partie;
import modele.exceptions.EvenementInnexistantException;
import modele.exceptions.RoleIntrouvableException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VirusIntrouvableException;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Dao {

    private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private static final CodecRegistry pojoCodeRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static final MongoDatabase db = mongoClient.getDatabase("pandemic9").withCodecRegistry(pojoCodeRegistry);

    public static void inscription(String pseudo, String mdp) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        Joueur joueur = new Joueur(pseudo, mdp);
        joueurMongoCollection.drop();
        joueurMongoCollection.insertOne(joueur);
    }

    public static void creerPartie(String codePartie) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        MongoCollection<Partie> partiesMongoCollection = db.getCollection("parties", Partie.class);
        if (partiesMongoCollection.countDocuments() > 0)
            partiesMongoCollection.drop();
//        Partie partie = Partie.getInstance(codePartie);
        Partie partie = new Partie(codePartie);
        // TODO : une partie du contenu de creerPartieQuatreJoueurs ou les autres devrait se trouver ici
        partiesMongoCollection.insertOne(partie);
    }

    public static boolean seReconnecterAuJeu(String idPartie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq("_id", idPartie))).first();
        Objects.requireNonNull(partie).getPartie(idPartie);
        return true;
    }

    public static Partie creerPartieQuatreJoueurs(String codePartie) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        if (partieMongoCollection.countDocuments() > 0)
            partieMongoCollection.drop();
        Partie partie = Partie.creerPartieQuatreJoueurs(codePartie);
        partieMongoCollection.insertOne(partie);
        return partie;
    }



}
