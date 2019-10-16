package com.mobileapps.popularmoviesmvvm.model.datasource.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MovieDbApiHelper {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY    = "4f9c18edc7a03e1e4444fae0a16350a1"
    const val BASE_IMAGE = "https://image.tmdb.org/t/p/w342"
    const val BASE_TRAILER = "https://www.youtube.com/watch?v="
    const val DB_MOVIE_DETAIL_PATH = "movie/{movieId}"
    const val DB_POPULAR_MOVIES_PATH = "movie/popular"
    const val DB_MOVIE_TRAILERS_PATH = "movie/{movieId}/videos"



    fun getApi() : MovieDbApi{

        val requestInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDbApi::class.java)


    }
}