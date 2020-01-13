@file:Suppress("FunctionName")

package com.github.mvysny.kaributesting.v10

import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.combobox.GeneratedVaadinComboBox
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.data.provider.DataCommunicator
import com.vaadin.flow.function.SerializableConsumer

/**
 * Emulates an user inputting something into the combo box, filtering items.  You can use [getSuggestionItems]
 * to retrieve those items and to verify that the filter on your data provider works properly.
 *
 * WARNING: Only works with Vaadin 12 or higher
 */
fun <T> ComboBox<T>.setUserInput(userInput: String?) {
    checkEditableByUser()
    check(ComboBox::class.java.declaredFields.any { it.name == "filterSlot" }) { "This function only works with Vaadin 12 or higher" }
    val comboBoxFilterSlot = ComboBox::class.java.getDeclaredField("filterSlot").apply { isAccessible = true }
    @Suppress("UNCHECKED_CAST")
    (comboBoxFilterSlot.get(this) as SerializableConsumer<String?>).accept(userInput)
}

/**
 * Simulates the user creating a custom item. Only works if the field is editable by the user
 * and allows custom values ([ComboBox.isAllowCustomValue] is true).
 */
fun <T> ComboBox<T>._fireCustomValueSet(userInput: String) {
    checkEditableByUser()
    check(isAllowCustomValue) { "${toPrettyString()} doesn't allow custom values" }
    _fireEvent(GeneratedVaadinComboBox.CustomValueSetEvent<ComboBox<T>>(this, true, userInput))
}

/**
 * Fetches items currently displayed in the suggestion box.
 *
 * WARNING: Only works with Vaadin 12 or higher
 */
@Suppress("UNCHECKED_CAST")
fun <T> ComboBox<T>.getSuggestionItems(): List<T> {
    check(ComboBox::class.java.declaredFields.any { it.name == "dataCommunicator" }) { "This function only works with Vaadin 12 or higher" }
    val field = ComboBox::class.java.getDeclaredField("dataCommunicator").apply { isAccessible = true }
    val dataCommunicator = field.get(this) as DataCommunicator<T>
    return dataCommunicator.fetch(0, Int.MAX_VALUE)
}

/**
 * Fetches captions of items currently displayed in the suggestion box.
 *
 * WARNING: Only works with Vaadin 12 or higher
 */
fun <T> ComboBox<T>.getSuggestions(): List<String> {
    val items = getSuggestionItems()
    return items.map { itemLabelGenerator.apply(it) }
}

/**
 * Fetches items currently displayed in the suggestion box.
 */
@Suppress("UNCHECKED_CAST")
fun <T> Select<T>.getSuggestionItems(): List<T> = dataProvider._findAll()

/**
 * Fetches captions of items currently displayed in the suggestion box.
 */
fun <T> Select<T>.getSuggestions(): List<String> {
    val items = getSuggestionItems()
    return when (itemLabelGenerator) {
        null -> items.map { it.toString() }
        else -> items.map { itemLabelGenerator.apply(it) }
    }
}
