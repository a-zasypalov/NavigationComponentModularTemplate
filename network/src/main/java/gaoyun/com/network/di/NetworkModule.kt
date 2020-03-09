package gaoyun.com.network.di

import com.example.network.BuildConfig
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import gaoyun.com.network.service.AdviceService
import gaoyun.com.network.domain.AdviceRemoteRepositoryInteractor
import gaoyun.com.network.repository.AdviceRemoteRepository
import gaoyun.com.network.repository.AdviceRemoteRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @Provides
    internal fun provideOkHttpClient(
            httpLoggingInterceptor
            : HttpLoggingInterceptor
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.networkInterceptors().add(httpLoggingInterceptor)

        return httpClientBuilder.build()
    }

    @Provides
    internal fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder().create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    internal fun provideRetrofit(
            okHttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.adviceslip.com/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build()
    }

    @Provides
    internal fun provideApi(retrofit: Retrofit): AdviceService {
        return retrofit.create<AdviceService>(AdviceService::class.java)
    }

    @Provides
    internal fun provideAdviceRemoteRepository(adviceService: AdviceService): AdviceRemoteRepository {
        return AdviceRemoteRepositoryImpl(adviceService)
    }

    @Provides
    internal fun provideAdviceRemoteRepositoryInteractor(repository: AdviceRemoteRepository): AdviceRemoteRepositoryInteractor {
        return AdviceRemoteRepositoryInteractor(repository)
    }

}