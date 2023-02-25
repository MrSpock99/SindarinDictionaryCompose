include(
    // App
    ":app",
    ":base-ui",
    ":app_sindarin"
)

fun includeModuleFromDir(dirPath: String, vararg modules: String) {
    modules.forEach { name ->
        include(":${name}")
        project(":${name}").projectDir = File(rootDir, "$dirPath/${name}")
    }
}

val dictionaryDirPath = "/feature/dictionary"
includeModuleFromDir(dictionaryDirPath, "dictionary_api", "dictionary_impl")

val favoritesDirPath = "/feature/favorites"
includeModuleFromDir(favoritesDirPath, "favorites_api", "favorites_impl")
