package com.wix.detox.common

import com.facebook.react.views.slider.ReactSlider
import com.facebook.react.views.slider.ReactSliderManager
import com.wix.detox.espresso.action.common.ReflectUtils
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import org.mockito.kotlin.*

private const val TEST_CLASS = "com.facebook.react.views.slider.ReactSlider"

class ReflectUtilsTest {
    @Test
    fun `should return true for match`() {
        val reactSlider: ReactSlider = mock()
        assertThat(ReflectUtils.isObjectAssignableFrom(reactSlider, TEST_CLASS)).isEqualTo(true)
    }

    @Test
    fun `should return false for no match`() {
        val reactSlider: ReactSliderManager = mock()
        assertThat(ReflectUtils.isObjectAssignableFrom(reactSlider, TEST_CLASS)).isEqualTo(false)
    }
}