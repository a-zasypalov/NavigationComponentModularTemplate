package gaoyun.com.feature_advice_screen.data

import gaoyun.com.data.Advice

sealed class AdviceUiModel {

    data class Success(val advice: Advice) : AdviceUiModel()

    data class Error(val error: String) : AdviceUiModel()

}