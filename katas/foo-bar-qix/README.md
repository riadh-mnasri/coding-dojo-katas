# FooBarQix

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/FooBarQix](https://codingdojo.org/kata/FooBarQix/)

## Le kata

`compute(String): String` applique, dans cet ordre :

- divisible par 3, 5, 7 → `Foo`, `Bar`, `Qix` ;
- puis pour chaque chiffre 3, 5, 7 du nombre, dans l'ordre des chiffres → `Foo`, `Bar`, `Qix`.

**Étape 2** : chaque 0 est remplacé par `*` (`101 → 1*1`, `105 → FooBarQix*Bar`).

## Démarche TDD

1. `1`, `2` → inchangés.
2. `6 → Foo`, `10 → Bar` : règle de divisibilité, d'abord avec deux `if`, puis une table `3→Foo, 5→Bar, 7→Qix` dès que 7 arrive.
3. `3 → FooFoo` : la même table sert pour les chiffres. Les deux parties (diviseurs puis chiffres) sont concaténées.
4. `53 → BarFoo`, `33 → FooFooFoo` : on parcourt les chiffres dans l'ordre, ce qui était déjà le cas.
5. **Étape 2** : `101 → 1*1`. Piège : quand aucun mot n'est produit, on rend le nombre avec ses `*`, mais quand il y a des mots, les `*` s'intercalent avec eux (`303 → FooFoo*Foo`). Le test « a-t-on produit un mot ? » devient « le résultat contient-il autre chose que des `*` ? ».
6. Conserver les tests de l'étape 1 m'a fait garder les deux comportements (`step1` et `step2`), car `10` vaut `Bar` à l'étape 1 et `Bar*` à l'étape 2.

## Solution

- Une seule table ordonnée (`linkedMapOf`) sert pour les diviseurs et pour les chiffres.
- Le chiffre `0` produit `*` uniquement en mode étape 2 (`traceZeros`).
- Le repli (aucun mot) remplace les `0` par `*` en étape 2.

## Ce que j'en retiens

L'étape 2 casse un exemple de l'étape 1 (`10`). Plutôt que de modifier un test vert, j'ai explicité les deux versions de la règle métier : c'est souvent ce qui arrive avec une vraie demande d'évolution.

## Lancer les tests

```bash
./gradlew :katas:foo-bar-qix:test
```
