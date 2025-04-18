package com.github.mvysny.kaributesting.v10

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.confirmdialog.ConfirmDialog
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.html.Span
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractDialog23_1tests() {
    @BeforeEach fun fakeVaadin() { MockVaadin.setup() }
    @AfterEach fun tearDownVaadin() { MockVaadin.tearDown() }

    @Test fun `lookup in header+footer`() {
        val dialog = Dialog()
        dialog.header.add(Span("Foo"))
        dialog.footer.add(Button("Bar"))
        dialog.open()
        _expectOne<Span> { text = "Foo" }
        _expectOne<Button> { text = "Bar" }
    }

    @Test fun `lookup in ConfirmDialog`() {
        val dlg = ConfirmDialog()
        dlg.setCancelButton(Button("Cancel"))
        dlg.setConfirmButton(Button("Confirm"))
        dlg.setRejectButton(Button("Reject"))
        dlg.open()
        _expectOne<Button> { text = "Cancel" }
        _expectOne<Button> { text = "Confirm" }
        _expectOne<Button> { text = "Reject" }
    }
}
