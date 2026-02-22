
plugins {
    id("java")
    id("org.checkerframework").version("0.6.61")
}

project.group = "selentic-framework"
project.version = "release"

repositories {
    mavenCentral()
}

java {
    withJavadocJar()
}

checkerFramework {
    this.checkers = listOf(
        "org.checkerframework.checker.nullness.NullnessChecker",
        "org.checkerframework.checker.optional.OptionalChecker"
    )
}

dependencies {
    testImplementation("org.testng:testng:7.11.+")
    implementation("org.seleniumhq.selenium:selenium-java:4.+")
    implementation("ch.qos.logback:logback-classic:1.5.+")
    implementation("ch.qos.logback:logback-core:1.5.+")
    implementation("commons-io:commons-io:2.17.+")
    implementation("com.typesafe:config:1.4.+")
}

tasks.test {
    useTestNG()
}

tasks.javadoc {
    options.overview = "src/main/javadoc/overview.html"
}