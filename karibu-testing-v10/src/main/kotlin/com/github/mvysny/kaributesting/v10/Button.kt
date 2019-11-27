@file:Suppress("FunctionName")

package com.github.mvysny.kaributesting.v10

import com.vaadin.flow.component.button.Button
import kotlin.test.fail

/**
 * Clicks the button, but only if it is actually possible to do so by the user. If the button is read-only or disabled, it throws an exception.
 * @throws IllegalArgumentException if the button was not visible, not enabled, read-only. See [_checkClickable] for
 * more details.
 */
fun Button._click() {
    _checkClickable()
    click()  // this doesn't work on Vaadin 12 but it works properly with Vaadin 13
}

/**
 * Alias for [checkEditableByUser].
 */
fun Button._checkClickable() {
    checkEditableByUser()
}

/**
 * Fails if the button is clickable.
 * @throws AssertionError if the button is clickable.
 */
fun Button._expectNotClickable() {
    try {
        _checkClickable()
        fail("The ${toPrettyString()} is clickable")
    } catch (ex: IllegalStateException) {
        // okay
    }
}
