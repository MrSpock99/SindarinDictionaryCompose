package apps.robot.sindarin_dictionary_en.dictionary.list.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.UiState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.EngToElfDictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.base.data.EngToElfDictionaryRepositoryImpl
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetHeadersUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetPagedWordListAsFlowUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryLoadWordListUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionarySearchWordsUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.DictionaryListState
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.WordUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import timber.log.Timber

internal class DictionaryListViewModel(
    private val getPagedWordListAsFlow: DictionaryGetPagedWordListAsFlowUseCase,
    private val loadWordList: DictionaryLoadWordListUseCase,
    private val getHeaders: DictionaryGetHeadersUseCase,
    private val searchWords: DictionarySearchWordsUseCase,
    private val dispatchers: AppDispatchers,
    private val repository: EngToElfDictionaryRepository
) : BaseViewModel() {

    val state = MutableStateFlow(DictionaryListState(uiState = UiState.Loading))

    init {
        launchJob {
            listOf(
                asyncCatching { loadWordList(DictionaryMode.ELVISH_TO_ENGLISH) },
                asyncCatching { loadWordList(DictionaryMode.ENGLISH_TO_ELVISH) }
            ).awaitAll()
            subscribeToWords(state.value.dictionaryMode)
            subscribeToSearch()
            state.update { it.copy(uiState = UiState.Content) }
            (repository as EngToElfDictionaryRepositoryImpl).addItem("a","us","adan")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("e","alien","benn")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("i","illusion","lim")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("o","orbit","noss")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("u","cook","um")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("y","like german 'ü'","ylf")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("ai","slide ","edain")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("ei","sale ","einior")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("ui","queen","uilos")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("ae","Ally","aear")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("oe","nowhere","noeg")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("au","now","auth,maw")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("b","bush","benn")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("c","class","calad")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("ch","like russian 'х', but more aggressive","chîr")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("d","date","daw")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("dh","this","nedh")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("f","fly","faras")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("-f in the end of a word and before a consonant","vine","lâf, uidafnen")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("g","goose","galad")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("h","like russian 'x'","hador")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("hw","where","hwand")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("k","kill","kalar")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("l","1) after 'e','i', in the end of words, before consonants — soft 'l' \n2)hard 'l'","lhaw")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("m","my","amar")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("n","nose","nîn")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("ng","1) in the end of a word - nasal 'n' and 'g'(long)\n2) in the middle of a word - God ","1) ang\n2) angband")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("p","post","pân")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("ph","farm","alph")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("r","like russian 'р'","arad")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("rh","like russian 'р' but thrill isn't accentuated","Rhovanion")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("lh","hard 'l'","lhaw")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("s","sleep","losto")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("t","тропа","tinc, mant")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("th","think","maeth")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("v","vine","govad")
            (repository as EngToElfDictionaryRepositoryImpl).addItem("w","way","wend, iarwain")
        }
        //setHeaders(state.value.dictionaryMode)
    }

    fun onModeChange(dictionaryMode: DictionaryMode) {
        state.update {
            it.copy(
                dictionaryMode = dictionaryMode
            )
        }
        subscribeToWords(dictionaryMode)
        //setHeaders(dictionaryMode)
    }

    fun onSearchToggle() {
        state.update {
            it.copy(
                searchWidgetState = if (state.value.searchWidgetState == SearchWidgetState.OPENED) {
                    SearchWidgetState.CLOSED
                } else {
                    SearchWidgetState.OPENED
                }
            )
        }
    }

    fun onSearchTextChange(searchText: String) {
        state.value.searchText.tryEmit(searchText)
    }

    fun onDragChange(isDragging: Boolean) {
        state.value.headersState.isUserDragging.value = isDragging
        state.value.headersState.shouldShowSelectedHeader.value =
            isDragging && state.value.headersState.headers.isNotEmpty()
    }

    fun onSelectedHeaderIndexChange(selectedIndex: Int) {
        state.value.headersState.selectedHeaderIndex.value = selectedIndex
    }

    private fun subscribeToWords(dictionaryMode: DictionaryMode) {
        state.value = state.value.copy(
            words = getPagedWordListAsFlow(dictionaryMode).map { pagingData ->
                pagingData.map {
                    Timber.d("DictList: ${it.word}")
                    WordUiModel(
                        id = it.id,
                        word = UiText.DynamicString(it.word),
                        translation = UiText.DynamicString(it.translation),
                        isFavorite = it.isFavorite
                    )
                }
            }.cachedIn(viewModelScope + dispatchers.computing)
        )
    }

    private fun setHeaders(dictionaryMode: DictionaryMode) {
        launchJob {
            state.update {
                it.copy(
                    headersState = it.headersState.copy(
                        headers = getHeaders(dictionaryMode).map {
                            UiText.DynamicString(
                                it
                            )
                        }
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun subscribeToSearch() {
        state.value.searchText.debounce(SEARCH_DEBOUNCE)
            .mapLatest {
                state.value = state.value.copy(
                    words = searchWords(dictionaryMode = state.value.dictionaryMode, keyword = it)
                        .map { pagingData ->
                            pagingData.map {
                                WordUiModel(
                                    id = it.id,
                                    word = UiText.DynamicString(it.word),
                                    translation = UiText.DynamicString(it.translation),
                                    isFavorite = it.isFavorite
                                )
                            }
                        }.cachedIn(viewModelScope + dispatchers.computing)
                )
            }.launchIn(viewModelScope + dispatchers.computing)
    }

    private companion object {
        const val SEARCH_DEBOUNCE = 300L
    }
}