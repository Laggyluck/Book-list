package com.example.mybooks.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybooks.BooksAdapter
import com.example.mybooks.R
import com.example.mybooks.database.DbHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: BooksAdapter
    private val dbHandler= DbHandler(this)

    fun addBook(view: View) {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(applicationContext)
        rv_books.layoutManager = linearLayoutManager

        GlobalScope.launch {
            val books = async {
                dbHandler.getBooks()
            }
            adapter = BooksAdapter(books.await())
            rv_books.adapter = adapter
        }
    }
}
