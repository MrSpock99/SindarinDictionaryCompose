package apps.robot.grammar.impl.pronounce.domain

data class PronounceItem(val id: String, val sound: String, val example: String, val sindarinExample: String) {
    constructor(): this("","","","")
}