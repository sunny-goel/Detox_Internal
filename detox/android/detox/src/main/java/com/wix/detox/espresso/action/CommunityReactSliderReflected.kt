package com.wix.detox.espresso.action

import android.util.Log
import com.wix.detox.common.DetoxLog
import org.joor.ReflectException
import java.lang.reflect.Method

const val CLASS_REACT_SLIDER_COMMUNITY = "com.reactnativecommunity.slider.ReactSlider"
private const val METHOD_GET_PROGRESS = "getProgress"
private const val METHOD_TO_REAL_PROGRESS = "toRealProgress"

class CommunityReactSliderReflected {
    companion object {
        fun getCommunityReactSliderClass(): Class<*>? {
            return try {
                Class.forName(CLASS_REACT_SLIDER_COMMUNITY)
            } catch (e: ReflectException) {
                Log.e(DetoxLog.LOG_TAG, "could not get class for Community ReactSlider ", e)
                null
            }
        }

        fun castInstance(instance: Any): Any? {
            return try {
                getCommunityReactSliderClass()?.cast(instance)
            } catch (e: ReflectException) {
                Log.e(DetoxLog.LOG_TAG, "could not cast to Community ReactSlider ", e)
                null
            }
        }

        private fun getProgressMethod(): Method? {
            return try {
                getCommunityReactSliderClass()?.getMethod(METHOD_GET_PROGRESS)
            } catch (e: ReflectException) {
                Log.e(DetoxLog.LOG_TAG, "could not get getProgress method ", e)
                null
            }
        }

        private fun getToRealProgressMethod(): Method? {
            return try {
                getCommunityReactSliderClass()?.getMethod(
                    METHOD_TO_REAL_PROGRESS,
                    Int::class.java
                )
            } catch (e: ReflectException) {
                Log.e(DetoxLog.LOG_TAG, "could not get toRealProgress method ", e)
                null
            }
        }

        fun invokeGetProgressMethod(view: Any): Int {
            return try {
                val communitySliderInstance = castInstance(view)
                getProgressMethod()?.invoke(communitySliderInstance) as Int
            } catch (e: ReflectException) {
                Log.e(DetoxLog.LOG_TAG, "could not invoke getProgress method ", e)
                0
            }
        }

        fun invokeToRealProgressMethod(view: Any, value: Int): Double {
            return try {
                val communitySliderInstance = castInstance(view)
                getToRealProgressMethod()?.invoke(communitySliderInstance, value) as Double
            } catch (e: ReflectException) {
                Log.e(DetoxLog.LOG_TAG, "could not invoke toRealProgress method ", e)
                0.0
            }
        }
    }
}