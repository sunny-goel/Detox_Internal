package com.wix.detox.espresso.action.common

import javax.annotation.Nonnull

class ReflectUtils {
    fun isObjectAssignableFrom(@Nonnull source: Any, target: String): Boolean {
        return Class.forName(target).isAssignableFrom(source.javaClass)
    }
}