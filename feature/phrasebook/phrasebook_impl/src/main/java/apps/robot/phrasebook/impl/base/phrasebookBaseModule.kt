package apps.robot.phrasebook.impl.base

import apps.robot.phrasebook.impl.BuildConfig
import apps.robot.phrasebook.impl.base.data.PhrasebookRepositoryImpl
import apps.robot.phrasebook.impl.base.data.FirebasePhrasebookDataSource
import apps.robot.phrasebook.impl.base.data.LocalPhrasebookDataSource
import apps.robot.phrasebook.impl.base.data.PhrasebookDataSource
import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal fun phrasebookBaseModule() = module {
    single<PhrasebookDataSource> {
        if (BuildConfig.USE_FIREBASE_PHRASEBOOK_DATA_SOURCE) {
            FirebasePhrasebookDataSource(
                db = get(),
                dispatchers = get(),
                fallback = LocalPhrasebookDataSource(androidContext().resources)
            )
        } else {
            LocalPhrasebookDataSource(androidContext().resources)
        }
    }
    factory<PhrasebookRepository> {
        PhrasebookRepositoryImpl(
            resources = androidContext().resources,
            dataSource = get(),
            dao = get()
        )
    }
}
