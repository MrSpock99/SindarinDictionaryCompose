package apps.robot.favorites.impl.list

import apps.robot.favorites.api.domain.FavoritesGetFavoriteByIdUseCase
import apps.robot.favorites.api.domain.FavoritesUpdateFavoriteStatusUseCase
import apps.robot.favorites.impl.list.domain.DictionaryGetFavoritesAsFlowUseCase
import apps.robot.favorites.impl.list.domain.FavoritesGetFavoriteByIdUseCaseImpl
import apps.robot.favorites.impl.list.domain.FavoritesUpdateFavoriteStatusUseCaseImpl
import apps.robot.favorites.impl.list.presentation.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun favoritesListModule() = module {
    factory<FavoritesUpdateFavoriteStatusUseCase> {
        FavoritesUpdateFavoriteStatusUseCaseImpl(
            favoritesDao = get()
        )
    }
    factory {
        DictionaryGetFavoritesAsFlowUseCase(
            dao = get()
        )
    }
    factory<FavoritesGetFavoriteByIdUseCase> {
        FavoritesGetFavoriteByIdUseCaseImpl(
            favoritesDao = get()
        )
    }
    viewModel {
        FavoritesViewModel(
            getFavoritesAsFlow = get(),
            dispatchers = get(),
        )
    }
}