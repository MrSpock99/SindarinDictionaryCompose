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