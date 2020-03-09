package gaoyun.com.network.responses

data class ErrorResponse(
        val message: ErrorMessage
)

data class ErrorMessage(
        val type: String,
        val text: String
)