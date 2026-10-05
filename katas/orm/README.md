# ORM

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/orm](https://codingdojo.org/kata/orm/)

## Le kata

Apprendre à se servir d'un ORM sur un petit carnet de contacts (nom, prénom, date de naissance) :

- enregistrer et relire des personnes, avec une base de **test** (`tests.db`, neuve à chaque test) distincte de la base de **production** (dont l'URL est donnée par la variable d'environnement `DB_URL`) ;
- remplir la base de production avec les contacts de l'énoncé ;
- faire évoluer le schéma (ajout d'un e-mail) par des **migrations** versionnées, qu'on peut appliquer et défaire.

ORM retenu : [Exposed](https://github.com/JetBrains/Exposed) (JetBrains), sur SQLite, ce qui évite d'installer un serveur pour faire tourner les tests.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Les tests tournent sur de vrais fichiers SQLite, dans un dossier temporaire JUnit (`@TempDir`) : chaque test part d'une base vide.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `save a person and read it back` | Compilation impossible. |
| 2 | 🟢 `save and find persons with Exposed on SQLite` | L'objet métier `Person`, la table `Persons` et le carnet `Contacts` qui traduit de l'un à l'autre ; la table est créée au démarrage. |
| 3 | 🔴 `read the production database URL from DB_URL` | `forProduction` n'existe pas. L'environnement est passé en paramètre (par défaut `System.getenv()`) pour être testable. |
| 4 | 🟢 `connect to the production database given by DB_URL` | Sans `DB_URL`, une erreur explicite. |
| 5 | 🔴 `seed the production contacts` | `ProductionContacts` et `all` n'existent pas. |
| 6 | 🟢 `list contacts and seed the production ones` | |
| 7 | 🔴 `record the schema version in the database` | `Migrator` n'existe pas. |
| 8 | 🟢 `apply numbered migrations and record the schema version` | Une table `schema_version` ; la migration 1 crée la table. |
| 9 | 🔵 `create the schema through the migrations` | `Contacts` ne crée plus la table lui-même : il migre la base à la dernière version. |
| 10 | 🔴 `store an email with migration 2` | `Person` n'a pas d'e-mail. |
| 11 | 🟢 `add the email column with migration 2` | Pour que la migration 2 puisse ajouter la colonne, la migration 1 ne pouvait plus créer la table d'après la définition Exposed actuelle (qui a déjà l'e-mail) : chaque migration est désormais écrite en SQL figé. |
| 12 | 🔴 `migrate back down and keep the data` | Un premier rouge venait d'un import oublié dans le test : corrigé en amendant le commit (avant de pousser). Le vrai rouge : la colonne `email` reste après le retour en version 1. |
| 13 | 🟢 `undo migrations down to a target version` | Les `down` sont joués dans l'ordre inverse. |
| 14 | 📌 `upgrade a populated version 1 database` | Le cas de la production : une base v1 avec des données monte en v2 sans rien perdre. |

## Solution

- `Person` est un objet métier pur ; `Persons` décrit la table ; `Contacts` fait le lien. Le reste du code ne voit jamais de ligne de base.
- `Migrator` lit la version enregistrée, puis joue les `up` (pour monter) ou les `down` (pour descendre) nécessaires, dans une seule transaction.
- **Une migration est un fait historique** : son SQL est figé. Générer la migration 1 à partir de la table d'aujourd'hui aurait fait échouer la migration 2 sur une base neuve (colonne en double), et c'est ce piège que l'étape 11 a révélé.

## Ce que j'en retiens

- Tester contre une vraie base (ici un fichier SQLite jetable) plutôt qu'un simulacre : ce sont les détails de la base (colonnes, `DROP COLUMN`) qui sont en jeu.
- Injecter l'environnement plutôt que lire `System.getenv()` en dur rend la configuration de production testable sans toucher aux variables du processus.
- Pour un vrai projet, un outil dédié (Flyway, Liquibase) ferait le travail de `Migrator` ; l'écrire une fois montre ce qu'il fait.

## Lancer les tests

```bash
./gradlew :katas:orm:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/orm   # l'historique TDD
```
