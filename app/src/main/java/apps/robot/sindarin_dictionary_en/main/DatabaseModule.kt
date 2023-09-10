package apps.robot.sindarin_dictionary_en.main

import androidx.room.Room
import androidx.room.migration.Migration
import apps.robot.favorites.api.data.migration.OldFavoritesTableMigration
import apps.robot.sindarin_dictionary_en.main.data.AppDatabase
import apps.robot.sindarin_dictionary_en.main.data.OldDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun databaseModule() = module {

    factory<Migration> { OldFavoritesTableMigration(1,2) }

    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "sindarin")
            .build()
    }

    single {
        Room.databaseBuilder(androidApplication(), OldDatabase::class.java, "FAVORITES")
            .apply {
                val migrations = getAll<Migration>()
                migrations.forEach { addMigrations(it) }
            }
            .build()
    }
    factory { get<OldDatabase>().oldFavoritesDao() }

    factory { get<AppDatabase>().elfToEngDao() }
    factory { get<AppDatabase>().engToElfDao() }
    factory { get<AppDatabase>().favoritesDao() }
    factory { get<AppDatabase>().pronounceDao() }
}