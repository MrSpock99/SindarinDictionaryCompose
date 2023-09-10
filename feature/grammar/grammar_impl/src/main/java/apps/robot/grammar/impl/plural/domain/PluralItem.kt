package apps.robot.grammar.impl.plural.domain

data class PluralItem(val id: String, val vowel: String, val beginning: String, val end: String, val examples: String) {
    constructor() : this("", "", "", "", "")
}