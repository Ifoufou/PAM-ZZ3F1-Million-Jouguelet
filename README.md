# Projet PAM : application liste de jeux
Le projet développé est une application affichant une liste de jeux et la description détaillée de ces jeux. Les informations sont récupérées via un fichier JSON personnalisé, disponible en ligne ("https://jsonkeeper.com/b/4MQB").
Les informations relatives à un jeu seront stockées dans un objet de type `TouhouGame`.

Les images sont téléchargées en ligne dans l'application :
    https://perso.isima.fr/~tejouguele/Th06cover.jpg
    https://perso.isima.fr/~tejouguele/Th07cover.jpg
    ...

## Écrans
Un premier écran, représenté par le fragment ``MainFragment``, affiche la liste des jeux récupérée via le fichier JSON. La récupération des données est gérée de façon dynamique via une `LiveData`. La liste un `RecyclerView` dont les item sont alimenté via des requêtes effectuée sur un objet ``Adapter`` (``MyAdapter``). La classe `MainFragment` implémente l'interface `OnGameListener` disposant de callback qui seront appelé lorsque l'utilisateur cliquera sur un item du ``RecyclerView``

Le clique sur un item affiche le deuxième écran. Ce changement s'effectue à par le biais d'un composant `Navigation`, défini dans `app/res/navigation/my_nav.xml`.

![](https://i.imgur.com/fhxvukW.png)
> Premier écran (liste des jeux)

---

Le deuxième écran, représenté par le fragment ``DesciptionFragment``, affiche la description du jeu sélectionné sur le premier écran. Les informations du jeu sélectionné sont transmise par sérialisation (de l'objet ``TouhouGame`` correspondant) via le `NavController`. Il est nécessaire d'utiliser le bouton de retour du téléphone pour revenir sur le premier écran.

Notons également que l'application n'adapte pas son affichage lorsque l'écran est retourné.

![](https://i.imgur.com/fIKBps8.png)
> Deuxième écran (description d'un jeu)
