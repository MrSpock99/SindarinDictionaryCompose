package apps.robot.sindarin_dictionary_en.main.data

import androidx.room.Database
import androidx.room.RoomDatabase
import apps.robot.favorites.api.data.FavoritesDao
import apps.robot.favorites.api.domain.FavoriteModel
import apps.robot.grammar.api.PronounceDao
import apps.robot.grammar.api.PronounceItem
import apps.robot.phrasebook.api.CategoryItem
import apps.robot.phrasebook.api.PhrasebookDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.ElfToEngDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.EngToElfDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.main.data.AppDatabase.Companion.VERSION

@Database(
    entities = [
        EngToElfWordEntity::class,
        ElfToEngWordEntity::class,
        FavoriteModel::class,
        PronounceItem::class,
        CategoryItem::class,
    ],
    version = VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun engToElfDao(): EngToElfDao
    abstract fun elfToEngDao(): ElfToEngDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun pronounceDao(): PronounceDao
    abstract fun phrasebookDao(): PhrasebookDao

    companion object {
        const val VERSION = 1
    }
}