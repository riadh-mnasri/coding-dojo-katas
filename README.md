# Coding Dojo Katas

🇬🇧 [English version](README.en.md)

Les [katas de codingdojo.org](https://codingdojo.org/kata/) résolus en **Kotlin**, en **TDD strict**, avec pour chaque kata une documentation (en français et en anglais) qui explique la démarche, la solution et ce que j'en retiens.

## La règle du jeu : du TDD vérifiable

Chaque kata est développé par petits cycles :

1. **Rouge** : j'écris *un seul* test, je le lance et je vérifie qu'il échoue pour la bonne raison.
2. **Vert** : j'écris le code minimal qui le fait passer.
3. **Refactor** : je nettoie le code (et les tests) sans jamais quitter le vert.

Chaque étape fait l'objet de **son propre commit**, au format Angular :

| Étape | Type de commit | Exemple |
|---|---|---|
| Rouge | `test(<kata>)` | `test(bowling): score a gutter game` |
| Vert | `feat(<kata>)` | `feat(bowling): sum knocked down pins` |
| Refactor | `refactor(<kata>)` | `refactor(bowling): extract frame scoring` |

Les commits rouges contiennent dans leur description la raison de l'échec (erreur de compilation ou assertion). L'historique git est donc la preuve de la démarche, et non une reconstitution :

```bash
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/bowling
```

### Le garde-fou

Le script [`scripts/tdd.sh`](scripts/tdd.sh) lance les tests du kata avant chaque commit et **refuse** :

- une étape rouge si toute la suite passe (un test qui ne peut pas échouer ne prouve rien) ;
- une étape verte ou de refactor si un test échoue ;
- un message de commit dont le type ne correspond pas à l'étape.

Un test qui passe dès sa première exécution n'est pas maquillé en étape rouge : il est commité avec la phase `pin` (`test(<kata>): ...`, mention « Passed on first run » dans le corps). Il ne force aucun code et sert de documentation ou de filet de sécurité.

```bash
scripts/tdd.sh bowling red      "test(bowling): score a gutter game"
scripts/tdd.sh bowling green    "feat(bowling): sum knocked down pins"
scripts/tdd.sh bowling refactor "refactor(bowling): extract frame scoring"
```

### La documentation de chaque kata

Chaque dossier `katas/<kata>/` contient un `README.md` (français) et un `README.en.md` (anglais) :

- **Le kata** : l'énoncé résumé, avec le lien vers codingdojo.org ;
- **Démarche TDD** : la suite réelle des cycles, rédigée à partir de l'historique git du kata ;
- **Solution** : le design obtenu et les choix qui comptent ;
- **Ce que j'en retiens** : le point d'apprentissage du kata.

## Les katas

<!-- katas:start -->
**Avancement : 14 / 61 katas.**

| Kata | Ce qu'il fait travailler | Statut | Énoncé |
|---|---|---|---|
| Anagram | performance vs lisibilité | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Anagram/) |
| Args | parsing, conception extensible | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Args/) |
| Bank OCR | parsing, checksum, recherche de corrections | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/BankOCR/) |
| Birthday Greetings | architecture hexagonale, ports et adapters | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/birthday-greetings/) |
| [Bowling](katas/bowling/README.md) | règles métier à états | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/Bowling/) |
| Brainfuck | interpréteur, instructions extensibles | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Brainfuck/) |
| Christmas Delivery | concurrence, files d'attente | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/christmas-delivery/) |
| [Code Cracker](katas/code-cracker/README.md) | substitution, aller-retour | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/CodeCracker/) |
| CQRS Booking | CQRS, séparation lecture/écriture | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/CQRS_Booking/) |
| Cupcake | patterns Décorateur et Composite | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/cupcake/) |
| Depth First Search | récursion, conversation simulée | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/DepthFirstSearch/) |
| [Diamond](katas/diamond/README.md) | tests de propriétés | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/Diamond/) |
| [Dictionary Replacer](katas/dictionary-replacer/README.md) | remplacement de chaînes | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/DictionaryReplacer/) |
| Eight Queens | backtracking, parcours d'arbre | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/eight-queens/) |
| Elephant Carpaccio | découpage en tranches fines | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/elephant-carpaccio/) |
| Employee Report | assertions ciblées, maintenabilité des tests | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Employee-Report/) |
| [FizzBuzz](katas/fizz-buzz/README.md) | baby steps, règles composables | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/FizzBuzz/) |
| [FooBarQix](katas/foo-bar-qix/README.md) | évolution des exigences | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/FooBarQix/) |
| Game of Life | automate cellulaire, immutabilité | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/GameOfLife/) |
| Gilded Rose | code legacy, tests de caractérisation | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/gilded-rose/) |
| Greed | règles de score | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Greed/) |
| [Hello](katas/hello/README.md) | doublures de test | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/Hello/) |
| JEE Web Authentication | mocks vs stubs, filtres servlet | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/JEEWebAuthentication/) |
| Lags | programmation dynamique | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Lags/) |
| Langton Ant | automate cellulaire, règles extensibles | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/LangtonAnt/) |
| [Leap Years](katas/leap-years/README.md) | règles et exceptions | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/LeapYears/) |
| [Manhattan Distance](katas/manhattan-distance/README.md) | objets sans getters (Tell, don't ask) | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/manhattan-distance/) |
| Markov Chain | statistiques, aléatoire injecté | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/MarkovChain/) |
| Mars Rover | commandes, obstacles, parsing de carte | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/mars-rover/) |
| Mastermind | comptage, choix des tests | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Mastermind/) |
| Mathematical AST | arbre syntaxique, Visiteur | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/mathematical-ast/) |
| Minesweeper | grilles, voisinage | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Minesweeper/) |
| Movie Rental | refactoring (Fowler) | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/movie-rental/) |
| Nearest Color | distance, égalités | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/NearestColor/) |
| Nim Game | règles de jeu, tours | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Nim/) |
| Number to LCD | rendu texte, exigences changeantes | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/NumberToLCD/) |
| Numbers in Words | conversion bidirectionnelle | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/NumbersInWords/) |
| ORM | ORM, migrations de schéma | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/orm/) |
| PacMan | jeu à ticks, état du plateau | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/PacMan/) |
| Pagination Seven | cas limites d'affichage | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/PaginationSeven/) |
| Pizza Maker | asynchrone, temps virtuel | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/pizza-maker/) |
| Poker Hands | classement et comparaison | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/PokerHands/) |
| Potter | optimisation de remises | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Potter/) |
| Quote of the Day | service web minimal | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/QotdCgi/) |
| Range | objet valeur, bornes | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Range/) |
| Reversi | coups légaux, directions | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Reversi/) |
| [Roman Calculator](katas/roman-calculator/README.md) | trouver le prochain test | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/RomanCalculator/) |
| [Roman Numerals](katas/roman-numerals/README.md) | algorithme glouton | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/RomanNumerals/) |
| [RPN Calculator](katas/rpn-calculator/README.md) | pile, opérations extensibles | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/RPN/) |
| RSA | arithmétique modulaire | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/rsa/) |
| Social Network | example mapping, domaine | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/social-network/) |
| [String Calculator](katas/string-calculator/README.md) | gestion d'erreurs incrémentale | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/StringCalculator/) |
| Sudoku Concurrent Resolver | propagation de contraintes par messages | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/sudoku/) |
| [Tennis](katas/tennis/README.md) | machine à états | ✅ fait | [codingdojo.org](https://codingdojo.org/kata/Tennis/) |
| Texas Hold'em | meilleure main parmi 7 cartes | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/TexasHoldEm/) |
| Tic Tac Toe | double boucle TDD | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/tic-tac-toe/) |
| Trading Card Game | boucle de jeu, doublures | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/TradingCardGame/) |
| Trip Service | casser les dépendances du legacy | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/TripService/) |
| Wallet | port externe (taux de change) | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Wallet/) |
| Word Wrap | récursion, cas limites | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/WordWrap/) |
| Yahtzee | catégories de score | ⏳ à faire | [codingdojo.org](https://codingdojo.org/kata/Yahtzee/) |
<!-- katas:end -->

## Stack

- Kotlin 2.0 (JVM 17), Gradle 8 en multi-module : **un module Gradle par kata**, sans dépendance entre katas.
- JUnit 5 + AssertJ pour tous les katas. Quelques katas ajoutent ce dont leur sujet a besoin (coroutines pour la concurrence, MockK pour les doublures, Exposed + SQLite pour l'ORM).
- Tests nommés par comportement attendu, structurés en *Given / When / Then* quand ils ont une mise en place.

## Lancer les tests

```bash
./gradlew test                          # tous les katas
./gradlew :katas:bowling:test           # un seul kata
```

Prérequis : un JDK 17 ou plus récent. Le wrapper Gradle télécharge le reste.

## Licence

[MIT](LICENSE). © 2026 Riadh MNASRI. Les énoncés des katas appartiennent à leurs auteurs respectifs, cités sur [codingdojo.org](https://codingdojo.org/kata/).
