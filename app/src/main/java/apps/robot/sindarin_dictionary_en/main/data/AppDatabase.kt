package apps.robot.sindarin_dictionary_en.main.data

import androidx.room.Database
import androidx.room.RoomDatabase
import apps.robot.favorites.api.data.FavoritesDao
import apps.robot.favorites.api.domain.FavoriteModel
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.ElfToEngDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.EngToElfDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.main.data.AppDatabase.Companion.VERSION

@Database(
    entities = [
        EngToElfWordEntity::class,
        ElfToEngWordEntity::class,
        FavoriteModel::class
    ],
    version = VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun engToElfDao(): EngToElfDao
    abstract fun elfToEngDao(): ElfToEngDao
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        const val VERSION = 1
    }
}