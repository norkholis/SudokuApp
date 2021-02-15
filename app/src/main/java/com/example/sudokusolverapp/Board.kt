package com.example.sudokusolverapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.ceil
import kotlin.math.min

class Board(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val a = context.theme.obtainStyledAttributes(attrs, R.styleable.Board, 0,0)

    private val boardColor = a.getInteger(R.styleable.Board_boardColor,0)
    private val cellFillColor = a.getInteger(R.styleable.Board_cellFillColor,0)
    private val cellHighlightColor = a.getInteger(R.styleable.Board_cellHighlightColor,0)

    private val letterColor = a.getInteger(R.styleable.Board_letterColor, 0)
    private val letterColorSolve = a.getInteger(R.styleable.Board_letterColorSolve, 0)

    private var cellSize = 0

    private val boardColorPaint by lazy { Paint() }
    private val cellFillColorPaint by lazy { Paint() }
    private val cellHighlightColorPaint by lazy { Paint() }

    private val letterPaint by lazy { Paint() }
    private val letterPaintBound by lazy { Rect() }

    private val solver by lazy { Solver() }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val dimension = min(this.measuredWidth, this.measuredHeight)

        cellSize = dimension / 9
        setMeasuredDimension(dimension, dimension)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var isValid: Boolean

        val x = event.x
        val y = event.y

        val action = event.action

        isValid = if (action == MotionEvent.ACTION_DOWN) {
            solver.setSelectedRow(ceil((y/cellSize).toDouble()).toInt())
            solver.setSelectedColumn(ceil((x/cellSize).toDouble()).toInt())
            true
        } else {
            false
        }
        
        return isValid
    }

    private fun colorCell(canvas: Canvas, r: Int, c: Int) {
        if (solver.getSelectedColumn() != -1 && solver.getSelectedRow() != -1) {
            canvas.drawRect(
                    ((c - 1) * cellSize).toFloat(),
                    0F,
                    (c * cellSize).toFloat(),
                    (cellSize * 9).toFloat(),
                    cellHighlightColorPaint
            )
            canvas.drawRect(
                    0F,
                    ((r - 1) * cellSize).toFloat(),
                    (cellSize * 9).toFloat(),
                    (r * cellSize).toFloat(),
                    cellHighlightColorPaint
            )
            canvas.drawRect(
                    ((c - 1) * cellSize).toFloat(),
                    ((r - 1) * cellSize).toFloat(),
                    (c * cellSize).toFloat(),
                    (r * cellSize).toFloat(),
                    cellHighlightColorPaint
            )
        }

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        boardColorPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 16.toFloat()
            color = boardColor
            isAntiAlias = true
        }

        cellFillColorPaint.apply {
            style = Paint.Style.FILL
            color = cellFillColor
            isAntiAlias = true
        }

        cellHighlightColorPaint.apply {
            style = Paint.Style.FILL
            color = cellHighlightColor
            isAntiAlias = true
        }

        letterPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = letterColor
        }

        colorCell(canvas, solver.getSelectedRow(), solver.getSelectedColumn())
        canvas.drawRect(0F,0F,width.toFloat(), height.toFloat(), boardColorPaint)
        drawBoard(canvas)
        drawNumbers(canvas)
    }

    private fun drawThickLine() {
        boardColorPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 10.toFloat()
            color = boardColor
        }
    }

    private fun drawThinLine() {
        boardColorPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 4.toFloat()
            color = boardColor
        }
    }

    private fun drawBoard(canvas: Canvas) {
        for (c in 0..9) {
            if (c%3 == 0) {
                drawThickLine()
            } else {
                drawThinLine()
            }
            canvas.drawLine((cellSize * c).toFloat(), 0F, (cellSize * c).toFloat(), width.toFloat(), boardColorPaint)
        }

        for (r in 0..9) {
            if (r%3 == 0) {
                drawThickLine()
            } else {
                drawThinLine()
            }
            canvas.drawLine(0F, (cellSize * r).toFloat(), width.toFloat(), (cellSize * r).toFloat(), boardColorPaint)
        }
    }

    private fun drawNumbers(canvas: Canvas) {

        letterPaint.textSize = cellSize.toFloat()

        for (r in 0 until 9) {
            for (c in 0 until 9) {
                if (solver.getBoardd()[r][c] != 0) {
                    val text = Integer.toString(solver.board[r][c])

                    letterPaint.getTextBounds(text, 0, text.length, letterPaintBound)
                    val width: Float = letterPaint.measureText(text)
                    val height: Float = letterPaintBound.height().toFloat()

                    canvas.drawText(text, (c*cellSize) + ((cellSize - width)/2), (r*cellSize+cellSize) - ((cellSize - height)/2), letterPaint)
                }
            }
        }

        letterPaint.color = letterColorSolve

        for (letter in solver.getEmptyBoxIndexx()!!) {
            val r = letter.get(0) as Int
            val c = letter.get(1) as Int

            val text = Integer.toString(solver.board[r][c])

            letterPaint.getTextBounds(text, 0, text.length, letterPaintBound)
            val width: Float = letterPaint.measureText(text)
            val height: Float = letterPaintBound.height().toFloat()

            canvas.drawText(text, (c*cellSize) + ((cellSize - width)/2), (r*cellSize+cellSize) - ((cellSize - height)/2), letterPaint)
        }
    }

    fun getSolverr(): Solver {
        return this.solver
    }
}