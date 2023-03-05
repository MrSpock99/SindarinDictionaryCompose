package apps.robot.sindarin_dictionary_en.base_ui.presentation.base

import androidx.annotation.IntDef

/**
 * Используется для старта выполнения каких-то работ при запуске приложения.
 */
interface BaseAppInitializer {
    /**
     * Отвечает за приоритет запуска работ.
     * Чем выше значение, тем раньше относительно других запускается работа.
     * Т.е. если ваша работа должна быть запущена самой первой, выбирайте [Priority.SYSTEM]
     * Если вам не важно, когда будет запущена ваша работа [Priority.LOW] (значение по умолчанию).
     */
    @Priority fun getPriority() = Priority.LOW

    /**
     * Метод, в котором нужно запускать свою работу.
     * Метод синхронный, если нужно запустить что либо асинхронно,
     * то стартуйте корутину из скоупа приложения.
     */
    fun onAppStartInit()

    @IntDef(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Priority {
        companion object {
            const val LOW = 0
            const val MEDIUM = 500
            const val HIGH = 1000
            const val SYSTEM = Int.MAX_VALUE
        }
    }
}