package com.dicoding.warceng.di

import com.dicoding.warceng.data.UmkmRepository

object Injection {
    fun provideRepository(): UmkmRepository{
        return UmkmRepository.getInstance()
    }
}