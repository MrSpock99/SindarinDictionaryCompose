package apps.robot.sindarin_dictionary_en.base_ui.presentation

import apps.robot.sindarin_dictionary_en.base_ui.BuildConfig

fun isFree(): Boolean {
    return BuildConfig.FLAVOR == "EngFree"
}