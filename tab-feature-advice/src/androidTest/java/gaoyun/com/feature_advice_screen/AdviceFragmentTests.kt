package gaoyun.com.feature_advice_screen

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdviceFragmentTests {

    @Test
    fun onLaunchLoaderIsDisplayed() {
        launchFragmentInContainer<AdviceFragment>(null, R.style.AppTheme)
        onView(withId(R.id.pbLoading)).check(matches(isDisplayed()))
    }

}