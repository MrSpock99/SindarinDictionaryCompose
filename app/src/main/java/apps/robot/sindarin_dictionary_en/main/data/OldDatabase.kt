package apps.robot.sindarin_dictionary_en.main.data

import androidx.room.Database
import androidx.room.RoomDatabase
import apps.robot.favorites.api.data.OldFavoritesDao
import apps.robot.favorites.api.domain.OldFavoriteModel
import apps.robot.sindarin_dictionary_en.main.data.OldDatabase.Companion.VERSION

@Database(
    entities = [
        OldFavoriteModel::class,
    ],
    version = VERSION
)
abstract class OldDatabase : RoomDatabase() {
    abstract fun oldFavoritesDao(): OldFavoritesDao

    companion object {
        const val VERSION = 2
    }
}