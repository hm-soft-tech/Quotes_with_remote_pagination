package com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper

import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Images_entity

interface Mapping<F,T> {

    fun map(f: F): T

}

fun <F,T> Mapping<F,T>.mapall(list: List<F>)= list.map { map(it) }
