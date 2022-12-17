package dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import modele.elements.Joueur;
import modele.elements.Partie;
import modele.elements.PionJoueur;
import modele.elements.actions.IAction;
import modele.exceptions.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.FileNotFoundException;
import java.util.*;

public class Dao {

    private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private static final CodecRegistry pojoCodeRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
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
        Partie partie = Partie.getInstance(codePartie);
        // TODO : une partie du contenu de creerPartieQuatreJoueurs ou les autres devrait se trouver ici
        partiesMongoCollection.insertOne(partie);
    }

    public static boolean seReconnecterAuJeu(String idPartie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq("_id", idPartie))).first();
        System.out.println(partie);
//        Objects.requireNonNull(partie).getPartie(idPartie);
        return true;
    }

    // test de la fonction au dessus mais qui retourne un objet partie pour la reprise de partie
    public static Partie reprendrePartie() {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partie = new ArrayList<>();
        partieMongoCollection.find().forEach(a->partie.add(a));
        return partie.stream().toList().get(0);
//        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq("_id", idPartie))).first();
//        Objects.requireNonNull(partie).getPartie(idPartie);
//        return partie;

    }
    public static Joueur reprendrejoueur(){
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        Collection<Joueur> joueurs = new ArrayList<>();
        joueurMongoCollection.find().forEach(joueurs::add);
        return joueurs.stream().toList().get(0);
    }


    public static void sauvegarderPartie(Partie partie){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties",Partie.class);
        partieMongoCollection.drop();
        partieMongoCollection.insertOne(partie);
    }

    //=================================================================================================================

    public static Partie creerPartieDeuxJoueurs(String codePartie) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        if (partieMongoCollection.countDocuments() > 0)
            partieMongoCollection.drop();
        Partie partie = Partie.creerPartieDeuxJoueurs(codePartie);
        partieMongoCollection.insertOne(partie);
        return partie;
    }

    public static Partie creerPartieTroisJoueurs(String codePartie) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        if (partieMongoCollection.countDocuments() > 0)
            partieMongoCollection.drop();
        Partie partie = Partie.creerPartieTroisJoueurs(codePartie);
        partieMongoCollection.insertOne(partie);
        return partie;
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
