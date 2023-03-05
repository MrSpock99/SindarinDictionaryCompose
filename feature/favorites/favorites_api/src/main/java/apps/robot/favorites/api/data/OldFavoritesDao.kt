package apps.robot.favorites.api.data

import androidx.room.Dao
import androidx.room.Query
import apps.robot.favorites.api.domain.OldFavoriteModel

@Dao
abstract class OldFavoritesDao {

    @Query("SELECT * FROM FAVORITES")
    abstract suspend fun getAll(): List<OldFavoriteModel>

    @Query("DELETE FROM FAVORITES")
    abstract suspend fun deleteAll()
}