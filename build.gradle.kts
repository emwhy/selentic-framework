plugins {
    id("java")
}

group = "seliion-framework"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.testng:testng:7.11.+")
    implementation("org.seleniumhq.selenium:selenium-java:4.+")
    implementation("ch.qos.logback:logback-classic:1.5.+")
    implementation("ch.qos.logback:logback-core:1.5.+")
    implementation("commons-io:commons-io:2.17.+")
}

tasks.test {
    useTestNG()
}