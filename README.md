# Gestionnaire de formation

## 1. Base de données

- Création des tables principales
    - `utilisateur` (avec `id` auto-incrément, `matricule`, `nom`, `prenom`, `adresse_mail`, `adresse_postal`, `code_postal`, `ville`, `avatar`, `mot_de_passe`, `statut`)
    - `role` (`id`, `libelle`, `statut`)
    - `formation` (`id`, `libelle`, `numero_offre`, `date_debut`, `date_fin`, `date_debut_pe`, `date_fin_pe`, `statut`)
    - `centre`, `salle`, `module`, `sequence`, `ressource`, `document`, `type`

- Création des tables de liaison (FK uniquement)
    - `utilisateur_role`, `utilisateur_formation_fonction`
    - `formation_centre`, `formation_module`, `module_sequence`
    - `formation_ressource`, `formation_document`

- Peuplement **fake data** pour tester toutes les liaisons sans erreurs de clé étrangère

## 2. Back-end (Spring Boot + JPA/Hibernate)

- **Entities** modélisées pour chaque table (annotation `@Entity`, relations `@ManyToMany`, `@OneToMany`, etc.)
- **Repositories** `JpaRepository` CRUD-ready
- **Services** pour orchestrer la logique métier
- **Controllers** exposant les endpoints REST
    - Utilisateurs : `GET/POST/PUT/DELETE /utilisateurs` + désactivation
    - Rôles : `GET/POST/PUT/DELETE /roles`
    - Formations, Modules, Séquences, Centres, Salles, Ressources, Documents, Types, et leurs liaisons via URLs RESTful

## 3. DTOs

- Introduction de `UtilisateurDTO` pour :
    - Exclure les champs sensibles (mot de passe)
    - Empêcher les boucles infinies JSON
    - Ajouter le champ `statut` + format « roles: role1, role2 »

- À prévoir : DTOs similaires pour **Centre**, **Salle**, **Formation**, etc.

## 4. Tests / Postman

- Jeu de commandes Postman fourni pour tester chaque entité et chaque liaison
- Vérification des réponses JSON, correction des mappings et suppression des boucles

## 5. Prochaines étapes

- **DTOs** pour les autres entités
- **Sécurité** avec Spring Security (login JWT, contrôle d’accès par rôle)
- **Recherche full-text** sur le catalogue (filtre via paramètre `?search=`)
- **Génération de PDF** (iText ou IronPDF) et envoi de questionnaires

## 6. Reste à faire
### Ajouts et améliorations / DevOps
- ajouter des tests
- ajouter des logs ('
- ajouter un front :
    -   interface graphique (Angular, React, Vue, JavaFX, etc.)
- ajouter un docker :
    -   empaqueter ton appli dans un conteneur pour la rendre portable (docker-compose, dockerfile, docker swarm, kubernetes, etc.)
- ajouter un CI/CD :
  - automatiser les tests, les builds et les déploiements (ex : GitHub Actions, GitLab CI). 
  CI = intégration continue / CD = déploiement continu
- ajouter un swagger :
  - documenter ton API REST (ex : SpringDoc, Swagger UI)
- ajouter un cache :
  - améliorer les performances de ton appli (ex : Redis, Memcached)
- ajouter un CDN :
  - améliorer la vitesse de chargement de ton appli (ex : Cloudflare, AWS CloudFront)
- ajouter un WAF :
  - protéger ton appli contre les attaques (ex : AWS WAF, Cloudflare WAF)

### Ajouts et améliorations / Sécurité
- ajouter un SSO ou SAML ou OAuth2 ou OpenID Connect :
  - authentification unique pour plusieurs applications (ex : Google, Facebook, etc.)
- ajouter un JWT 
  - authentification par token (ex : JSON Web Token)







