package ru.kaspenium.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Stack

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val operations = findViewById<TextView>(R.id.operations)

        val btn0 = findViewById<Button>(R.id.num0)
        val btn1 = findViewById<Button>(R.id.num1)
        val btn2 = findViewById<Button>(R.id.num2)
        val btn3 = findViewById<Button>(R.id.num3)
        val btn4 = findViewById<Button>(R.id.num4)
        val btn5 = findViewById<Button>(R.id.num5)
        val btn6 = findViewById<Button>(R.id.num6)
        val btn7 = findViewById<Button>(R.id.num7)
        val btn8 = findViewById<Button>(R.id.num8)
        val btn9 = findViewById<Button>(R.id.num9)
        val btnAC = findViewById<Button>(R.id.AC)
        val btnBack = findViewById<Button>(R.id.back)
        val btnResult = findViewById<Button>(R.id.result)
        val btnDot = findViewById<Button>(R.id.dot)
        val bracketOpen = findViewById<Button>(R.id.bracketOpen)
        val bracketClose = findViewById<Button>(R.id.bracketClose)
        val btnPlus = findViewById<Button>(R.id.plus)
        val btnMinus = findViewById<Button>(R.id.minus)
        val btnMultipli = findViewById<Button>(R.id.multipli)
        val btnDivision = findViewById<Button>(R.id.division)

        btn0.setOnClickListener { tryAddSymbol("0", operations) }
        btn1.setOnClickListener { tryAddSymbol("1", operations) }
        btn2.setOnClickListener { tryAddSymbol("2", operations) }
        btn3.setOnClickListener { tryAddSymbol("3", operations) }
        btn4.setOnClickListener { tryAddSymbol("4", operations) }
        btn5.setOnClickListener { tryAddSymbol("5", operations) }
        btn6.setOnClickListener { tryAddSymbol("6", operations) }
        btn7.setOnClickListener { tryAddSymbol("7", operations) }
        btn8.setOnClickListener { tryAddSymbol("8", operations) }
        btn9.setOnClickListener { tryAddSymbol("9", operations) }
        btnDot.setOnClickListener { tryAddSymbol(".", operations) }
        btnPlus.setOnClickListener { tryAddSymbol("+", operations) }
        btnMinus.setOnClickListener { tryAddSymbol("-", operations) }
        btnMultipli.setOnClickListener { tryAddSymbol("*", operations) }
        btnDivision.setOnClickListener { tryAddSymbol("/", operations) }
        bracketOpen.setOnClickListener { tryAddSymbol("(", operations) }
        bracketClose.setOnClickListener { tryAddSymbol(")", operations) }

        btnAC.setOnClickListener { operations.text = "" }

        btnBack.setOnClickListener {
            if (operations.text.isNotEmpty() && operations.text != "#ERROR!") operations.text =
                operations.text.substring(0, operations.text.length - 1)
        }

        btnResult.setOnClickListener {
            val result = calculateExpression(operations.text.toString())
            try {
                val doubleResult = result.toDouble()
                val longResult = doubleResult.toLong()
                if (doubleResult == longResult.toDouble()) {
                    operations.text = longResult.toString()
                } else {
                    operations.text = doubleResult.toString()
                }
            } catch (ex: Exception) {
                operations.text = "#ERROR!"
            }
        }
    }

    fun tryAddSymbol(str: String, view: TextView) {
        if (view.text.toString() != "#ERROR!") {
            view.append(str)
        }
    }

    fun calculateExpression(expression: String): String {
        val operands = Stack<Double>()
        val operators = Stack<Char>()

        fun performOperation() {
            val operator = operators.pop()
            val operand2 = operands.pop()
            val operand1 = operands.pop()

            when (operator) {
                '+' -> operands.push(operand1 + operand2)
                '-' -> operands.push(operand1 - operand2)
                '*' -> operands.push(operand1 * operand2)
                '/' -> {
                    if (operand2 != 0.0) {
                        operands.push(operand1 / operand2)
                    } else {
                        throw ArithmeticException("Division by zero")
                    }
                }
            }
        }

        var currentNumber = StringBuilder()

        for (token in expression) {
            when {
                token.isDigit() || token == '.' -> {
                    currentNumber.append(token)
                }

                token in setOf('+', '-', '*', '/') -> {
                    if (currentNumber.isNotEmpty()) {
                        operands.push(currentNumber.toString().toDouble())
                        currentNumber.clear()
                    }

                    while (operators.isNotEmpty() && precedence(operators.peek()) >= precedence(token)) {
                        try {
                            performOperation()
                        } catch (ex: Exception) {
                            return "Error"
                        }
                    }
                    operators.push(token)
                }

                token == '(' -> operators.push(token)
                token == ')' -> {
                    if (currentNumber.isNotEmpty()) {
                        operands.push(currentNumber.toString().toDouble())
                        currentNumber.clear()
                    }

                    while (operators.isNotEmpty() && operators.peek() != '(') {
                        try {
                            performOperation()
                        } catch (ex: Exception) {
                            return "Error"
                        }
                    }
                    operators.pop() // Pop the '('
                }
            }
        }

        if (currentNumber.isNotEmpty()) {
            operands.push(currentNumber.toString().toDouble())
        }

        while (operators.isNotEmpty()) {
            try {
                performOperation()
            } catch (ex: Exception) {
                return "Error"
            }
        }

        return if (operands.size == 1) {
            operands.pop().toString()
        } else {
            "Error"
        }
    }

    private fun precedence(operator: Char): Int {
        return when (operator) {
            '+', '-' -> 1
            '*', '/' -> 2
            else -> 0
        }
    }
}