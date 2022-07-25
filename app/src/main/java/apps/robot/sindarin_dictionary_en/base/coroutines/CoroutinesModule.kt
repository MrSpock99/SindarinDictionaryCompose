package apps.robot.sindarin_dictionary_en.base

import apps.robot.sindarin_dictionary_en.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.base.coroutines.JvmAppDispatchers
import org.koin.dsl.module

fun coroutinesModule() = module {
    single<AppDispatchers> { JvmAppDispatchers() }
}