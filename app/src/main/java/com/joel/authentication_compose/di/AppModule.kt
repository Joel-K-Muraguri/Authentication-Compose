package com.joel.authentication_compose.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.joel.authentication_compose.auth.AuthRepo
import com.joel.authentication_compose.auth.AuthRepoImplementation
import com.joel.authentication_compose.network.ApiService
import com.joel.authentication_compose.utils.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApiService() : ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app : Application) : SharedPreferences{
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(apiService: ApiService, prefs: SharedPreferences): AuthRepo {
        return AuthRepoImplementation(apiService, prefs)
    }

}

