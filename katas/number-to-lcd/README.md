# Number to LCD

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/NumberToLCD](https://codingdojo.org/kata/NumberToLCD/)

## Le kata

**Partie 1** : afficher un nombre en chiffres façon LCD, sur 3 lignes :

```
    _  _     _  _  _  _  _  _
  | _| _||_||_ |_   ||_||_|| |
  ||_  _|  | _||_|  ||_| _||_|
```

**Partie 2** (à ne lire qu'après la partie 1) : rendre la largeur et la hauteur des chiffres variables. Pour une largeur 3 et une hauteur 2, le 2 devient :

```
 ___
    |
    |
 ___
|
|
 ___
```

## Une incohérence de l'énoncé

À taille 1, les deux formats ne coïncident pas : la partie 1 tient sur **3 lignes** (la barre du milieu et celle du bas partagent la ligne des segments verticaux), alors que l'exemple de la partie 2 donne à chaque barre **sa propre ligne** (2 × hauteur + 3 lignes, donc 5 lignes à taille 1). J'ai gardé les deux, en respectant chaque exemple : `render(n)` pour la forme compacte, `render(n, width, height)` pour la forme étirée.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `draw the digit 1` | Compilation impossible. |
| 2 | 🟢 `draw a hard-coded 1` | Les trois lignes en dur. |
| 3 | 🔴 `draw the digit 2` | Le 1 en dur est démasqué. |
| 4 | 🟢 `look digits up in a glyph table` | Une table `chiffre → 3 lignes`. |
| 5 | 🔴 `draw a number with several digits` | `1234567890` absent de la table. |
| 6 | 🟢 `draw every digit and put them side by side` | Les dix glyphes, et pour chaque ligne la concaténation des glyphes. |
| 7 | 🔴 `stretch a 2 to width 3 and height 2` | La surcharge `render(n, width, height)` n'existe pas. |
| 8 | 🟢 `stretch digits from their segments` | Un dessin en dur ne s'étire pas : chaque chiffre devient un **ensemble de segments allumés**, et chaque ligne se calcule à partir de ces segments. |
| ⛔ | refactor refusé | Mon `typealias` était déclaré dans l'objet, ce que Kotlin interdit : compilation impossible, commit refusé. |
| 9 | 🔵 `derive the compact form from the segments too` | La forme compacte de la partie 1 utilise elle aussi les segments : la table de glyphes disparaît, il ne reste qu'une seule description des chiffres. |
| 10 | 📌 `stretch several digits` | `10` en largeur 2. |
| 11 | 🔴 `reject negative numbers and empty sizes` | Un nombre négatif lève une `NoSuchElementException` sur le `-`. |
| 12 | 🟢 `validate the number and the sizes` | Deux `require`. |

## Solution

```
 _a_
b   c
 _d_
e   f
 _g_
```

Chaque chiffre est l'ensemble des segments allumés (`2` = `a c d e g`). Une forme d'affichage est une liste de « lignes », chacune étant une fonction des segments d'un chiffre ; on la répète pour tous les chiffres du nombre.

## Ce que j'en retiens

L'énoncé demande de ne pas lire la partie 2 trop tôt. La table de dessins de la partie 1 a dû être remplacée à la partie 2, et c'est le but : s'adapter à une exigence imprévue. Le refactoring final a supprimé le doublon entre les deux formes.

## Lancer les tests

```bash
./gradlew :katas:number-to-lcd:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/number-to-lcd   # l'historique TDD
```
