// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "coding-dojo-katas"

// Chaque dossier de katas/ est un module Gradle autonome.
file("katas")
    .listFiles { file -> file.isDirectory && file.resolve("src").isDirectory }
    ?.sortedBy { it.name }
    ?.forEach { include(":katas:${it.name}") }
