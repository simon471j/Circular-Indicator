package com.example.customizedcircularindicator

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customizedcircularindicator.ui.theme.retainDecimal


@Composable
fun IndicatorComponent(
    componentSize: Dp = 300.dp,
    maxIndicatorNum: Float = 100f,
    backgroundIndicatorColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    backgroundIndicatorStrokeWidth: Float = 100f,
    foregroundSweepAngle: Float
) {

    val targetIndicatorValue = remember {
        Animatable(initialValue = 0f)
    }

    val legalSweepAngle = foregroundSweepAngle in 0.0..maxIndicatorNum.toDouble()

    LaunchedEffect(key1 = foregroundSweepAngle, block = {
        if (legalSweepAngle) {
            targetIndicatorValue.animateTo(
                targetValue = foregroundSweepAngle * 2.4f, animationSpec = tween(
                    durationMillis = 500
                )
            )
        }
    })
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(componentSize)
            .drawBehind {
                val indicatorComponentSize = size * 0.8f
                drawBackgroundIndicator(
                    componentSize = indicatorComponentSize,
                    stroke = backgroundIndicatorStrokeWidth,
                    color = backgroundIndicatorColor
                )
                drawForegroundIndicator(
                    componentSize = indicatorComponentSize,
                    stroke = backgroundIndicatorStrokeWidth,
                    sweepAngle = targetIndicatorValue.value
                )

            }
    ) {
        Text(
            text = "Current Occupy",
            style = TextStyle(
                color = Color.LightGray,
                fontSize = 12.sp
            )
        )
        Text(
            text = "${(foregroundSweepAngle / 100).retainDecimal(2)} Percent",
            style = TextStyle(
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        )

    }
}

fun DrawScope.drawBackgroundIndicator(componentSize: Size, stroke: Float, color: Color) {
    drawArc(
        size = componentSize,
        color = color,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(cap = StrokeCap.Round, width = stroke),
        topLeft = Offset(
            x = ((size.width - componentSize.width) / 2),
            y = ((size.height - componentSize.height) / 2)
        )
    )
}


fun DrawScope.drawForegroundIndicator(
    componentSize: Size,
    stroke: Float,
    sweepAngle: Float
) {

    drawArc(
        size = componentSize,
        brush = Brush.sweepGradient(
            0.111f to Color(0xFF4286f4),
            0.388f to Color(0xFF373B44),
            1f to Color(0xFF4286f4)
        ),
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(cap = StrokeCap.Round, width = stroke),
        topLeft = Offset(
            x = ((size.width - componentSize.width) / 2),
            y = ((size.height - componentSize.height) / 2)
        )
    )
}