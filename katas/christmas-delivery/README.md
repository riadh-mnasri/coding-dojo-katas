# Christmas Delivery

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/christmas-delivery](https://codingdojo.org/kata/christmas-delivery/)

## Le kata

Le Père Noël doit passer d'un seul lutin à plusieurs pour charger son traîneau :

1. **Système actuel** : une machine à jouets donne un cadeau à un lutin, qui met un moment à le charger puis redevient disponible.
2. **Plusieurs lutins** : Mère Noël reçoit les cadeaux des machines et les confie aux lutins libres ; s'il n'y en a aucun, elle garde les cadeaux.
3. **Familles** : les cadeaux d'une même famille doivent si possible partir ensemble, mais sans laisser de lutin inoccupé (ils coûtent cher).
4. **Familles pas sages** : le Père Noël peut faire jeter leurs cadeaux.

Le traîneau est fourni sous forme d'interface : `SantasSleigh.pack(present)`.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Comme pour Pizza Maker, chaque livraison est une **coroutine** et les tests utilisent le **temps virtuel** : un lutin met 10 secondes à charger un cadeau, et les tests avancent l'horloge sans attendre. Le traîneau de test enregistre les cadeaux chargés.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `story 1, an elf packs a present after a while` | À 9,999 s le traîneau est vide, à 10 s il contient le cadeau. Compilation impossible. |
| 2 | 🟢 `story 1, let an elf pack a present after a while` | `delay(packingTime)` puis `sleigh.pack(present)`. |
| 3 | 🔴 `story 2, Mrs Claus hands presents to free elves in parallel` | `MrsClaus` n'existe pas ; deux lutins doivent charger deux cadeaux en même temps. |
| 4 | 🟢 `story 2, dispatch presents to free elves` | Une file de cadeaux et une file de lutins libres ; un lutin qui a fini redevient libre et relance la répartition. |
| 5 | 📌 `story 2, hold presents until an elf is free` | Un seul lutin : le second cadeau attend son retour. |
| 6 | 🔴 `story 3, finish a family before starting another` | Avec un lutin et l'ordre Smith, Jones, Smith, le Jones passait avant le second Smith. |
| 7 | 🟢 `story 3, prefer families already started` | Un lutin libre prend d'abord un cadeau d'une famille déjà commencée, sinon le plus ancien. |
| 8 | 📌 `story 3, keep elves busy rather than idle` | Deux lutins, deux familles : ils travaillent quand même en parallèle, comme le demande l'énoncé. |
| 9 | 🔴 `story 4, discard the presents of naughty families` | `cancel` n'existe pas. |
| 10 | 🟢 `story 4, discard queued and future presents of naughty families` | Les cadeaux en attente sont jetés, et ceux qui arrivent ensuite aussi. |

## Choix et limites

- Les machines à jouets ne sont que des appelants de `MrsClaus.receive` : plusieurs machines reviennent à plusieurs appels.
- L'état de Mère Noël (files, familles) est modifié depuis les coroutines des lutins. C'est sûr sur un dispatcher à un seul fil (celui des tests, ou un contexte confiné) ; sur un pool de threads, il faudrait le protéger (`Mutex` ou acteur à base de `Channel`).
- Un cadeau déjà confié à un lutin n'est pas rappelé par l'annulation : il est déjà en route vers le traîneau.

## Ce que j'en retiens

La stratégie par famille tient en une fonction (`nextPresent`) parce que la répartition était déjà isolée : ajouter une règle de priorité n'a pas touché à la mécanique de concurrence.

## Lancer les tests

```bash
./gradlew :katas:christmas-delivery:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/christmas-delivery   # l'historique TDD
```
