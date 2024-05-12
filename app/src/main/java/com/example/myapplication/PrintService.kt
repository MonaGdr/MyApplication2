package com.example.myapplication

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class WorkerModule {
    @Provides
    fun provideAnalyticsService(
        // Potential dependencies of this type
    ): PrintService {
        return PrintServiceImpl()
    }
}

interface PrintService {
    fun print()
}

class PrintServiceImpl: PrintService {
    override fun print() {
        Log.v("Salam", "SALAM")
    }
}