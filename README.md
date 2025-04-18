## Tester la partie Back

### 1. Démarrer docker-compose

- Ouvrir **Docker Desktop** si l'on veut voir les images et conteneurs qui seront créés (étape non nécessaire)
- Se placer à la racine du projet, là où se trouve le fichier `docker-compose.yml`
- Exécuter la commande suivante :

```bash
docker-compose up
```

Cela va :
- Construire les images si nécessaire 
- Créer les conteneurs 
- Lancer les services Docker

Dans le navigateur, se rendre sur http://localhost:1080 pour accéder au serveur MailDev.

Dans Postman, tester la requête POST http://localhost:8088/api/v1/auth/register avec le body ci-dessous :
```
{
    "firstname" : "Freddy",
    "lastname" : "Guen",
    "email" : "guen@yahoo.fr",
    "password" : "password123"
}
```

### 2. Tester l'activation de compte

Pour tester le service d'activation de compte :
- Ouvrir postman et renseigner la requête GET http://localhost:8088/api/v1/auth/activate-account
- Dans l'onglet "params", ajouter le paramètre `token` avec comme valeur le code envoyé dans MailDev. L'url 
devient : http://localhost:8088/api/v1/auth/activate-account?token=708999 (avec 708999 le code). Tester !
    - Si le code a été envoyé il y a plus de 15 minutes, une exception est levée avec le message "Code d'activation expiré. Un nouveau code vient d'être envoyé."
        - En retournant dans MailDev, on voit un nouveau mail avec un nouveau code. Remplacer dans les params l'ancien code par le nouveau et tester.
          En vérifiant la table Token en base, on constate que la colonne `enabled` contient maintenant la valeur 'true'

### 3. Tester la connexion au compte

Pour tester le service login :
- Dans postman, renseigner la requête POST http://localhost:8088/api/v1/auth/authenticate avec le body ci-dessous : 
```
{
  "email" : "guen@yahoo.fr",
  "password" : "password123"
}
```
On obtient en réponse le token généré.