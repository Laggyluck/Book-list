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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UpdateActivity : Activity() {

    fun updateData(view: View) {
        val isbn = findViewById<EditText>(R.id.et_isbnNumber).text.toString()
        if (isbnValidation(isbn)) {
            DbHandler(this).updateBook(createBook())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else Toast.makeText(
            applicationContext,
            "Use correct isbn format",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun deleteData(view: View) {
        DbHandler(this).deleteBook(intent.getStringExtra("_id").toInt())
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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

    private fun setData(book: Book?) {
        findViewById<EditText>(R.id.et_title).setText(book?.title)
        findViewById<EditText>(R.id.et_author).setText(book?.author)
        findViewById<EditText>(R.id.et_pages).setText(book?.pages.toString())
        findViewById<EditText>(R.id.et_isbnNumber).setText(book?.isbn.toString())
        //TODO: rating stars
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_layout)
        val id = intent.getStringExtra("_id").toInt()
        val dbHandler = DbHandler(this)

        GlobalScope.launch {
            val book = async {
                dbHandler.getBook(id)
            }
            setData(book.await())
        }
    }
}