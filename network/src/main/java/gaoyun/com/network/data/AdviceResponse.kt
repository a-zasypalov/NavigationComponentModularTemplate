package gaoyun.com.network.data

import com.google.gson.annotations.SerializedName

data class AdviceObject(
        val slip: SlipObject? = null,
        @SerializedName("message")
        val error: ErrorMessage? = null
)

data class SlipObject(
        @SerializedName("slip_id")
        val slipId: String,
        val advice: String
)

data class ErrorMessage(
        val type: String,
        val text: String
)