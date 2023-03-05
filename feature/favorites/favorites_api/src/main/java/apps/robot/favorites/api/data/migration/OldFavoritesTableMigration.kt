package apps.robot.favorites.api.data.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class OldFavoritesTableMigration(oldVersion: Int, newVersion: Int): Migration(oldVersion, newVersion) {

    override fun migrate(database: SupportSQLiteDatabase) {
        // noting to do
    }
}