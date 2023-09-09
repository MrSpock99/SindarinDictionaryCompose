package apps.robot.sindarin_dictionary_en.app_sindarin

import android.app.Application
import android.content.Context
import apps.robot.favorites.impl.favoritesModules
import apps.robot.grammar.impl.grammarModules
import apps.robot.phrasebook.impl.phrasebookModules
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.appModule
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.coroutinesModule
import apps.robot.sindarin_dictionary_en.base_ui.presentation.clipboard.clipboardModule
import apps.robot.sindarin_dictionary_en.dictionary.dictionaryModules
import apps.robot.sindarin_dictionary_en.main.databaseModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class AppDelegate : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initKoinDi()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        val initializers = getKoin().getAll<BaseAppInitializer>()
            .sortedByDescending {
                it.getPriority()
            }
        initializers.forEach {
            it.onAppStartInit()
        }
    }

    private fun initKoinDi() {
        startKoin {
            androidContext(this@AppDelegate)
            modules(dictionaryModules())
            modules(clipboardModule())
            modules(appModule())
            modules(coroutinesModule())
            modules(databaseModule())
            modules(favoritesModules())
            modules(phrasebookModules())
            modules(grammarModules())
        }
    }
}

