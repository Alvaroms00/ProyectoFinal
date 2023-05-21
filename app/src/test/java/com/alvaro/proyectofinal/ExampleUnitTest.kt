package com.alvaro.proyectofinal

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testTextField() {
        val inputText = "Hello, World!"
        val textField = TextField()

        textField.setText(inputText)

        val actualText = textField.getText()
        assertEquals(inputText, actualText)
    }
}

class TextField {
    private var text: String = ""

    fun setText(text: String) {
        this.text = text
    }

    fun getText(): String {
        return text
    }
}