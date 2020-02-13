package com.example.mybooks.activities

import android.app.Activity
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
    var book: Book? = null
    var title: String? = null
    var author: String? = null
    var pages: Int? = null
    var isbn: String? = null
    var rating: Float? = null

    fun postData(view: View) {
        if(createBook()) {
            val isbn = findViewById<EditText>(R.id.et_isbnNumber).text.toString()

            if (isbnValidation(isbn)) {
                DbHandler(this).addBook(book!!)
                finish()
            } else {
                Toast.makeText(applicationContext, "Use correct isbn format", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun createBook(): Boolean{
        try {
            title = findViewById<EditText>(R.id.et_title).text.toString()
            author = findViewById<EditText>(R.id.et_author).text.toString()
            isbn = findViewById<EditText>(R.id.et_isbnNumber).text.toString()
            rating = findViewById<RatingBar>(R.id.rb_rating).rating.toString().toFloat()
            pages = findViewById<EditText>(R.id.et_pages).text.toString().toInt()
            if (title == "" || author == "" || isbn == "") {
                throw NullPointerException()
            }

            book = Book(null, title!!, author!!, pages!!, isbn!!, rating!!)
            return true
        } catch (e: NumberFormatException) {
            Toast.makeText(applicationContext, "Fill all fields!", Toast.LENGTH_SHORT).show()
        } catch (e: NullPointerException) {
            Toast.makeText(applicationContext, "Fill all fields!", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_layout)
    }
}