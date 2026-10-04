# Social Network

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/social-network](https://codingdojo.org/kata/social-network/)

## Le kata

Un réseau social très léger, découpé en *epics*, à l'origine pour pratiquer l'**example mapping** :

- **Publier** : Thomas publie un message ;
- **Lire** : Alice voit tous les messages de Thomas ;
- **Suivre** : Charlie s'abonne à Thomas et Alice et voit un mur agrégé ;
- **Mentionner** : Alice mentionne Charlie avec `@Charlie` ;
- **Lier** : Thomas partage un lien vers un message ;
- **Messages privés** : Alice écrit en privé à Thomas.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Un test par epic, nommé d'après elle, avec l'exemple de l'énoncé : c'est l'example mapping traduit en tests. L'horloge est injectée (elle avance d'une minute à chaque message), pour que l'ordre chronologique soit déterministe.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `let Thomas publish a message` | Compilation impossible. |
| 2 | 🟢 `publish messages` | Une liste de `Post`. |
| 3 | 🔴 `let Alice read Thomas's messages, newest first` | L'ordre est chronologique au lieu d'être antéchronologique. |
| 4 | 🟢 `show a timeline newest first` | Tri par date décroissante. |
| 5 | 🔴 `aggregate the followed users on Charlie's wall` | `follow` et `wall` n'existent pas. |
| 6 | 🟢 `follow users and aggregate them on a wall` | Le mur réunit ses propres messages et ceux des personnes suivies. |
| 7 | 🔴 `find the messages mentioning Charlie` | `mentionsOf` n'existe pas ; le test vérifie aussi que « Charlie » sans `@` et « @Charlotte » ne comptent pas. |
| 8 | 🟢 `find mentions written as @name` | Une expression régulière sur des mots entiers. |
| 9 | 🔴 `share a link to a message` | `linkTo` et `open` n'existent pas. |
| 10 | 🟢 `link to a message and open the link` | Un lien `https://social.example/<auteur>/messages/<id>` qui ramène au message. **Écart** : j'y ai écrit aussi le rejet d'un lien inconnu, sans test rouge. |
| 11 | 🔴 `send a private message` | `sendDirectMessage` et `inbox` n'existent pas. |
| 12 | 🟢 `keep private messages out of timelines` | Les messages privés sont stockés à part et n'apparaissent que dans la boîte du destinataire. |
| 13 | 📌 `cover unknown links, rejected without a red test` | Rattrapage de l'écart de l'étape 10. |

## Ce que j'en retiens

Avec une horloge injectée, « du plus récent au plus ancien » devient testable sans `Thread.sleep` ni dates approximatives. Et nommer chaque test d'après son epic donne une documentation que l'on peut relire avec le métier.

## Lancer les tests

```bash
./gradlew :katas:social-network:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/social-network   # l'historique TDD
```
