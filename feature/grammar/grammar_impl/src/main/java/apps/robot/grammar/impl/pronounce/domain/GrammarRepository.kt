package apps.robot.grammar.impl.pronounce.domain

import apps.robot.grammar.api.PronounceItem
import apps.robot.grammar.impl.plural.domain.PluralItem
import kotlinx.coroutines.flow.Flow

interface GrammarRepository {
    suspend fun loadPronounceItems()
    suspend fun getPronunciationAsFlow(): Flow<List<PronounceItem>>
    suspend fun getPronounceItemSize(): Int

    suspend fun loadPluralsItems()

    fun getPluralItemsAsFlow(): Flow<List<PluralItem>>
}