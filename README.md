AutoHero
The project is coded in Java and Selenium using IntelliJ IDE. Chrome drriver is used to launch the driver. Page object model is used as a structure of automation. In project, under the common folder there are two classes that contains base methods. BaseTest class contains chrome driver settings and AutoHero url navigation methods. BaseLibrary class contains general prject needs, driver settings, exceptions, screenshot, waitings for elements etc. These may not be used in project. However, many methods are needed during the automation projects. That is why it is one of my project's base. Under Pages director, there are two Page classes. MainPage and Search Page. MainPage is always an entrance page. On my task, I don't have any operation. On SearchPage class, each operation has own method. There are step explenations at the beginning of methods. Under test directory, there are 2 scenarios. for each verification of search results.

Running the tests
testng.xml file is used to run the tests. Or in scenario java class, you can run tests one by one.

/****************************************************

Date: 2019-08-07
Writer: Zehra YILDIZ
****************************************************/
