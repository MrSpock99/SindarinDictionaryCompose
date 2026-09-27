package apps.robot.grammar.impl.pronounce.data

import apps.robot.grammar.api.PronounceItem

internal interface PronounceDataSource {
    suspend fun loadItems(): List<PronounceItem>
}
