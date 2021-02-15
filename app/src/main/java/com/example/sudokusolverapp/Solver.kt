package com.example.sudokusolverapp

import android.util.Log

class Solver {

    var board: Array<IntArray>
    var emptyBoxIndex: ArrayList<ArrayList<Any>>? = null

    private var selectedRow: Int
    private var selectedColumn: Int

    init {
        selectedRow = -1
        selectedColumn = -1

        board = Array(9) { IntArray(9) }

        for (r in 0 until 9) {
            for (c in 0 until 9) {
                board[r][c] = 0
            }
        }

        emptyBoxIndex = ArrayList()
    }

    fun getEmptyBoxIndexs() {
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                if (this.board[r][c] == 0) {
                    this.emptyBoxIndex?.add(ArrayList())
                    this.emptyBoxIndex?.get(this.emptyBoxIndex?.size!!.minus(1))?.add(r)
                    this.emptyBoxIndex?.get(this.emptyBoxIndex?.size!!.minus(1))?.add(c)
                }
            }
        }
    }

//    private fun checkDuplicateNumber(row: Int, col: Int): Boolean {
//        var isDuplicateVertical = false
//        var isDuplicateHorizontal = false
//        var isDuplicateInBox = false
//
//        isDuplicateVertical = getDuplicateVertical(row, col)
//        isDuplicateHorizontal = getDuplicateHorizontal(row, col)
//        isDuplicateInBox = getDuplicateInBox(row,col)
//
//        return isDuplicateVertical && isDuplicateHorizontal && isDuplicateInBox
//    }
//
//    private fun getDuplicateVertical(row: Int, col: Int): Boolean {
//        for (i in 0 until 9) {
//            return this.board[row][col] == this.board[row][i]
//        }
//        return false
//    }
//
//    private fun getDuplicateHorizontal(row: Int, col: Int): Boolean {
//        for (i in 0 until 9) {
//            return this.board[row][col] == this.board[i][col]
//        }
//        return false
//    }
//
//    private fun getDuplicateInBox(row: Int, col: Int): Boolean {
//        for (x in 0..2) {
//            for (y in 0..2) {
//                return this.board[row][col] == this.board[x+row%3][y+col%3]
//            }
//        }
//        return false
//    }

    private fun check(row: Int, column: Int): Boolean {
        if (this.board[row][column] > 0) {
            for (i in 0 until 9) {
                if (this.board[i][column] == this.board[row][column] && row != i) {
                    return false
                }

                if (this.board[row][i] == this.board[row][column] && column != i) {
                    return false
                }
            }
            val boxRow = row/3
            val boxCol = column/3

            for (r in boxRow * 3 until boxRow * 3 + 3) {
                for (c in boxCol * 3 until boxCol * 3 + 3) {
                    if (this.board[r][c] == this.board[row][column] && row != r && column != c) {
                        return false
                    }
                }
            }
        }
        return true
    }

    fun solve(display: Board): Boolean{
        var row = -1
        var col = -1

        for (r in 0 until 9) {
            for (c in 0 until 9) {
                if (this.board[r][c] == 0) {
                    row = r
                    col = c
                    break
                }
            }
        }

        if (row == -1 || col == -1) {
            return true
        }

        for (i in 1 until 10) {
            this.board[row][col] = i
            display.invalidate()

            if (check(row, col)) {
                if (solve(display)) {
                    return true
                }
            }

            this.board[row][col] = 0
        }

        return false
    }

    fun resetBoard() {
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                board[r][c] = 0
            }
        }
        this.emptyBoxIndex = ArrayList()
    }

    fun setNumberPos(num: Int) {
        if (this.selectedRow != -1 && this.selectedColumn != -1) {
            if (this.board[this.selectedRow-1][this.selectedColumn-1] == num) {
                this.board[this.selectedRow-1][this.selectedColumn-1] = 0
            } else {
                this.board[this.selectedRow-1][this.selectedColumn-1] = num
            }
        }
    }

    fun getBoardd(): Array<IntArray> {
        return this.board
    }

    fun getEmptyBoxIndexx(): ArrayList<ArrayList<Any>>? {
        return this.emptyBoxIndex
    }

    fun getSelectedRow(): Int {
        return selectedRow
    }

    fun getSelectedColumn(): Int {
        return selectedColumn
    }

    fun setSelectedRow(r: Int) {
        selectedRow = r
    }

    fun setSelectedColumn(c: Int) {
        selectedColumn = c
    }

}