package apps.robot.sindarin_dictionary_en.base_ui.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

enum class BorderOrder {
    Start, Center, End
}

fun Modifier.drawSegmentedBorder(
    strokeWidth: Dp,
    color: Color,
    cornerPercent: Int,
    borderOrder: BorderOrder,
    drawDivider: Boolean = false
) = composed(
    factory = {

        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height
            val cornerRadius = height * cornerPercent / 100

            when (borderOrder) {
                BorderOrder.Start -> {

                    drawLine(
                        color = color,
                        start = Offset(x = width, y = 0f),
                        end = Offset(x = cornerRadius, y = 0f),
                        strokeWidth = strokeWidthPx
                    )

                    // Top left arc
                    drawArc(
                        color = color,
                        startAngle = 180f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset.Zero,
                        size = Size(cornerRadius * 2, cornerRadius * 2),
                        style = Stroke(width = strokeWidthPx)
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = cornerRadius),
                        end = Offset(x = 0f, y = height - cornerRadius),
                        strokeWidth = strokeWidthPx
                    )
                    // Bottom left arc
                    drawArc(
                        color = color,
                        startAngle = 90f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(x = 0f, y = height - 2 * cornerRadius),
                        size = Size(cornerRadius * 2, cornerRadius * 2),
                        style = Stroke(width = strokeWidthPx)
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = cornerRadius, y = height),
                        end = Offset(x = width, y = height),
                        strokeWidth = strokeWidthPx
                    )
                }
                BorderOrder.Center -> {
                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = width, y = 0f),
                        strokeWidth = strokeWidthPx
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = height),
                        end = Offset(x = width, y = height),
                        strokeWidth = strokeWidthPx
                    )

                    if (drawDivider) {
                        drawLine(
                            color = color,
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = height),
                            strokeWidth = strokeWidthPx
                        )
                    }
                }
                else -> {

                    if (drawDivider) {
                        drawLine(
                            color = color,
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = height),
                            strokeWidth = strokeWidthPx
                        )
                    }

                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = width - cornerRadius, y = 0f),
                        strokeWidth = strokeWidthPx
                    )

                    // Top right arc
                    drawArc(
                        color = color,
                        startAngle = 270f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(x = width - cornerRadius * 2, y = 0f),
                        size = Size(cornerRadius * 2, cornerRadius * 2),
                        style = Stroke(width = strokeWidthPx)
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = width, y = cornerRadius),
                        end = Offset(x = width, y = height - cornerRadius),
                        strokeWidth = strokeWidthPx
                    )
                    // Bottom right arc
                    drawArc(
                        color = color,
                        startAngle = 0f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(
                            x = width - 2 * cornerRadius,
                            y = height - 2 * cornerRadius
                        ),
                        size = Size(cornerRadius * 2, cornerRadius * 2),
                        style = Stroke(width = strokeWidthPx)
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = height),
                        end = Offset(x = width -cornerRadius, y = height),
                        strokeWidth = strokeWidthPx
                    )
                }
            }
        }
    }
)

fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: (Modifier.() -> Modifier)? = null
): Modifier {
    return if (condition) {
        then(ifTrue(Modifier))
    } else if (ifFalse != null) {
        then(ifFalse(Modifier))
    } else {
        this
    }
}