package apps.robot.sindarin_dictionary_en.main

import androidx.room.Room
import apps.robot.sindarin_dictionary_en.main.data.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun databaseModule() = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "sindarin")
            .build()
    }

    factory { get<AppDatabase>().elfToEngDao() }
    factory { get<AppDatabase>().engToElfDao() }
}