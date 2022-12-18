package dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import modele.elements.Partie;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.cartes.CarteVille;
import modele.elements.cartes.evenements.*;
import modele.elements.cartes.roles.*;
import modele.exceptions.*;
import modele.facade.FacadePandemic9Impl;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.FileNotFoundException;
import java.util.*;

public class Dao {

    private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

    private static final ClassModel<CarteVille> classVille = ClassModel.builder(CarteVille.class).enableDiscriminator(true).build();
    private static final ClassModel<CarteJoueur> classJoueur = ClassModel.builder(CarteJoueur.class).enableDiscriminator(true).build();

    private static final ClassModel<CarteRole> classRole = ClassModel.builder(CarteRole.class).enableDiscriminator(true).build();
    private static final ClassModel<CarteScientifique> classCarteScientifique = ClassModel.builder(CarteScientifique.class).enableDiscriminator(true).build();
    private static final ClassModel<CarteChercheuse> classChercheuse = ClassModel.builder(CarteChercheuse.class).enableDiscriminator(true).build();
    private static final ClassModel<CarteExpertAuxOperations> classExpert = ClassModel.builder(CarteExpertAuxOperations.class).enableDiscriminator(true).build();
    private static final ClassModel<CarteMedecin> classCarteMedecin = ClassModel.builder(CarteMedecin.class).enableDiscriminator(true).build();
    private static final ClassModel<CartePlanificateurDurgence> classCartePlan = ClassModel.builder(CartePlanificateurDurgence.class).enableDiscriminator(true).build();
    private static final ClassModel<CarteRepartiteur> classRepart = ClassModel.builder(CarteRepartiteur.class).enableDiscriminator(true).build();
    private static final ClassModel<CarteSpecialisteEnMiseEnQuarantaine> classSpecia = ClassModel.builder(CarteSpecialisteEnMiseEnQuarantaine.class).enableDiscriminator(true).build();

    private static final ClassModel<CarteEvenement> classEvent = ClassModel.builder(CarteEvenement.class).enableDiscriminator(true).build();

    private static final ClassModel<CarteParUneNuitTranquille> classCarteParUneNuitTranquille = ClassModel.builder(CarteParUneNuitTranquille.class).enableDiscriminator(true).build();
    private static final ClassModel<CartePontAerien> classPontAerien = ClassModel.builder(CartePontAerien.class).enableDiscriminator(true).build();
    private static final ClassModel<CartePopulationResiliente> classPop = ClassModel.builder(CartePopulationResiliente.class).enableDiscriminator(true).build();
    private static final ClassModel<CartePrevision> classPrev = ClassModel.builder(CartePrevision.class).enableDiscriminator(true).build();
    private static final ClassModel<CarteSubventionPublique> classSub = ClassModel.builder(CarteSubventionPublique.class).enableDiscriminator(true).build();


    private static final CodecRegistry pojoCodeRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).register(classJoueur,classCarteScientifique, classEvent,classVille,classCarteParUneNuitTranquille,classPontAerien,classPop,classPrev,classSub,classRole,classChercheuse, classExpert,classCarteMedecin, classCartePlan, classRepart, classSpecia).build()));

    //    private static final CodecRegistry pojoCodeRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static final MongoDatabase db = mongoClient.getDatabase("pandemic9").withCodecRegistry(pojoCodeRegistry);

//    public static void creerPartie(String codePartie) throws RoleIntrouvableException, VilleIntrouvableException, EvenementInnexistantException, VirusIntrouvableException, FileNotFoundException {
//        MongoCollection<Partie> partiesMongoCollection = db.getCollection("parties", Partie.class);
//        if (partiesMongoCollection.countDocuments() > 0)
//            partiesMongoCollection.drop();
////        Partie partie = Partie.getInstance(codePartie);
//        Partie partie = new Partie(codePartie);
//        // TODO : une partie du contenu de creerPartieQuatreJoueurs ou les autres devrait se trouver ici
//        partiesMongoCollection.insertOne(partie);
//    }

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

    public static Partie jouerTour(List<IAction> listeAction, FacadePandemic9Impl instance, Partie partie) throws EchecDeLaPartiePlusDeCarteJoueurException, CarteVilleInexistanteDansDeckJoueurException, NbCartesVilleDansDeckJoueurInvalideException, VirusDejaEradiqueException, VilleNonVoisineException, NbActionsMaxTourAtteintException, VilleActuellePossedeDejaUneStationDeRechercheException, CarteEvenementNonTrouveDansDefausseException, JoueursNonPresentMemeVilleException, VirusDejaTraiteException, VilleIntrouvableException, VilleDestinationEstVilleActuelleException, MauvaisRoleException, VirusInexistantDansLaVilleActuelException, VilleAvecAucuneStationDeRechercheException, DonneeManquanteException, TropDeCarteEnMainException, NuitTranquilleException, VilleDejaEclosException, NbCubesAAjouterInvalideException, PropagationImpossibleCarSpecialisteQuarantaineException, VictoireFinDePartieException, DefaitePartieTermineException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);

        for (IAction action : listeAction){
            instance.jouerAction(partie.getJoueurActuel(),action);
        }
        instance.piocherCartes(partie.getJoueurActuel());
        instance.propagation(partie.getJoueurActuel());

        if (partie.presenceMedecin())
            partie.effetCarteMedecin();

        partie.joueurSuivant();
        partie.estUneVictoire();

//        partieMongoCollection.updateOne(Filters.eq("_id", "123abcd"),Updates.combine(
//                Updates.set("defaite", partie.isDefaite()),
//                Updates.set("indexJoueur", partie.getIndexJoueur()),
//                Updates.set("joueurActuel", partie.getJoueurActuel()),
//                Updates.set("listeJoueur", partie.getJoueurs()),
//                Updates.set("plateau", partie.getPlateau()),
//                Updates.set("victoire", partie.isVictoire())
//        ));

        partieMongoCollection.drop();
        partieMongoCollection.insertOne(partie);
        return partie;
    }
}