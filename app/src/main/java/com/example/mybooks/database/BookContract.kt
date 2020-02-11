package com.example.mybooks.database

import android.provider.BaseColumns

object BookContract {

    object Books : BaseColumns {
    const val TABLE_NAME = "books"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_AUTHOR = "author"
    const val COLUMN_NAME_PAGES = "pages"
    const val COLUMN_NAME_ISBN = "isbn"
    const val COLUMN_NAME_RATING = "rating"
}

    const val SQL_CREATE_BOOKS =
        "CREATE TABLE ${Books.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Books.COLUMN_NAME_TITLE} TEXT," +
                "${Books.COLUMN_NAME_AUTHOR} TEXT," +
                "${Books.COLUMN_NAME_PAGES} NUMBER," +
                "${Books.COLUMN_NAME_ISBN} TEXT UNIQUE," +
                "${Books.COLUMN_NAME_RATING} NUMBER)"

    const val SQL_DELETE_BOOKS = "DROP TABLE IF EXISTS ${Books.TABLE_NAME}"
}