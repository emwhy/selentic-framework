![](readme_files/selentic-framework-logo.png "Selentic Framework Logo")





Selentic Framework is a test automation framework for writing web UI tests. It is designed based on Component-Object Model design (COM). It aims to help producing test automation codes that are easy to maintain and read while enabling ramping up the test automation development speed. 

Software development projects incorporate test automation to cut down on the technical debt while increasing the quality of their products. 
Causing more technical debt to maintain unstable, inconsistent, complicated, hard-to-read test automation code is exactly what we want to avoid.
This framework was designed and developed to help ensuring the test automation code remains easily readable and consistent without being unstable and flaky. 
Being able to maintain and manage test automation code efficiently is crucial to the success of any test automation project.

This framework is new. But the design concept is not new. I have utilized the similar concept in multiple test automation projects to successes for over a decade. 
The framework is accumulations and evolution of ideas from the years of my experiences building multiple test automation framework.

Selentic Framework utilizes ***Selenium*** and is written in ***Java*** (Developed on Java 21). 

## Feature Highlights

- The Component Object Model design allows custom components that are reusable, minimizing re-writing the code to do same or similar actions or copying-and-pasting, allowing less code to maintain.
- It was designed to provides library to code in strongly pattern based design that are consistent throughout. It makes the codes more predictable, legible, and easy to maintain while making it easier to spot any abnormality that could cause unexpected behaviors.
- It implements automatic wait allows the code to focus on actions and assertions rather than timing, while allowing custom waits for those more difficult cases.
- It isolates actions on page, frame, external windows, dialog, etc. using lambda expression, making it easy to spot where actions are implemented.
- It includes selector builder. This allows both CSS selector and XPath to be implemented in consistent syntax while utilizing code highlighting and code suggestions of IDE. 

## Component Object Model

The Component Object Model (COM) in test automation is a design pattern that structures tests around specific, reusable UI components rather than entire pages. It is an evolution of the traditional Page Object Model (POM), allowing for more modular, maintainable, and scalable test frameworks, especially for modern applications with dynamic user interfaces.

### Key Concept

- <ins>**Component Abstraction:**</ins> In the COM pattern, individual UI elements like buttons, text fields, navigation menus, or search bars are treated as independent objects or classes. This differs from the traditional POM, where an entire web page is represented by a single class.

- <ins>**Encapsulation:**</ins> Each component class encapsulates its own web elements (locators) and the methods (actions) that can be performed on it (e.g., clickLoginButton() within a LoginForm component). This ensures that changes to a component's UI only require updates in one specific place.

- <ins>**Reusability:**</ins> Since components are defined independently, they can be reused across multiple pages or even different projects. This reduces code duplication and speeds up test development.

- <ins>**Hierarchy:**</ins> Components can be organized into a hierarchy, with a base component defining common methods for all components (e.g., checking visibility or existence), which is then extended by specialized components.

- <ins>**Extensibility:**</ins> If a component's behavior is slightly different (e.g., wait time after entering text into a textbox), a method in a component can be overridden to change only that behavior rather than writing the entire component class. 

### Benefits

- <ins>**Improved Maintainability:**</ins> UI changes only affect the specific component's class, minimizing the need to update numerous test scripts.
- <ins>**Enhanced Scalability:**</ins> The modular nature makes it easier to manage a large number of tests and components in complex applications.
- <ins>**Better Readability and Clarity:**</ins> Tests become easier to read and understand as they interact with high-level component methods, freeing the test automation engineer to focus on test case design. 

### Implementation

The Component Object Model design pattern is often implemented using a Page Object Model, where a page object acts as a holder for various component objects. This allows test automation engineers to access component-specific action methods via the page object, creating a clear separation of concerns between test logic, page structure, and individual component behavior.  

## Setting up Selentic Framework

- Download the latest **selentic-framework.jar** file from https://github.com/emwhy/selentic-framework/releases/. The javadoc for the framework is packaged in  ***selentic-framework-javadoc.jar***. When configured, the documentation can be shown right from IDE (such as IntelliJ).
- Move the file to appropriate location in a project directory (i.e., ./lib).
- There are additional packages that Selentic Framework depends on. Add reference to these packages. If you are working with Gradle, add dependencies to **build.gradle.kts** file.
```
dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.+")
    implementation("ch.qos.logback:logback-classic:1.5.+")
    implementation("ch.qos.logback:logback-core:1.5.+")
    implementation("commons-io:commons-io:2.17.+")
    implementation("com.typesafe:config:1.4.+")
}
```
- **selentic-framework-jar** can be added like this.
```
    implementation(files("lib/selentic-framework.jar"));
```
- Reload your Gradle. You should now be able to use Selentic Framework classes.


## More about Selentic Framework
These are some more information to get to know how Selentic Framework works. 
- [Selentic Configuration >](readme_files/config.md)
- [Handling Browsers >](readme_files/browser.md)
- [Using Selector Builder >](readme_files/selector-builder.md)
- [Writing a Simple Page >](readme_files/writing-simple-page.md)
- [Writing a Component >](readme_files/writing-component.md)
- [Implementing a Dialog Component >](readme_files/implementing-dialog.md)
- [Handling Frames >](readme_files/handling-frames.md)
- [Handling Multiple Browser Windows >](readme_files/handling-windows.md)
