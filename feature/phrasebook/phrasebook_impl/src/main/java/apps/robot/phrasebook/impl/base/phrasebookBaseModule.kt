package apps.robot.phrasebook.impl.base

import apps.robot.phrasebook.impl.base.data.PhrasebookRepositoryImpl
import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal fun phrasebookBaseModule() = module {
    factory<PhrasebookRepository> {
        PhrasebookRepositoryImpl(
            resources = androidContext().resources,
            db = get(),
            dispatchers = get()
        )
    }
}