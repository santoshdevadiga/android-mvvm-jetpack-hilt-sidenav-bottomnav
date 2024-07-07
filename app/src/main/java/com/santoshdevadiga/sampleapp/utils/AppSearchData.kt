package com.santoshdevadiga.sampleapp.utils

import androidx.appsearch.annotation.Document
import androidx.appsearch.app.AppSearchSchema
import androidx.appsearch.app.GenericDocument

@Document
data class AppSearchData(
    /** Namespace for AppSearchData */
    @Document.Namespace
    val namespace: String = "appsearch",

    /** Id for AppSearchData */
    @Document.Id
    val id: String,

    /** Field for text that that user inputs */
    @Document.StringProperty(
        indexingType = AppSearchSchema.StringPropertyConfig.INDEXING_TYPE_PREFIXES
    )
    val text: String
)