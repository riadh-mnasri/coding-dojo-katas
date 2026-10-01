# Leap Years (années bissextiles)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/LeapYears](https://codingdojo.org/kata/LeapYears/)

## Le kata

Dire si une année est bissextile selon le calendrier grégorien :

1. divisible par 400 : bissextile (2000) ;
2. divisible par 100 mais pas par 400 : non bissextile (1900, 2100) ;
3. divisible par 4 mais pas par 100 : bissextile (2012) ;
4. non divisible par 4 : non bissextile (2019).

Extension : les années divisibles par 4000 ne sont pas bissextiles.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

L'énoncé liste les critères du plus spécifique au plus général. J'ai pris l'ordre inverse, du cas général vers ses exceptions : chaque test ajoute alors une seule exception.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `reject years not divisible by 4` | Compilation impossible : `LeapYear` n'existe pas. |
| 2 | 🟢 `no year is a leap year yet` | `return false` suffit pour 2017, 2018, 2019. |
| 3 | 🔴 `accept years divisible by 4` | 2008, 2012, 2016 : attendu `true`, obtenu `false`. |
| 4 | 🟢 `years divisible by 4 are leap years` | `year % 4 == 0`. |
| 5 | 🔴 `reject centuries` | 1700, 1800, 1900, 2100 : attendu `false`, obtenu `true`. |
| 6 | 🟢 `centuries are not leap years` | `&& year % 100 != 0`. |
| 7 | 🔴 `accept years divisible by 400` | 1600, 2000, 2400 : attendu `true`, obtenu `false`. |
| 8 | 🟢 `years divisible by 400 are leap years` | `year % 400 == 0 \|\| (...)` : ça passe, mais l'expression devient difficile à lire. |
| 9 | 🔵 `read the rules in priority order` | Un `when` où chaque branche est une règle, de la plus prioritaire à la plus générale, avec une extension `isDivisibleBy`. |
| 10 | 🔴 `reject years divisible by 4000 with the extra rule` | Le paramètre `withFourThousandYearRule` n'existe pas. |
| 11 | 🟢 `add the optional 4000-year rule` | Une branche en tête du `when`, active seulement si l'option est demandée : le comportement grégorien reste celui par défaut. |
| 12 | 📌 `keep other leap years under the 4000-year rule` | 2000, 2024, 4004 restent bissextiles avec l'option. |

## Solution

```kotlin
fun isLeap(year: Int): Boolean = when {
    withFourThousandYearRule && year.isDivisibleBy(4000) -> false
    year.isDivisibleBy(400) -> true
    year.isDivisibleBy(100) -> false
    else -> year.isDivisibleBy(4)
}
```

## Ce que j'en retiens

Aller du général aux exceptions donne des étapes vertes minuscules, mais une expression booléenne qui grossit à chaque test. Le refactoring de l'étape 9 retrouve la lisibilité de l'énoncé : une règle par ligne, dans l'ordre de priorité.

## Lancer les tests

```bash
./gradlew :katas:leap-years:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/leap-years   # l'historique TDD
```
