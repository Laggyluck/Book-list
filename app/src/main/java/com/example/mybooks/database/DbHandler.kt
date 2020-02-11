package com.example.mybooks.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.mybooks.models.Book

class DbHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Delete
    fun deleteBook(_id: Int) {
        val db = this.writableDatabase
        db.delete(BookContract.Books.TABLE_NAME,
            BaseColumns._ID + "=?",
            arrayOf(_id.toString())).toLong()
        db.close()
    }

    // Update
    fun updateBook(book: Book) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(BookContract.Books.COLUMN_NAME_TITLE, book.title)
            put(BookContract.Books.COLUMN_NAME_AUTHOR, book.author)
            put(BookContract.Books.COLUMN_NAME_PAGES, book.pages)
            put(BookContract.Books.COLUMN_NAME_ISBN, book.isbn)
            put(BookContract.Books.COLUMN_NAME_RATING, book.rating)
        }
        db.update(BookContract.Books.TABLE_NAME,
            values,
            BaseColumns._ID + "=?",
            arrayOf(book._id.toString())).toLong()
        db.close()
    }

    // Get All
    fun getBooks(): ArrayList<Book> {
        val booksList = ArrayList<Book>()
        val db = this.readableDatabase

        val cursor = db.query(
            BookContract.Books.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "${BaseColumns._ID} DESC"
        )
        with(cursor) {
            while (moveToNext()) {
                val book = Book(
                    cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),
                    cursor.getString(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_AUTHOR)),
                    cursor.getInt(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_PAGES)),
                    cursor.getString(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_ISBN)),
                    cursor.getFloat(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_RATING))
                )
                booksList.add(book)
            }
        }
        cursor.close()
        return booksList
    }

    // Get One
    fun getBook(_id: Int): Book? {
        val db = writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(_id.toString())

        val cursor = db.query(
            BookContract.Books.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
            )

        with(cursor) {
            while (moveToNext()) {
                return Book(
                    cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),
                    cursor.getString(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_AUTHOR)),
                    cursor.getInt(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_PAGES)),
                    cursor.getString(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_ISBN)),
                    cursor.getFloat(cursor.getColumnIndex(BookContract.Books.COLUMN_NAME_RATING))
                )
            }
        }
        cursor.close()
        return null
    }

    fun addBook(book: Book) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(BookContract.Books.COLUMN_NAME_TITLE, book.title)
            put(BookContract.Books.COLUMN_NAME_AUTHOR, book.author)
            put(BookContract.Books.COLUMN_NAME_PAGES, book.pages)
            put(BookContract.Books.COLUMN_NAME_ISBN, book.isbn)
            put(BookContract.Books.COLUMN_NAME_RATING, book.rating)
        }
        db.insert(BookContract.Books.TABLE_NAME, null, values)
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(BookContract.SQL_CREATE_BOOKS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(BookContract.SQL_DELETE_BOOKS)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Books.db"
    }

}