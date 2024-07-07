package com.santoshdevadiga.sampleapp.utils

import android.content.Context
import androidx.appsearch.app.AppSearchBatchResult
import androidx.appsearch.app.AppSearchSession
import androidx.appsearch.app.PutDocumentsRequest
import androidx.appsearch.app.RemoveByDocumentIdRequest
import androidx.appsearch.app.SearchResult
import androidx.appsearch.app.SearchSpec
import androidx.appsearch.app.SearchSpec.RANKING_STRATEGY_CREATION_TIMESTAMP
import androidx.appsearch.app.SetSchemaRequest
import androidx.appsearch.localstorage.LocalStorage
import androidx.appsearch.platformstorage.PlatformStorage
import androidx.concurrent.futures.await
import androidx.core.os.BuildCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AppSearchManager(context: Context, coroutineScope: CoroutineScope) {
    private val isInitialized: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private lateinit var appSearchSession: AppSearchSession

    init {
        coroutineScope.launch {
            // Creates a [AppSearchSession], for S+ devices uses PlatformStorage, for R- devices uses
            // LocalStorage session.
            appSearchSession = if (BuildCompat.isAtLeastS()) {
                PlatformStorage.createSearchSessionAsync(
                    PlatformStorage.SearchContext.Builder(context, DATABASE_NAME).build()
                ).await()
            } else {
                LocalStorage.createSearchSessionAsync(
                    LocalStorage.SearchContext.Builder(context, DATABASE_NAME).build()
                ).await()
            }

            try {
                // Sets the schema for the AppSearch database by registering the [AppSearchData] document class as a
                // schema type in the overall database schema.
                val setSchemaRequest =
                    SetSchemaRequest.Builder().addDocumentClasses(AppSearchData::class.java).build()
                appSearchSession.setSchemaAsync(setSchemaRequest).await()

                // Set the [AppSearchDataAppSearchManager] instance as initialized to allow AppSearch operations to
                // be called.
                isInitialized.value = true

                awaitCancellation()
            } finally {
                appSearchSession.close()
            }
        }
    }

    /**
     * Adds a [AppSearchData] document to the AppSearch database.
     */
    suspend fun addAppSearchData(AppSearchData: AppSearchData): AppSearchBatchResult<String, Void> {
        awaitInitialization()

        val request = PutDocumentsRequest.Builder().addDocuments(AppSearchData).build()
        return appSearchSession.putAsync(request).await()
    }

    /**
     * Queries the AppSearch database for matching [AppSearchData] documents.
     *
     * @return a list of [SearchResult] objects. This returns SearchResults in the order
     * they were created (with most recent first). This returns a maximum of 10
     * SearchResults that match the query, per AppSearch default page size.
     * Snippets are returned for the first 10 results.
     */
    suspend fun queryLatestAppSearchDatas(query: String): List<SearchResult> {
        awaitInitialization()

        val searchSpec = SearchSpec.Builder()
            .setRankingStrategy(RANKING_STRATEGY_CREATION_TIMESTAMP)
            .setSnippetCount(10)
            .build()

        val searchResults = appSearchSession.search(query, searchSpec)
        return searchResults.nextPageAsync.await()
    }

    /**
     * Removes [AppSearchData] document from the AppSearch database by namespace and
     * id.
     */
    suspend fun removeAppSearchData(
        namespace: String,
        id: String
    ): AppSearchBatchResult<String, Void> {
        awaitInitialization()

        val request =
            RemoveByDocumentIdRequest.Builder(namespace).addIds(id).build()
        return appSearchSession.removeAsync(request).await()
    }

    /**
     * Awaits [isInitialized] being set to ```true```.
     */
    private suspend fun awaitInitialization() {
        if (!isInitialized.value) {
            isInitialized.first { it }
        }
    }

    companion object {
        private const val DATABASE_NAME = "AppSearchDatasDatabase"
    }
}