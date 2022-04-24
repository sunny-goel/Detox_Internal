package com.wix.detox.espresso.action

import android.view.View
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.facebook.react.bridge.JavaOnlyMap
import com.facebook.react.uimanager.ReactStylesDiffMap
import com.facebook.react.views.slider.ReactSlider
import com.facebook.react.views.slider.ReactSliderManager
import com.wix.detox.espresso.action.common.ReflectUtils
import org.hamcrest.Matcher
import org.hamcrest.Matchers

private const val CLASS_REACT_SLIDER_LEGACY = "com.facebook.react.views.slider.ReactSlider"

class AdjustSliderToPositionAction(private val desiredPosition: Double, private val mManager: ReactSliderManager, private val reflectUtils: ReflectUtils) : ViewAction {
    private val communityReactSliderReflected = CommunityReactSliderReflected()
    private val communityReactSliderManagerReflected = CommunityReactSliderManagerReflected()

    override fun getConstraints(): Matcher<View?>? = Matchers.allOf(
        ViewMatchers.isAssignableFrom(AppCompatSeekBar::class.java),
        getIsDisplayed())

    override fun getDescription() = "adjustSliderToPosition"

    fun getIsDisplayed(): Matcher<View?> = ViewMatchers.isDisplayed()

    private fun buildStyles(vararg keysAndValues: Any) = ReactStylesDiffMap(JavaOnlyMap.of(*keysAndValues))

    private fun calculateProgressTarget(view: View): Double {
        val castView = view as AppCompatSeekBar
        val sliderProgress = when {
            (reflectUtils.isObjectAssignableFrom(view, CLASS_REACT_SLIDER_LEGACY)) ->
                (view as ReactSlider).toRealProgress(view.progress)
            (reflectUtils.isObjectAssignableFrom(view, CLASS_REACT_SLIDER_COMMUNITY)) -> {
                val progressValue = communityReactSliderReflected.invokeGetProgressMethod(view)
                communityReactSliderReflected.invokeToRealProgressMethod(view, progressValue)
            }
            else -> (view as ReactSlider).toRealProgress(view.progress)
        }
        val sliderScrollFactor = castView.max / view.progress.toDouble()
        val sliderMaxValue: Double = sliderProgress * sliderScrollFactor
        return desiredPosition * sliderMaxValue
    }

    override fun perform(uiController: UiController?, view: View) {
        val progressNewValue = calculateProgressTarget(view)

        if (reflectUtils.isObjectAssignableFrom(view, CLASS_REACT_SLIDER_LEGACY)) {
            mManager.updateProperties(view as ReactSlider, buildStyles("value", progressNewValue))
        }
        else if (reflectUtils.isObjectAssignableFrom(view, CLASS_REACT_SLIDER_COMMUNITY)) {
            communityReactSliderManagerReflected.invokeSetValue(view, progressNewValue)
        }
    }
}
