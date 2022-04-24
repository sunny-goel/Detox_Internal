package com.wix.detox.espresso.action

import android.util.Log
import com.wix.detox.common.DetoxLog
import org.joor.ReflectException
import java.lang.reflect.Method

private const val CLASS_REACT_SLIDER_MANAGER = "com.reactnativecommunity.slider.ReactSliderManager"
private const val METHOD_SET_VALUE = "setValue"

class CommunityReactSliderManagerReflected {
    private val communitySlider = CommunityReactSliderReflected()

    private fun getSliderManagerClass(): Class<*>? {
        return try {
            Class.forName(CLASS_REACT_SLIDER_MANAGER)
        } catch (e: Exception) {
            Log.e(DetoxLog.LOG_TAG, "could not get class for Community ReactSliderManager ", e)
            null
        }
    }

    private fun getSliderManagerInstance(): Any? {
        return try {
            getSliderManagerClass()?.newInstance()
        } catch (e: Exception) {
            Log.e(DetoxLog.LOG_TAG, "could not cast to Community ReactSliderManager ", e)
            null
        }
    }

    private fun getSetValueMethod(): Method? {
        return try {
            val communityReactSliderClass = communitySlider.getCommunityReactSliderClass()
            val sliderManagerClass = getSliderManagerClass()

            sliderManagerClass?.getMethod(
                METHOD_SET_VALUE,
                communityReactSliderClass,
                Double::class.java
            )
        } catch (e: ReflectException) {
            Log.e(DetoxLog.LOG_TAG, "could not get setValue method ", e)
            null
        }
    }

    fun invokeSetValue(view: Any, value: Double): Any? {
        return try {
            val reactSliderInstance = communitySlider.castInstance(view)
            val sliderManagerInstance = getSliderManagerInstance()
            val setValueMethod = getSetValueMethod()

            setValueMethod?.invoke(
                sliderManagerInstance,
                reactSliderInstance,
                value
            )
        } catch (e: Exception) {
            Log.e(DetoxLog.LOG_TAG, "could not invoke toRealProgress method ", e)
            0.0
        }
    }
}
