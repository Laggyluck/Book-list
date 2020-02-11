package com.example.mybooks.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.example.mybooks.R
import com.example.mybooks.database.DbHandler
import com.example.mybooks.isbnValidation
import com.example.mybooks.models.Book

class AddActivity : Activity() {

    fun postData(view: View) {
        val isbn = findViewById<EditText>(R.id.et_isbnNumber).text.toString()

        if (isbnValidation(isbn)) {
            DbHandler(this).addBook(createBook())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else
            Toast.makeText(applicationContext, "Use correct isbn format", Toast.LENGTH_SHORT).show()
    }

    private fun createBook(): Book {
        return Book(
            null,
            findViewById<EditText>(R.id.et_title).text.toString(),
            findViewById<EditText>(R.id.et_author).text.toString(),
            findViewById<EditText>(R.id.et_pages).text.toString().toInt(),
            findViewById<EditText>(R.id.et_isbnNumber).text.toString(),
            findViewById<RatingBar>(R.id.rb_rating).rating.toString().toFloat()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_layout)
    }
}