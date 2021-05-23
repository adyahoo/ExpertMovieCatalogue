package id.ac.mymoviecatalogue.core.data.source.remote

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : id.ac.mymoviecatalogue.core.data.source.remote.ApiResponse<T>()
    data class Error(val errorMessage: String) : id.ac.mymoviecatalogue.core.data.source.remote.ApiResponse<Nothing>()
    object Empty : id.ac.mymoviecatalogue.core.data.source.remote.ApiResponse<Nothing>()
}