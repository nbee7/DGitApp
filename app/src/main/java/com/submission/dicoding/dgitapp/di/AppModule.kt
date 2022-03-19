package com.submission.dicoding.dgitapp.di

import androidx.room.Room
import com.submission.dicoding.dgitapp.BuildConfig
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.local.LocalDataSource
import com.submission.dicoding.dgitapp.data.local.room.UserDatabase
import com.submission.dicoding.dgitapp.data.remote.RemoteDataSource
import com.submission.dicoding.dgitapp.data.remote.network.ApiService
import com.submission.dicoding.dgitapp.ui.detail.DetailUserViewModel
import com.submission.dicoding.dgitapp.ui.detail.follow.FollowViewModel
import com.submission.dicoding.dgitapp.ui.detail.userrepo.UserRepositoryViewModel
import com.submission.dicoding.dgitapp.ui.favorite.FavoriteUserViewModel
import com.submission.dicoding.dgitapp.ui.home.MainViewModel
import com.submission.dicoding.dgitapp.utils.Constant.CONNECTION_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<UserDatabase>().userDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java, "GithubUser.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val loggingInterceptor = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { UserGithubRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { FavoriteUserViewModel(get()) }
    viewModel { DetailUserViewModel(get()) }
    viewModel { FollowViewModel(get()) }
    viewModel { UserRepositoryViewModel(get()) }
}