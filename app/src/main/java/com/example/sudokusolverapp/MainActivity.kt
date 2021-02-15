package com.example.sudokusolverapp

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var gameBoard: Board
    lateinit var solver: Solver

    private var isStart = false

    private lateinit var solveBtn: Button
    private lateinit var chrono: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameBoard = findViewById(R.id.board)
        solveBtn = findViewById(R.id.btn_solve)
        chrono = findViewById(R.id.timer)
        solver = gameBoard.getSolverr()
    }

    fun btnOnePressed(view: View) {
        solver.setNumberPos(1)
        gameBoard.invalidate()
    }

    fun btnTwoPressed(view: View) {
        solver.setNumberPos(2)
        gameBoard.invalidate()
    }

    fun btnThreePressed(view: View) {
        solver.setNumberPos(3)
        gameBoard.invalidate()
    }

    fun btnFourPressed(view: View) {
        solver.setNumberPos(4)
        gameBoard.invalidate()
    }

    fun btnFivePressed(view: View) {
        solver.setNumberPos(5)
        gameBoard.invalidate()
    }

    fun btnSixPressed(view: View) {
        solver.setNumberPos(6)
        gameBoard.invalidate()
    }

    fun btnSevenPressed(view: View) {
        solver.setNumberPos(7)
        gameBoard.invalidate()
    }

    fun btnEightPressed(view: View) {
        solver.setNumberPos(8)
        gameBoard.invalidate()
    }

    fun btnNinePressed(view: View) {
        solver.setNumberPos(9)
        gameBoard.invalidate()
    }

    fun btnSolvePressed(view: View) {
        if (isStart) {
            solver.getEmptyBoxIndexs()

            val thread = SolveBoardThread()
            Thread(thread).start()

            gameBoard.invalidate()
            chrono.stop()
            isStart = false
        } else {
            Toast.makeText(this, getString(R.string.new_game_first), Toast.LENGTH_SHORT).show()
        }

    }

    fun btnClearPressed(view: View) {
        if (isStart) {
            solver.resetBoard()
            gameBoard.invalidate()
            isStart = false
        } else {
            isStart = true
            solver.resetBoard()
            gameBoard.invalidate()
            chrono.base = SystemClock.elapsedRealtime()
            chrono.start()
        }
    }

    inner class SolveBoardThread: Runnable {
        override fun run() {
            solver.solve(gameBoard)
        }

    }
}