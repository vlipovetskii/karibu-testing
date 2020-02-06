import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributesting.v10.VaadinMeta
import com.github.mvysny.kaributesting.v10.allTests
import com.github.mvysny.kaributesting.v10.jvmVersion
import java.net.URL
import kotlin.test.expect

class AllTests : DynaTest({
    if (jvmVersion < 13) {

        test("flow-build-info.json exists") {
            val res: URL? = Thread.currentThread().contextClassLoader.getResource("META-INF/VAADIN/config/flow-build-info.json")
            expect(true, "flow-build-info.json is not on the classpath!") { res != null }
        }

        group("Vaadin env") {
            beforeEach { MockVaadin.setup() }
            afterEach { MockVaadin.tearDown() }

            test("Vaadin version") {
                expect(15) { VaadinMeta.version }
                expect(false) { VaadinMeta.isCompatibilityMode }
            }
        }
        allTests()
    } else {
        // Sorry, no support for Vaadin 15 on Java 13 or higher: https://github.com/mvysny/karibu-testing/issues/29
    }
})
