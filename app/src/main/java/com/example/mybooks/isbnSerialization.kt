package com.example.mybooks

fun isbnValidation(isbn: String): Boolean{
    var valid = false
    val regex = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})" +
            "[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)" +
            "?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$"

    if (isbn.matches(regex.toRegex())) valid=true

    return valid
}
