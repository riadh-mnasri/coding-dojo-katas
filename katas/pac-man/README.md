# PacMan

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/PacMan](https://codingdojo.org/kata/PacMan/)

## Le kata

Pac-Man se déplace sur une grille remplie de pastilles. L'énoncé donne une liste volontairement incomplète : il a une direction, avance à chaque tick, peut être tourné, mange les pastilles, passe d'un bord à l'autre, s'arrête devant un mur, ne tourne pas vers un mur, marque des points ; puis les monstres, les niveaux, l'animation...

Le plateau est du texte, comme le suggère l'énoncé. Pac-Man y est dessiné bouche ouverte : `V` quand il regarde vers le haut, `^` vers le bas, `>` vers la gauche, `<` vers la droite.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Comme le conseille l'énoncé, l'état change par pas discrets : une méthode `tick()` et un rendu texte rendent chaque règle facile à tester, plateau avant, plateau après.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `move up on a tick and eat the dot` | Compilation impossible. (Mon attendu passait par un `replaceRange` illisible : réécrit en clair en amendant le commit, avant de pousser.) |
| 2 | 🟢 `move forward on each tick and eat dots` | Une grille de caractères, la position et la direction de Pac-Man. |
| 3 | 🔴 `turn pacman` | `turn` n'existe pas. |
| ⛔ | vert refusé | Mon attendu oubliait la pastille restée à droite après un pas à gauche : c'était le test qui se trompait. Corrigé en amendant le commit rouge (non poussé), puis vert rejoué. |
| 4 | 🟢 `turn pacman` | Nouvelle direction, nouveau dessin. |
| 5 | 🔴 `wrap around the edges of the board` | `IndexOutOfBoundsException` au bord. |
| 6 | 🟢 `wrap around the edges` | `floorMod` sur les deux axes. |
| 7 | 🔴 `stop in front of a wall` | Pac-Man traverse le mur. |
| 8 | 🟢 `stop in front of walls` | Un mur devant : on ne bouge pas. |
| 9 | 🔴 `refuse to turn towards a wall` | La rotation vers un mur est acceptée. |
| 10 | 🟢 `ignore a turn towards a wall` | La case « devant » est calculée par une seule fonction, `ahead`, partagée par le déplacement et la rotation. |
| 11 | 🔴 `complete the level when every dot is eaten` | `isLevelComplete` n'existe pas. |
| 12 | 🟢 `complete the level when no dot is left` | Plus aucune pastille sur le plateau. |
| 13 | 🔴 `end the game when pacman runs into a monster` | `isOver` n'existe pas. |
| 14 | 🟢 `end the game on a monster` | Un monstre devant : partie terminée, et plus rien ne bouge ensuite. |
| 15 | 🔵 `read the tick as one decision per kind of cell` | Un `when` sur la case devant : monstre, mur, ou déplacement. |

## Périmètre

Traités : direction, ticks, rotation, pastilles, bords, murs, score, fin de niveau, monstres immobiles. Non traités, comme l'énoncé l'admet (« vous ne pourrez probablement pas tout faire en une soirée ») : le déplacement des monstres, l'enchaînement des niveaux et l'animation de la bouche.

## Ce que j'en retiens

Un plateau en texte, avant et après un tick, fait des tests qui se lisent comme des dessins. La fonction `ahead`, extraite au moment où la rotation en a eu besoin, a évité de dupliquer le calcul des bords.

## Lancer les tests

```bash
./gradlew :katas:pac-man:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/pac-man   # l'historique TDD
```
