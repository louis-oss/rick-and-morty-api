This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.

# Rick & Morty Android App - Architecture du projet

## Architecture utilisée

Le projet suit une architecture de type Clean Architecture, composée de trois couches principales :

1. **Domain Layer (couche métier)** :
  - Contient les entités du domaine comme `Character`, `Episode`, `Location`, etc.
  - Définit les interfaces des `Repository`
  - Ne dépend d'aucune autre couche

2. **Data Layer (couche données)** :
  - Contient les implémentations des `Repository` définis dans la couche domaine
  - Séparée en deux sous-parties :
    - `local` : gestion des données locales via Room
    - `remote` : appels API distants via Ktor
  - Effectue les transformations entre les modèles de données (DTO) et les modèles métier

3. **Presentation Layer (couche présentation)** :
  - Interface utilisateur écrite avec Jetpack Compose
  - Vue déclarative qui communique avec les ViewModels
  - Gère les états de l’interface (`State`) et les actions utilisateur (`Action`)

## Technologies utilisées

- Kotlin Multiplatform
- Jetpack Compose
- Room (base de données locale)
- Ktor (communication réseau)
- Koin (injection de dépendances)

## Structure du projet

- `domain/`
  - `models/` : définitions des entités métier
  - `repository/` : interfaces des repositories
- `data/`
  - `local/` : DAO Room et entités de base de données
  - `remote/` : appels API via Ktor
  - `repository/impl/` : implémentations concrètes des repositories
- `ui/`
  - `screens/` : chaque écran avec ses éléments propres (state, action, viewmodel, composable)
  - `core/` : navigation, thème, composants réutilisables

## Utilisation de Managers

Un manager est utilisé pour encapsuler une fonctionnalité hardware :

- `SoundPlayer` : interface définie dans le domaine
- `AndroidSoundPlayer` : implémentation pour Android dans le module `androidMain`
  - Utilise `MediaPlayer` pour jouer un son à l’interaction utilisateur

Ce composant est injecté via Koin pour être utilisé dans la couche présentation.

## Bonnes pratiques appliquées

- Séparation claire des responsabilités
- Code organisé et structuré
- Nommage explicite des fichiers et classes
- Utilisation de Koin pour faciliter les tests