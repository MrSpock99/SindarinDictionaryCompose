package apps.robot.favorites.api.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import apps.robot.favorites.api.domain.FavoriteModel
import apps.robot.favorites.api.domain.FavoriteModel.Companion.FAVORITES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FavoritesDao {
    @Query("SELECT * FROM $FAVORITES_TABLE")
    abstract fun getFavoriteWordsAsFlow(): Flow<List<FavoriteModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(item: FavoriteModel)

    @Delete
    abstract suspend fun remove(favoriteModel: FavoriteModel)

    @Query("SELECT * FROM $FAVORITES_TABLE where text = :text AND translation = :translation")
    abstract suspend fun get(text: String, translation: String): FavoriteModel?

    @Query("SELECT * FROM $FAVORITES_TABLE")
    abstract suspend fun getAll(): List<FavoriteModel?>
}