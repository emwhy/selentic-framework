# Selion Framework

Selion Framework is a test automation framework based on Component-Object Model design (COM). It aims to help producing test automation codes that are easy to maintain and read while ramping up the test automation development speed. 

The ease to read and maintain the test automation code is crucial to success of any test automation projects. Software development projects incorporate test automation to cut down on the technical debt. Adding more technical debt by having to maintain complex, illegible code is exactly what we want to avoid.

Selion Framework utilizes ***Selenium*** and is written in ***Java*** (Developed on Java 21). 

## Feature Highlights

- The Component Object Model design allows custom components that are reusable, minimizing re-writing the code to do same or similar actions or copying-and-pasting, allowing less code to maintain.
- It was designed to provides library to code in strongly pattern based design that are consistent throughout. It making the codes more predictable, legible, and easy to maintain while making it easier to spot any abnormality that could cause unexpected behaviors.
- It implements automatic wait allows the code to focus on actions and assertions rather than timing, while allowing custom waits for those more difficult cases.
- It forces indentations for actions on page, frame, external windows, dialog, etc. making it easy to spot where actions are happening.
- It includes selector builder. This allows both CSS selector and XPath to be implemented in consistent manner while utilizing code highlighting and code suggestions of IDE. 

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

## Setting up Selion Framework

- Download the latest **selion-framework.jar** file from https://github.com/emwhy/selion-framework/releases/. The release also contains ***selion-framework-javadoc.jar*** file that contains detailed documentation. When configured, the documentation can be shown right from IDE (such as IntelliJ).
- Move the file to appropriate location in your project directory (i.e., ./lib).
- There are additional packages that Selion depends on. Add reference to these packages. If you are working with Gradle, add dependencies to **build.gradle.kts** file.
```
dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.+")
    implementation("ch.qos.logback:logback-classic:1.5.+")
    implementation("ch.qos.logback:logback-core:1.5.+")
    implementation("commons-io:commons-io:2.17.+")
    implementation("com.typesafe:config:1.4.+")
}
```
- **selion-framework-jar** can be added also.
```
    implementation(files("lib/selion-framework.jar"));
```
- Reload your Gradle. You should now be able to use Selion classes.

## Writing a Simple Page Class

Below is a simple login page.

[login.htm](readme_files/login.htm)

Below is the page class for this login page, written with Selion Framework.

```
(imports and package are omitted)

public class SnLoginPage extends SnPage {
    private static final SnCssSelector USERNAME_TEXTBOX = _cssSelector.descendant(_id("username"));
    private static final SnCssSelector PASSWORD_TEXTBOX = _cssSelector.descendant(_id("password"));
    private static final SnCssSelector LOGIN_BUTTON = _cssSelector.descendant(_tag("button"), _type().is("submit"));
    
    @Override
    protected void waitForDisplayed() {
        waitForComponent(userNameTextbox);
    }
    
    public final SnTextbox userNameTextbox = $textbox(USERNAME_TEXTBOX);
    public final SnTextbox passwordTextbox = $textbox(PASSWORD_TEXTBOX);
    public final SnButton loginButton = $button(LOGIN_BUTTON);
}
```

- All Selion classes are prefixed with "Sn". This makes it clear about classes that are part of Selion Framework.
- All page class must extend from **SnPage** or one of its subclass. This provides access to properties and methods for 
 constructing selectors and defining components.
- Selectors are defined at the top. Selectors can be either **SnCssSelector** or **SnXPath**. Regardless of which is used, the syntax is similar.
Generally CSS selectors are faster while XPath offer more complex features.
- Since textbox and button are both standard HTML form element, they are already defined in Selion Framework.
- Both selector object and component object name ends with the component type (i.e., USERNAME_**TEXTBOX**, username**Textbox**). This practice is recommended to keep the test code easily legible.
- Overriding **waitForDisplayed** is optional. The page would automatically wait for the page to complete loading. However, if additional wait is needed (i.e., Ajax based components require more wait to fully load), that can be implemented here. In this case, in addition to waiting for the page to load, it also waits for the username textbox to be displayed.  

Once the page is defined, writing a test is straight forward (Written with TestNG).

```
(imports and package are omitted)

public class SnExampleTest {
    private final SnWithPage<SnLoginPage> loginPage = SnPage.with(SnLoginPage.class);

    @BeforeClass
    public void setup() {
        Selion.open("file://" + System.getProperty("user.dir") + "/build/resources/main/html/login.htm");
    }

    @AfterClass(alwaysRun = true)
    public void finish() {
        Selion.quit();
    }

    @Test
    public void testLogin() {
        loginPage.inPage(p -> {
            p.userNameTextbox.enterText("test");
            p.passwordTextbox.enterText("test");
            p.loginButton.click();
        });
    }
}
```

- The pages being used is defined at the top using **SnWithPage** class. This gives access to the page content by using **inPage()** method.
- When entering **inPage()** method, the page waits for the page to load without additional code. If **waitForDisplayed** of the page is overridden, that would also be executed.
- Because components are defined with respective component type, only relevant methods are available, like *click* for button and *enterText* for textbox.
- Before actions take place (i.e., click, enterText), the framework automatically checks for component's existence, ensure that it is displayed, check to ensure it is enabled (if needed), then scroll to the component.
This is to keep the test code focused only on actions, without cluttering it up with other code like checking for displayed or scrolling to component.

## Writing a Component Class

The previous example only contained simple components. But the example below adds a dropdown.

![login-enhanced-page.png](readme_files/login-enhanced-page.png)

The dropdown on this page is SlimSelect, which is stylized and provides more enhanced user experiences than standard select dropdown.

![slimselect-source.png](readme_files/slimselect-source.png)

Looking at the source doe, while the select element does exist on the page, it is not visible, so any attempt to directly interact with it would throw error. 
The actual visual elements are div tag under the select.

Because it is not a standard select dropdown, it needs to be defined.

