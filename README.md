# Projet Pandemic

### Fait par le groupe 9 :
- Jonathan BALTACI
- Robin TRASSARD
- Armel KOUASSI
- Nadir SAIAH

Année : Master 1 MIAGE - 2022
------------

### Mise en place de l'environnement de travail:

**1-** On lance tout d'abord l'application **Docker Desktop**  :
Lien de téléchargement: [ici](https://www.docker.com/products/docker-desktop/)

**2-**  On start notre conteneur :
En faisant un run dans notre fichier **docker-compose**
***Chemin:** projet-pandemic-9/docker-compose.yaml*
>Qui s'occupera de lancer automatiquement notre conteneur MongoDB ou de créer un nouveau si celui-ci n'existe pas encore.
>- Nom du conteneur : *mongodb*
>- Port utilisé : *27017*
>- Image : *mongo:latest*

**3-** Une fois ceci fait, on peut exécuter l'ensemble des tests :
En faisant un run de la classe **FacadePandemic9ImplTest**
***Chemin:** projet-pandemic-9/src/test/java/modele/facade/FacadePandemic9ImplTest.java**
>Afin de tester l'ensemble des fonctions de notre facade.

**4-** On peut éventuellement lancer l'application **MongoDBCompass**:
Lien de téléchargement: [ici](https://www.mongodb.com/try/download/compass)
Application nous permettant d'avoir une meilleure lecture de la base de données avec une interface graphique.
URL de connexion à notre conteneur et donc à notre BDD :
```
mongodb://localhost:27017/
```
-----------------------------------
### Difficultés rencontrées:

#### Problème avec partieMongoCollection.find().first() :
partieMongoCollection.find().first() ne fonctionne pas, on suppose que c'est à cause de la complexité des objets qu'on utilise.
- On a essayé de régler les soucis en utilisant le Discriminator et en ajoutant nos ClassModel problématiques (non concrètes, c'est à dire les classes abstraites ainsi que les interfaces) ainsi que les classes concrètes qui les extends ou les implements mais ceci nous a permis de régler une partie du problème.
  En les ajoutant dans le pojoCodeRegistry avec un .register() comme suit :
```java
CodecRegistry pojoCodeRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).register(classJoueur,classCarteScientifique, classEvent,classCarteParUneNuitTranquille,classPontAerien,classPop,classPrev,classSub,classRole,classChercheuse, classExpert,classCarteMedecin, classCartePlan, classRepart, classSpecia).build()));
```
**Pour faire simple :**
- L'écriture, l'encodage de Java en BSON -> Se fait sans aucun soucis, on arrive à insérer un objet Partie dans notre BDD :
```java
partieMongoCollection.insertOne(partie);
```
- La lecture, le décodage de BSON en Java -> Afin de lire l'objet depuis notre BDD, on n'y arrive pas, après 48 h à essayer de debug l'exception CodecConfigurationException
```java
Partie partie = partieMongoCollection.find().first();
```
>**Erreur :**
>Exception in thread "main" org.bson.codecs.configuration.CodecConfigurationException: An exception occurred when decoding using the AutomaticPojoCodec.
>
>Decoding into a 'Partie' failed with the following exception:
>
>Failed to decode 'Partie'. Decoding 'joueurActuel' errored with: Failed to decode 'PionJoueur'. Decoding 'deckJoueur' errored with: null
>
>A custom Codec or PojoCodec may need to be explicitly configured and registered to handle this type.

On a d'abord essayé de faire un Update() sauf que pour ça on a besoin de récupérer le document depuis la BDD.
```java
Partie partie = partieMongoCollection.find().first();
partieMongoCollection.updateOne(Filters.eq("_id", "123abcd"),Updates.combine(  
                Updates.set("defaite", partie.isDefaite()),  
                Updates.set("indexJoueur", partie.getIndexJoueur()),  
                Updates.set("joueurActuel", partie.getJoueurActuel()),  
                Updates.set("joueurs", partie.getJoueurs()), 
                Updates.set("plateau", partie.getPlateau()),
                Updates.set("victoire", partie.isVictoire())  
        ));
```

**Solution**
On a eu l'idée de contourner le problème en effectuant:
```java
partieMongoCollection.insertOne(partie);
``` 
En insérant de nouveau, le nouvel état de notre partie à la fin de chaque tour, dans notre BDD. Nous permettant ainsi d'avoir une persistance des données, et une BDD avec l'ensemble de nos variables enregistrées à chaque fin de tour au fur et à mesure que le jeu se déroule.

-----------------------------------
### Travail effectué :
- On a développé une application fonctionnelle, en utilisant quelques design pattern :
    - **Stratégie** : pour le fonctionnement des Actions ainsi que des Effets des Cartes.
    - **Façade** : avec l'ensemble des fonctionnalités proposées pour l'utilisateur
      > Exemple : création d'une partie, exécuter une action, jouer un tour...

- On a mis en place un package DTO, qui sert de squelette pour la lecture de nos données du fichier JSON *DonneesPlateau.json*
    - ***Chemin du package DTO :** projet-pandemic-9/src/main/java/modele/dto/*
    - ***Chemin du fichier JSON :** projet-pandemic-9/src/main/resources/DonneesPlateau.json*
      Fichier contenant :
        - La liste des villes avec le nom, la population totale, la population par km² ainsi que la liste des villes voisines.
        - La liste des virus avec la couleur, et l'etat (*de base étant NON_TRAITE*)
- On a mis en place une fonction dans la class Partie *lectureDonneesPlateau()* permettant de lire les données depuis le fichier en JSON en utilisant les classes DTO afin de construire les objets en Java
```java
public DonneesPlateauDTO lectureDonneesPlateau() throws FileNotFoundException {
...
}
```
- On a également mis en place une classe *DonneesVariablesStatiques* nous permettant de configurer l'ensemble des variables du jeu, par exemple si l'on souhaite modifier les règles sans avoir à retoucher au code, le nombre de cubes existant par virus, par exemple.
  ***Chemin :** projet-pandemic-9/src/main/java/modele/utils*/DonneesVariablesStatiques.java
- On a également mis en place un package enums contenant des enums :
    - CouleurPionRole : qu'on a utilisé afin de créer nos cartes roles, on peut y rajouter des couleurs.
    - EtatVirus : Traite / Non_Traite / Eradique
    - NomsEvenements : avec l'ensemble des noms des évènements, qu'on a utilisé afin de créer nos cartes évènements, on peut y rajouter des évènements, éventuellement.
    - NomsRoles : avec l'ensemble des noms des rôles, qu'on a utilisé afin de créer nos cartes rôles, on peut y rajouter des rôles éventuellement.