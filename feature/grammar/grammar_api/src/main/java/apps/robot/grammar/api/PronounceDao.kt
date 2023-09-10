package apps.robot.grammar.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
abstract class PronounceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addItems(items: List<PronounceItem>)

    @Query("SELECT * FROM '$PRONOUNCE_TABLE'")
    abstract fun getItemsAsFlow(): Flow<List<PronounceItem>>

    @Query("SELECT COUNT(*) FROM '$PRONOUNCE_TABLE'")
    abstract fun getItemsSize(): Int

    companion object {
        const val PRONOUNCE_TABLE = "pronounce"
    }
}