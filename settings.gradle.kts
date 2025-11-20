pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TasksOfAffirmation"
include(":app")
include(":core-models")
include(":core-data")
include(":task-api")
include(":task-api-test")
//include(":lint-checks")
