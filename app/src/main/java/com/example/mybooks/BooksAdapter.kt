package com.example.mybooks

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.Activities.UpdateActivity
import com.example.mybooks.models.Book
import kotlinx.android.synthetic.main.book_layout.view.*

class BooksAdapter(private val books: ArrayList<Book>) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var view: View = view
        private var book: Book? = null
        init {
            view.setOnClickListener(this)
        }

        fun bindBook(book: Book) {
            this.book = book
            view.tv_title.setText(book.title)
            view.tv_author.setText(book.author)
            view.tv_pages.append(" " + book.pages.toString())
            view.tv_isbn.setText(book.isbn)
            view.tv_rating.append(book.rating.toString())
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            val updateBookIntent = Intent(context, UpdateActivity::class.java)
            updateBookIntent.putExtra("_id", book!!._id.toString())
            context.startActivity(updateBookIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.book_layout, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemBook = books[position]
        holder.bindBook(itemBook)
    }

    override fun getItemCount() = books.size
}