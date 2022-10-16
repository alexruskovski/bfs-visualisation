package com.alexruskovski.breadth_first_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alexruskovski.breadth_first_search.data.Box
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random


class MainViewModel constructor(
    private val numberOfColumns: Int,
    private val numberOfRows: Int,
    private var rectWidth: Float,
    private var rectHeight: Float
) : ViewModel() {

    private val boxesFlow =
        MutableStateFlow(Array<Array<Box?>>(numberOfColumns) { Array(numberOfRows) { null } })
    val boxes = boxesFlow.asStateFlow()

    init {
        populateTheList()
    }

    private fun populateTheList() {
        for (i in 0 until numberOfColumns) {
            for (j in 0 until numberOfRows) {
                boxesFlow.value[i][j] = Box(
                    x = i * rectWidth,
                    y = j * rectHeight,
                    isSolid = Random.nextInt(10) < 2
                )
            }
        }
    }

    //just a flag so that once completed
    //use can restart it
    var started = false

    fun startBreadthFirstSearch() {
        if (started) {
            populateTheList()
            started = false
        } else {
            started = true
            viewModelScope.launch {
                bfs(0, 0)
            }
        }
    }

    //breadth first search
    private fun bfs(
        i: Int,
        j: Int
    ) {
        if (i > boxesFlow.value.size - 1 || i < 0) return
        if (j > boxesFlow.value[i].size - 1 || j < 0) return
        if (boxesFlow.value[i][j]?.isVisited == true) return
        if (boxesFlow.value[i][j]?.isSolid == true) return
        boxesFlow.value[i][j] = boxesFlow.value[i][j]?.visit()
        bfs(i = i + 1, j) //Right
        bfs(i = i, j + 1) //Down
//        bfs(i = i - 1, j, targetPoint) //Left
//        bfs(i = i, j + 1, targetPoint) //Up
        //Diagonals
//        bfs(i = i - 1, j + 1) //bottom left
//        bfs(i = i - 1, j - 1) //top left
//        bfs(i = i + 1, j - 1) //top right
//        bfs(i = i + 1, j + 1) //bottom right
    }

    companion object {
        fun getFactory(
            numberOfColumns: Int,
            numberOfRows: Int,
            rectWidth: Float,
            rectHeight: Float
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(numberOfColumns, numberOfRows, rectWidth, rectHeight) as T
                }
            }
        }
    }

}



