package gaoyun.com.network.responses

import com.google.gson.annotations.SerializedName

data class AdviceObject(
        val slip: SlipObject
)

data class SlipObject(
        @SerializedName("slip_id")
        val slipId: String,
        val advice: String
)