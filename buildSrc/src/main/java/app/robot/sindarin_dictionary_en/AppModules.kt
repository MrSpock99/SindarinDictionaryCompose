package app.robot.sindarin_dictionary_en

object AppModules {
    const val baseUi = ":base-ui"
    const val app = ":app"

    object Dictionary {
        const val api = ":dictionary_api"
        const val impl = ":dictionary_impl"
    }

    object Favorites {
        const val api = ":favorites_api"
        const val impl = ":favorites_impl"
    }

    object Phrasebook {
        const val api = ":phrasebook_api"
        const val impl = ":phrasebook_impl"
    }
}