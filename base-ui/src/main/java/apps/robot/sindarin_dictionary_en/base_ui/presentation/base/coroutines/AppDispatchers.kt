package apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * Использовать для переключения между [CoroutineDispatcher]'ами вместо использования
 * напрямую [kotlinx.coroutines.Dispatchers]
 */
interface AppDispatchers {
    /**
     * Использовать для выполнения корутин в ui-потоке.
     * подходит для выполнения задач, связаных с обновлением ui-элементов
     */
    val ui: MainCoroutineDispatcher

    /**
     * Для выполнения задач, связанных с записью в файл, либо в бд.
     * Подходит, например, для того, чтобы читать файл из resources\raw или assets,
     * либо для синхронной записи в [android.content.SharedPreferences]
     */
    val storage: CoroutineDispatcher

    /**
     * Для выполнения задач, связанных с походом в сеть.
     */
    val network: CoroutineDispatcher

    /**
     * Для выполнения длительных вычислительных задач, чтобы не занимать [main], [storage] или [network].
     * Сортировку, парсинг рекомендуется выполнять в [computing]
     */
    val computing: CoroutineDispatcher
}