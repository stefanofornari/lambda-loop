# Changelog

All notable changes to this project will be documented in this file.

## [0.4.0]
* Added Loop.on(Map) and Loop.on(Enumeration) support
* Improved site and documenttion (logo, layout, javadoc, getting-started, download links)

## [0.3.0]
* Feat: Added CharacterSequence support for looping over CharSequences (Strings, StringBuilders, etc.)
  ([Issue #3 - Loop over a CharSequence](https://github.com/stefanofornari/lambda-loop/issues/4))
* Docs: Added API Documentation (Javadoc) to the website
* Docs: Highlighted "Unified Iteration" benefit in introduction and README

## [0.2.2]
* Fixed [Issue #3 - No iteration with list/array of size=1](https://github.com/stefanofornari/lambda-loop/issues/3)

## [0.2.1]

* Renamed CollectionSequence to IndexedSequence
* Improved performance by suppressing stack trace creation in brk()
* Refactored sequence type hierarchy and fixed forward and backward looping

## [0.2.0] - 2025-11-16

* Added introduction and tutorial to the index
* Docs: updated documentation structure
* Renamed NumericSeries to NumericSequence
* Feat: added support to loop though Map entries
* Docs: enhanced _Array and Collection Loops_ section in getting-started.md
* Docs: udate GitHub Pages theme to Hacker
* Feat: added brk() method and updated documentation for breaking out of loops
* Deat: added support for oop though Iterable

## [0.1.0] - 2025-11-03

* Added deploy plugin and setting it up to deploy on Maven Central
* Fixed scm in pom.xml
* Renamed ArraySeries to Sequence and added support for loop(element -> {})
* Added ReturnValue holder to make is easier to handle return values inside lambdas
* Implemented step functionality for ArraySeries and updated the documentation
* Added varargs support for ArraySeries creation and updated the documentation
* Implemented step functionality for NumericSeries
* Handle empty arrays in ArraySeries loop
* Add README.md with link to documentation
