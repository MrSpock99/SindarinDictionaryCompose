package apps.robot.sindarin_dictionary_en.main.data

import androidx.room.Database
import androidx.room.RoomDatabase
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.ElfToEngDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.EngToElfDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.main.data.AppDatabase.Companion.VERSION

@Database(
    entities = [
        EngToElfWordEntity::class,
        ElfToEngWordEntity::class],
    version = VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun engToElfDao(): EngToElfDao
    abstract fun elfToEngDao(): ElfToEngDao

    companion object {
        const val VERSION = 1
    }
}