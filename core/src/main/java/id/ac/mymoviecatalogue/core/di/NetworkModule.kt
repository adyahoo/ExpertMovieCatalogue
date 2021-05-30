package id.ac.mymoviecatalogue.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.mymoviecatalogue.core.data.source.remote.api.ApiService
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val hostname = "developers.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname,"sha256/kBf1bI1z28Ytbj7xOYuejUOmxHRpY24T7W8mscSQ+tI=")
            .add(hostname,"sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
            .add(hostname,"sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
            .add(hostname,"sha256/eQsTXktLvFNnaTUFCUmbo6vM/0po/RQhB/z1FIMN+rQ=")
            .build()
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}