package ru.kaspenium.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val operations = findViewById<TextView>(R.id.operations)

        val btn0 = findViewById<TextView>(R.id.num0)
        val btn1 = findViewById<TextView>(R.id.num1)
        val btn2 = findViewById<TextView>(R.id.num2)
        val btn3 = findViewById<TextView>(R.id.num3)
        val btn4 = findViewById<TextView>(R.id.num4)
        val btn5 = findViewById<TextView>(R.id.num5)
        val btn6 = findViewById<TextView>(R.id.num6)
        val btn7 = findViewById<TextView>(R.id.num7)
        val btn8 = findViewById<TextView>(R.id.num8)
        val btn9 = findViewById<TextView>(R.id.num9)
        val btnAC = findViewById<TextView>(R.id.AC)
        val btnBack = findViewById<TextView>(R.id.back)
        val btnResult = findViewById<TextView>(R.id.result)
        val btnDot = findViewById<TextView>(R.id.dot)
        val bracketOpen = findViewById<TextView>(R.id.bracketOpen)
        val bracketClose = findViewById<TextView>(R.id.bracketClose)
        val btnPlus = findViewById<TextView>(R.id.plus)
        val btnMinus = findViewById<TextView>(R.id.minus)
        val btnMultipli = findViewById<TextView>(R.id.multipli)
        val btnDivision = findViewById<TextView>(R.id.division)

        btn0.setOnClickListener { operations.append("0") }
        btn1.setOnClickListener { operations.append("1") }
        btn2.setOnClickListener { operations.append("2") }
        btn3.setOnClickListener { operations.append("3") }
        btn4.setOnClickListener { operations.append("4") }
        btn5.setOnClickListener { operations.append("5") }
        btn6.setOnClickListener { operations.append("6") }
        btn7.setOnClickListener { operations.append("7") }
        btn8.setOnClickListener { operations.append("8") }
        btn9.setOnClickListener { operations.append("9") }
        btnDot.setOnClickListener { operations.append(".") }
        btnPlus.setOnClickListener { operations.append("+") }
        btnMinus.setOnClickListener { operations.append("-") }
        btnMultipli.setOnClickListener { operations.append("*") }
        btnDivision.setOnClickListener { operations.append("/") }
        bracketOpen.setOnClickListener { operations.append("(") }
        bracketClose.setOnClickListener { operations.append(")") }

        btnAC.setOnClickListener { operations.text = "" }

        btnBack.setOnClickListener {
            if (operations.text.isNotEmpty())
                operations.text = operations.text.substring(0, operations.text.length-1)
        }

        btnResult.setOnClickListener {
            try{
                val ex = ExpressionBuilder(operations.text.toString()).build()
                val res = ex.evaluate()
                val longRes = res.toLong()
                if (res == longRes.toDouble())
                    operations.text = longRes.toString()
                else
                    operations.text = res.toString()
            } catch(e:Exception) {
                Log.d("Error", "Error: ${e.message}")
            }
        }
    }
}