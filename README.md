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

Dans le navigateur, se rendre sur http://localhost:1080 pour accéder au serveur MailDev
Dans Postman, tester la requête POST http://localhost:8088/api/v1/auth/register avec le body ci-dessous :
```
{
    "firstname" : "Freddy",
    "lastname" : "Guen",
    "email" : "guenengafo@yahoo.fr",
    "password" : "password123"
}
```
