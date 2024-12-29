package io.kdbrian.minipos.android.presentation.di

//@Module
//@InstallIn(SingletonComponent::class)
//object CoreModule {
//
//    @Provides
//    @Singleton
//    fun providesOkHttpClient() = OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val request = chain.request()
//                Timber.d("Req:${request.method} : [${request.url}]\n ${request.body}")
//                chain.proceed(chain.request())
//            }
//            .build()
//
//
//    @Provides
//    @Singleton
//    public fun providesApolloClient(okHttpClient: OkHttpClient) =
//        ApolloClient.Builder()
//            .serverUrl(BuildConfig.ngrokHost)
//            .okHttpClient(okHttpClient)
//            .build()
//
//}