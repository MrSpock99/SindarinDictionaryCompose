package apps.robot.sindarin_dictionary_en.base_ui.presentation.clipboard

import apps.robot.sindarin_dictionary_en.base_ui.presentation.clipboard.data.ClipboardRepositoryImpl
import apps.robot.sindarin_dictionary_en.base_ui.presentation.clipboard.domain.ClipboardRepository
import apps.robot.sindarin_dictionary_en.base_ui.presentation.clipboard.domain.ClipboardSetTextUseCase
import org.koin.dsl.module

fun clipboardModule() = module {
    factory <ClipboardRepository> { ClipboardRepositoryImpl(get()) }
    factory { ClipboardSetTextUseCase(get()) }
}