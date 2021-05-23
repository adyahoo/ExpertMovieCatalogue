package id.ac.mymoviecatalogue.core.data

import id.ac.mymoviecatalogue.core.data.source.remote.ApiResponse
import id.ac.mymoviecatalogue.core.utils.AppExecutors
import id.ac.mymoviecatalogue.core.vo.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>(private val executor: AppExecutors) {
    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Resource.success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.error<ResultType>(apiResponse.errorMessage, null))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.success(it) })
        }
    }

    protected fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}