package com.alexruskovski.breadth_first_search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexruskovski.breadth_first_search.ui.theme.BFSTheme
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexruskovski.breadth_first_search.data.Box
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            BFSTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val configuration = LocalConfiguration.current
                    val screenHeight = configuration.screenHeightDp.dp.value * LocalDensity.current.density
                    val screenWidth = configuration.screenWidthDp.dp.value * LocalDensity.current.density
                    val numberOfColumns = 30
                    val rectWidth = (screenWidth / numberOfColumns)
                    val rectHeight = rectWidth
                    val numberOfRows = (screenHeight / rectHeight).roundToInt()
                    val boxSize = Size(rectWidth, rectHeight)

                    val viewModel: MainViewModel = viewModel(factory = MainViewModel.getFactory(numberOfColumns, numberOfRows, rectWidth, rectHeight))
                    val boxes = viewModel.boxes.collectAsState()
                    Grid(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                viewModel.startBreadthFirstSearch()
                            },
                        boxes.value,
                        boxSize
                    )
                }
            }
        }
    }
}

@Composable
fun Grid(
    modifier: Modifier = Modifier,
    boxes: Array<Array<Box?>>,
    boxSize: Size
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .size(maxWidth, maxHeight)
        ) {
            for (i in boxes.indices) {
                for (j in 0 until boxes[i].size) {
                    val box = boxes[i][j] ?: continue
                    drawRect(
                        color = box.getBoxColour(),
                        topLeft = Offset(box.x, box.y),
                        size = boxSize,
                        style = if (box.isVisited || box.isSolid)
                            Fill else Stroke(width = 1.dp.toPx()),
                        )
                }
            }
        }
    }
}

