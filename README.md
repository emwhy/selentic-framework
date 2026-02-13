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

