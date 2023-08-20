package com.fesvieira.whateverweather.dependencyinjection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fesvieira.whateverweather.BuildConfig
import com.fesvieira.whateverweather.repository.UserPreferencesRepository
import com.fesvieira.whateverweather.repository.WeatherService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val WEATHER_URL = BuildConfig.WEATHER_URL

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class WeatherRetrofit

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        builder.addInterceptor(Interceptor {
            val request = it.request().newBuilder()
                .addHeader("key", BuildConfig.WEATHER_KEY)
                .build()
            return@Interceptor it.proceed(request)
        })


        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    @Singleton
    @Provides
    @WeatherRetrofit
    fun provideWeatherRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideWeatherService(@WeatherRetrofit retrofit: Retrofit): WeatherService = retrofit.create(
        WeatherService::class.java
    )

    @Provides
    fun provideUserPreferencesRepository(
        @ApplicationContext context: Context
    ): UserPreferencesRepository {
        return UserPreferencesRepository(context.dataStore)
    }
}
