package apps.robot.grammar.impl.pronounce.domain

interface GrammarRepository {
    suspend fun getPronunciation(): List<PronounceItem>
}