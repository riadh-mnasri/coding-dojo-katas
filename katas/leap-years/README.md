# Leap Years (années bissextiles)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/LeapYears](https://codingdojo.org/kata/LeapYears/)

## Le kata

Dire si une année est bissextile selon le calendrier grégorien :

1. divisible par 400 : bissextile (2000) ;
2. divisible par 100 mais pas par 400 : non bissextile (1900, 2100) ;
3. divisible par 4 mais pas par 100 : bissextile (2012) ;
4. non divisible par 4 : non bissextile (2019).

Extension (story 2) : les années divisibles par 4000 ne sont pas bissextiles.

## Démarche TDD

L'ordre des critères d'acceptation de l'énoncé n'est pas le meilleur ordre de tests. J'ai pris le cas le plus général d'abord :

1. 2017, 2018, 2019 → `false` : `return false` suffit.
2. 2008, 2012, 2016 → `true` : `year % 4 == 0`.
3. 1700, 1800, 1900, 2100 → `false` : exception à la règle précédente, `% 100` avant `% 4`.
4. 1600, 2000, 2400 → `true` : exception de l'exception, `% 400` en tête.
5. *Refactoring* : la cascade de `if` devient un `when` qui se lit dans l'ordre de priorité, avec une petite extension `isDivisibleBy`.
6. Story 2 : la règle des 4000 ans est une option du constructeur, désactivée par défaut, pour ne pas casser le comportement grégorien.

## Solution

```kotlin
fun isLeap(year: Int): Boolean = when {
    withMillenniumRule && year.isDivisibleBy(4000) -> false
    year.isDivisibleBy(400) -> true
    year.isDivisibleBy(100) -> false
    else -> year.isDivisibleBy(4)
}
```

Chaque branche du `when` correspond à un critère d'acceptation, du plus spécifique au plus général.

## Ce que j'en retiens

Choisir l'ordre des tests du général vers les exceptions fait apparaître naturellement l'ordre des branches dans le code.

## Lancer les tests

```bash
./gradlew :katas:leap-years:test
```
