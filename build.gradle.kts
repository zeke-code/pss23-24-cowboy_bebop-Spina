plugins {
    // Apply tha java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application in Java.
    application

    // Using SpotBugs to help streamline development
    id("com.github.spotbugs") version "6.1.7"
    // Using spotless for code formatting
    id("com.diffplug.spotless") version "7.0.2"

    /*
     * Add tasks to create a fat jar
     * To create it, run on a terminal "./gradlew shadowJar
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.gradleup.shadow") version "9.0.0-beta9"

    // JavaFX plugin to streamline JavaFX setup
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    // Use Maven Central to resolve dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter frameowrk for unit testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:33.4.0-jre")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

// Spotless configuration to use Google Formatting for Java
spotless {
    java {
        googleJavaFormat()
    }
}

application {
    // Define the main class for the application.
    mainClass.set("github.zekecode.cowboybebop.Main")
}

// Setting up JavaFX versions and needed modules
javafx {
    version = "17.0.14"
    modules = listOf(
        "javafx.controls",
        "javafx.base",
        "javafx.fxml",
        "javafx.swing",
        "javafx.graphics"
    )
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}