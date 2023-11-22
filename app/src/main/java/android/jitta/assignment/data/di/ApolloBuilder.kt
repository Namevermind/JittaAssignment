package android.jitta.assignment.data.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

class ApolloBuilder(
    private val client: OkHttpClient
) {
    fun build(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://asia-east2-jitta-api.cloudfunctions.net/graphqlDev/")
            .okHttpClient(client)
            .build()
    }
}