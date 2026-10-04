# Pizza Maker

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/pizza-maker](https://codingdojo.org/kata/pizza-maker/)

## Le kata

Un programme interactif pour comprendre le style **asynchrone** : on enfourne des pizzas (« cook a Margherita Pizza »), on regarde le four (« show queue »), on sort une pizza (« Get out the pizza »).

- une pizza cuit en 45 secondes, et le programme le signale ;
- laissée 15 secondes de plus, elle brûle ;
- chaque pizza sortie cuite rapporte un point.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Chaque pizza est une **coroutine** qui attend (`delay`) puis change d'état. Les tests utilisent le **temps virtuel** de `kotlinx-coroutines-test` : `advanceTimeBy(45_000)` fait passer 45 secondes instantanément, et la suite tourne en quelques millisecondes.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `announce a pizza cooked after 45 seconds` | À 44,999 s, rien ; à 45 s, l'alerte. Compilation impossible. |
| 2 | 🟢 `announce a pizza after 45 seconds in the oven` | `scope.launch { delay(45.seconds); alert(...) }`. |
| 3 | 🔴 `earn a point for a pizza taken out in time` | `takeOut` et `points` n'existent pas. |
| 4 | 🟢 `take pizzas out and score the cooked ones` | Le four garde ses emplacements ; la coroutine change l'état de la pizza. |
| 5 | 🔴 `burn a pizza left 15 seconds too long` | Pas d'état brûlé. |
| 6 | 🟢 `burn a pizza 15 seconds after it is cooked` | Un second `delay`. |
| 7 | 🔴 `never burn a pizza already taken out` | **Le piège de l'asynchrone** : la pizza est sortie à 50 s, mais sa coroutine tourne toujours et annonce « brûlée » à 60 s. |
| 8 | 🟢 `stop the timer of a pizza taken out` | Chaque pizza garde son `Job`, annulé à la sortie. |
| 9 | 🔴 `show the queue of the oven` | `queue` n'existe pas. |
| 10 | 🟢 `show the queue of the oven` | La liste des pizzas et de leur état. |
| 11 | 🔴 `refuse to take a pizza out of an empty oven` | `NoSuchElementException` au lieu d'un message clair. |
| 12 | 🟢 `report an empty oven` | Un `check`. |
| 13 | 🔴 `understand the three commands of the kata` | La couche de commandes n'existe pas. |
| ⛔ | vert refusé | Mon premier jet contenait aussi un `main` de terminal qui ne compilait pas, et un message « four vide » qu'aucun test ne demandait. Refusé par le garde-fou ; refait sans ces ajouts. |
| 14 | 🟢 `understand the three commands of the kata` | Une expression régulière pour « cook a … pizza », et deux commandes fixes. |
| 15 | 🔵 `add an interactive terminal over the commands` | La boucle de terminal, dans son propre commit : un adaptateur sans logique, non testé, où les alertes s'affichent pendant qu'on tape. |

Pour jouer : lancer `main` dans `Console.kt` (le temps est réel, il faut patienter 45 s).

## Ce que j'en retiens

- Le temps virtuel rend l'asynchrone aussi facile à tester que du code synchrone, et bien plus rapide que de vraies attentes.
- Le test 7 montre ce que l'asynchrone a de spécifique : un traitement lancé continue de vivre tant qu'on ne l'arrête pas.

## Lancer les tests

```bash
./gradlew :katas:pizza-maker:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/pizza-maker   # l'historique TDD
```
