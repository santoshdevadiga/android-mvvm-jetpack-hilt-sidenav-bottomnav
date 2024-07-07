package com.santoshdevadiga.sampleapp.repository.remote

sealed class APIResult<T> (val data:T?=null,val errormessage: String?=null){
    class Loading<T>: APIResult<T>()
    class Success<T>(data:T): APIResult<T>(data=data)
    class Error<T>(errormessage: String): APIResult<T>(errormessage=errormessage)
}