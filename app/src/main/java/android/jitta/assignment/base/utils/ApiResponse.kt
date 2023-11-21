package android.jitta.assignment.base.utils

sealed class ApiResponse<out T> {
    class Success<T>(val data: T) : ApiResponse<T>()
    class Failure(val t: ApiException) : ApiResponse<Nothing>()
}

class ApiException(
    val error: String?
) : RuntimeException()