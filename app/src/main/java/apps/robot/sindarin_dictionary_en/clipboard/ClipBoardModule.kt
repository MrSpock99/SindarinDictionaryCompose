package apps.robot.sindarin_dictionary_en.clipboard

import apps.robot.sindarin_dictionary_en.clipboard.data.ClipboardRepositoryImpl
import apps.robot.sindarin_dictionary_en.clipboard.domain.ClipboardRepository
import apps.robot.sindarin_dictionary_en.clipboard.domain.ClipboardSetTextUseCase
import org.koin.dsl.module

fun clipboardModule() = module {
    factory <ClipboardRepository> { ClipboardRepositoryImpl(get()) }
    factory { ClipboardSetTextUseCase(get()) }
}