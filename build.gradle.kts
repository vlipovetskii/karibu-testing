import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
    signing
}

defaultTasks("clean", "build")

if (JavaVersion.current() < JavaVersion.VERSION_17) {
    throw GradleException("Karibu-Testing 2+ requires JDK 17; current JDK is ${JavaVersion.current()}")
}

allprojects {
    group = "com.github.mvysny.kaributesting"
    version = "2.1.7-SNAPSHOT"
    repositories {
        mavenCentral()
        maven(url = "https://maven.vaadin.com/vaadin-addons")
        maven(url = "https://maven.vaadin.com/vaadin-prereleases/")
        maven(url = "https://repo.spring.io/milestone")
    }
    tasks.withType<KotlinCompile> {
        compilerOptions.jvmTarget = JvmTarget.JVM_17
    }
}

subprojects {
    apply {
        plugin("maven-publish")
        plugin("kotlin")
        plugin("org.gradle.signing")
    }

    // creates a reusable function which configures proper deployment to Maven Central
    ext["configureMavenCentral"] = { artifactId: String ->

        java {
            withJavadocJar()
            withSourcesJar()
        }

        tasks.withType<Javadoc> {
            isFailOnError = false
        }

        publishing {
            repositories {
                maven {
                    setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                    credentials {
                        username = project.properties["ossrhUsername"] as String? ?: "Unknown user"
                        password = project.properties["ossrhPassword"] as String? ?: "Unknown user"
                    }
                }
            }
            publications {
                create("mavenJava", MavenPublication::class.java).apply {
                    groupId = project.group.toString()
                    this.artifactId = artifactId
                    version = project.version.toString()
                    pom {
                        description = "Karibu Testing, support for browserless Vaadin testing in Kotlin"
                        name = artifactId
                        url = "https://github.com/mvysny/karibu-testing"
                        licenses {
                            license {
                                name = "The Apache Software License, Version 2.0"
                                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                                distribution = "repo"
                            }
                        }
                        developers {
                            developer {
                                id = "mavi"
                                name = "Martin Vysny"
                                email = "martin@vysny.me"
                            }
                        }
                        scm {
                            url = "https://github.com/mvysny/karibu-testing"
                        }
                    }

                    from(components["java"])
                }
            }
        }

        if (project.properties["signing.keyId"] != null) {
            signing {
                sign(publishing.publications["mavenJava"])
            }
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            // to see the exceptions of failed tests in CI console.
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
