package com.alexruskovski.breadth_first_search.data

import androidx.compose.ui.graphics.Color


/**
 * Created by Alexander Ruskovski on 16/10/2022
 */

data class Box(
    val x: Float,
    val y: Float,
    val isVisited: Boolean = false,
    val isTarget: Boolean = false,
    val isSolid: Boolean = false
) {
    fun visit() = this.copy(isVisited = true)
    fun setAsTarget() = this.copy(isTarget = true)
    fun getBoxColour() = if (isTarget) Color.Blue
    else if (isSolid) Color.Black
    else if (isVisited) Color.Red
    else Color.Cyan
}