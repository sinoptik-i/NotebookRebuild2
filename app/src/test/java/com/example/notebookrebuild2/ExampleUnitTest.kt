package com.example.notebookrebuild2

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
    fun collections() {
        val collection = arrayListOf(1, 2, 3)
        var elem=5
        if (collection.contains(elem)){
            collection.remove(elem)
        }
        println(collection)
        elem=2
        if (collection.contains(elem)){
            collection.remove(elem)
        }
        println(collection)
    }
}