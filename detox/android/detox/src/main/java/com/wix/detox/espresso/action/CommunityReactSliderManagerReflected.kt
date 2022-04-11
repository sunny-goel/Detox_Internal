package com.wix.detox.espresso.action

import android.util.Log
import com.wix.detox.common.DetoxLog
import com.wix.detox.espresso.action.CommunityReactSliderReflected.Companion.castInstance
import com.wix.detox.espresso.action.CommunityReactSliderReflected.Companion.getCommunityReactSliderClass
import org.joor.ReflectException
import java.lang.reflect.Method

private const val CLASS_REACT_SLIDER_MANAGER = "com.reactnativecommunity.slider.ReactSliderManager"
private const val METHOD_SET_VALUE = "setValue"

class CommunityReactSliderManagerReflected {
    companion object {
        private fun getSliderManagerClass(): Class<*>? {
            return try {
                Class.forName(CLASS_REACT_SLIDER_MANAGER)
            } catch (e: ReflectException) {
                Log.e(DetoxLog.LOG_TAG, "could not get class for Community ReactSliderManager ", e)
                null
            }
        }

        private fun getSliderManagerInstance(): Any? {
            return try {
                getSliderManagerClass()?.newInstance()
            } catch (e: ReflectException) {
                Log.e(DetoxLog.LOG_TAG, "could not cast to Community ReactSliderManager ", e)
                null
            }
        }

        private fun getSetValueMethod(): Method? {
            return try {
                val communityReactSliderClass = getCommunityReactSliderClass()
                getSliderManagerClass()?.getMethod(
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
                val reactSliderInstance = castInstance(view)
                return getSetValueMethod()?.invoke(
                    getSliderManagerInstance(),
                    reactSliderInstance,
                    value
                )
            } catch (e: ReflectException) {
                Log.e(DetoxLog.LOG_TAG, "could not invoke toRealProgress method ", e)
                0.0
            }
        }
    }
}