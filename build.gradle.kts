plugins {
    id("java")
}

group = "selentic-framework"
version = "release"

repositories {
    mavenCentral()
}

java {
    withJavadocJar()
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