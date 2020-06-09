package com.github.mvysny.kaributesting.v10

import com.github.mvysny.dynatest.DynaNodeGroup
import com.github.mvysny.kaributesting.v10.pro.confirmDialogTestbatch
import com.github.mvysny.kaributesting.v10.pro.gridProTestbatch
import java.util.*

fun DynaNodeGroup.allTests() {
    beforeEach {
        // make sure that Validator produces messages in English
        Locale.setDefault(Locale.ENGLISH)
    }

    group("routes test") {
        routesTestBatch()
    }
    group("basic utils") {
        basicUtilsTestbatch()
    }
    group("HasValue utils") {
        hasValueTestbatch()
    }
    group("combo box") {
        comboBoxTestbatch()
    }
    group("grid") {
        gridTestbatch()
    }
    group("tree grid") {
        treeGridTestbatch()
    }
    group("iron list") {
        ironListTestbatch()
    }
    group("locator addons") {
        locatorAddonsTestbatch()
    }
    group("locatorj") {
        locatorJTest()
    }
    group("locator") {
        locatorTest()
    }
    group("mock vaadin") {
        mockVaadinTest()
    }
    group("polymer template") {
        polymerTemplateTest()
    }
    group("pretty print tree") {
        prettyPrintTreeTest()
    }
    group("search spec") {
        searchSpecTest()
    }
    group("notifications") {
        notificationsTestBattery()
    }
    group("context menu") {
        contextMenuTestbatch()
    }
    group("navigator test") {
        navigatorTest()
    }
    group("router link tests") {
        routerLinkBatch()
    }
    group("download test battery") {
        downloadTestBattery()
    }
    group("grid pro") {
        gridProTestbatch()
    }
    group("ConfirmDialog") {
        confirmDialogTestbatch()
    }
    group("upload") {
        uploadTestbatch()
    }
    group("login form test") {
        loginFormTestbatch()
    }
    group("binder test") {
        binderTestbatch()
    }
    group("form layout test") {
        formLayoutTest()
    }
    group("menu bar") {
        menuBarTestbatch()
    }
}
