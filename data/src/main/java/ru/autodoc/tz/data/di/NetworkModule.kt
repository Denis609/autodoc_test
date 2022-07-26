package ru.autodoc.tz.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.autodoc.tz.data.BuildConfig
import ru.autodoc.tz.data.rep.RepApi
import ru.autodoc.tz.data.user.UserApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    companion object {
        const val baseUrl: String = "https://api.github.com/"
    }

    @Singleton
    @Provides
    fun provideRepsApi(retrofit: Retrofit): RepApi = retrofit.create(RepApi::class.java)

    @Singleton
    @Provides
    fun provideUsersApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(timeout = 15L, unit = TimeUnit.SECONDS)
            .readTimeout(timeout = 60L, unit = TimeUnit.SECONDS)
            .writeTimeout(timeout = 60L, unit = TimeUnit.SECONDS)
            .addInterceptor(interceptor = httpLoggingInterceptor)
            .addInterceptor(interceptor = Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header(name = "Accept", value = "application/json")
                return@Interceptor chain.proceed(request = builder.build())
            }).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()
}