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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UpdateActivity : Activity() {
    var id: Int = 0
    var book: Book? = null
    var title: String? = null
    var author: String? = null
    var pages: Int? = null
    var isbn: String? = null
    var rating: Float? = null

    fun updateData(view: View) {
        if(createBook()) {
            val isbn = findViewById<EditText>(R.id.et_isbnNumber).text.toString()
            if (isbnValidation(isbn)) {
                DbHandler(this).updateBook(book!!)
                finish()
            } else Toast.makeText(
                applicationContext,
                "Use correct isbn format",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun deleteData(view: View) {
        DbHandler(this).deleteBook(id)
        finish()
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

            book = Book(id, title!!, author!!, pages!!, isbn!!, rating!!)
            return true
        } catch (e: NumberFormatException) {
            Toast.makeText(applicationContext, "Fill all fields!", Toast.LENGTH_SHORT).show()
        } catch (e: NullPointerException) {
            Toast.makeText(applicationContext, "Fill all fields!", Toast.LENGTH_SHORT).show()
        }
        return false
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
        id = intent.getStringExtra("_id").toInt()
        val dbHandler = DbHandler(this)

        GlobalScope.launch {
            val book = async {
                dbHandler.getBook(id)
            }
            setData(book.await())
        }
    }
}