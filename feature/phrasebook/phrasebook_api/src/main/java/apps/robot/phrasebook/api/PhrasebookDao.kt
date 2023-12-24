package apps.robot.phrasebook.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PhrasebookDao {
    @Query("SELECT * FROM ${CategoryItem.TABLE_NAME} where category=:categoryName")
    abstract fun getCategoryItemsAsFlow(categoryName: String): Flow<List<CategoryItem>>

    @Query("SELECT * FROM ${CategoryItem.TABLE_NAME} where category=:categoryName")
    abstract fun getCategoryItems(categoryName: String): List<CategoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(list: List<CategoryItem>)

    @Query("SELECT COUNT(*) FROM ${CategoryItem.TABLE_NAME}")
    abstract suspend fun getCategoryItemsSize(): Int
}