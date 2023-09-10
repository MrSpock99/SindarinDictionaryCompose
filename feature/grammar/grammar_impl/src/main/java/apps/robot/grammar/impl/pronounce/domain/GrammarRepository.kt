package apps.robot.grammar.impl.pronounce.domain

import apps.robot.grammar.api.PronounceItem
import kotlinx.coroutines.flow.Flow

interface GrammarRepository {
    suspend fun loadPronounceItems()
    suspend fun getPronunciationAsFlow(): Flow<List<PronounceItem>>
}