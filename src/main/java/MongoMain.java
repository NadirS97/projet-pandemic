import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dao.Dao;
import modele.elements.*;
import modele.elements.actions.IAction;
import modele.elements.actions.deplacement.DeplacementVoiture;
import modele.elements.cartes.*;
import modele.elements.cartes.evenements.*;
import modele.elements.cartes.roles.*;
import modele.elements.enums.CouleurPionsRole;
import modele.exceptions.*;
import modele.facade.FacadePandemic9Impl;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MongoMain {
    /**
     * Normalement juste docker desktop installé sur son pc
     * + le plugin le docker sur intellij
     * run le docker-compose et c'est bon
     * docker compass ou alors fenetre database d'intellij pour l'interface mongodb
     */
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";

        //Test afin de vérifier que notre partie est bien enregistré dans notre BDD une fois par tour

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            System.out.println("Début création partie 4 joueurs");

            FacadePandemic9Impl instance = new FacadePandemic9Impl();
            instance.creerPartieQuatreJoueurs("123abcd");

            System.out.println("Fin création partie 4 joueurs et insertion dans la BDD");

            System.out.println("Début jouerTour");

            PionJoueur joueurActuel = instance.getPartie().getJoueurActuel();
            joueurActuel.setRoleJoueur(new CarteRepartiteur(CouleurPionsRole.ROSE));
            IAction action = new DeplacementVoiture(instance.getPartie().getPlateau().recupererVilleAvecNom("Washington"));
            IAction action1 = new DeplacementVoiture(instance.getPartie().getPlateau().recupererVilleAvecNom("Atlanta"));
            IAction action2 = new DeplacementVoiture(instance.getPartie().getPlateau().recupererVilleAvecNom("Chicago"));
            IAction action3 = new DeplacementVoiture(instance.getPartie().getPlateau().recupererVilleAvecNom("Atlanta"));
            List<IAction> listeActions = List.of(action,action1, action2, action3);
            instance.jouerTour(listeActions);

            System.out.println("Fin jouerTour et insertion de la Partie dans la BDD");


            System.out.println("connexion success");

        } catch (MongoException me) {
            System.err.println("Une erreur a eu lieu lors de la connexion");
        } catch (RoleIntrouvableException | EvenementInnexistantException | VirusIntrouvableException |
                 FileNotFoundException | NuitTranquilleException | VilleDejaEclosException |
                 CarteVilleInexistanteDansDeckJoueurException | VirusDejaEradiqueException | VilleNonVoisineException |
                 DefaitePartieTermineException | VictoireFinDePartieException |
                 EchecDeLaPartiePlusDeCarteJoueurException | NbCartesVilleDansDeckJoueurInvalideException |
                 NbActionsMaxTourAtteintException | VilleActuellePossedeDejaUneStationDeRechercheException |
                 CarteEvenementNonTrouveDansDefausseException | JoueursNonPresentMemeVilleException |
                 VirusDejaTraiteException | VilleDestinationEstVilleActuelleException | MauvaisRoleException |
                 VirusInexistantDansLaVilleActuelException | TropDeCarteEnMainException |
                 VilleAvecAucuneStationDeRechercheException | DonneeManquanteException |
                 NbCubesAAjouterInvalideException | PropagationImpossibleCarSpecialisteQuarantaineException |
                 VilleIntrouvableException e) {
            throw new RuntimeException(e);
        }
    }
}