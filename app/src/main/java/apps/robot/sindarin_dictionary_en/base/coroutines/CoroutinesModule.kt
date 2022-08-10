package apps.robot.sindarin_dictionary_en.base.coroutines

import org.koin.dsl.module

fun coroutinesModule() = module {
    single<AppDispatchers> { JvmAppDispatchers() }
}