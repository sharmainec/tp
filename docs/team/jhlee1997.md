---
layout: page
title: Lee Jae Ho's Project Portfolio Page
---

### Project: LingoGO!

LingoGO! is a desktop application created as part of a team project for the module CS2103T (Software Engineering) at the
National University of Singapore (NUS). The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is
written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to export displayed flashcards to a CSV file. ([\#93](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/93)
  , [\#100](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/100), [\#136](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/136), [\#191](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/191))
  * What it does: allows the user to provide a CSV file name to export all displayed flashcards into.
  * Justification: This feature improves the product significantly because a user might have multiple devices for LingoGO! and the app should provide a convenient way to sync up hundreds of flashcards.
  * Highlights: Many bugs surfaced when users interact with the exported CSV files in Microsoft Excel, which uses Windows-1252 (Western European languages) as a default setting for text/csv import. Thus, flashcards in LingoGO! containing foreign languages (encoded in UTF-8 format) were not properly displayed in the Excel. Furthermore, it was also a task to account for the different rules for valid file names in different Operating Systems.
  * Credits: CSVWriter in the [OpenCSV](http://opencsv.sourceforge.net/) was used to format the CSV file created.

* **New Feature**: Added the ability to import new flashcards from a CSV file. ([\#93](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/93)
  , [\#100](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/100), [\#136](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/136), [\#191](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/191), [\#217](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/217))
  * What it does: allows the user to specify a CSV file to import all new flashcards into LingoGO!.
  * Justification: This feature improves the product significantly because a user might wish to add a ready-made dictionary into LingoGO! and the app should provide a convenient way to add hundreds of new flashcards.
  * Highlights: Various scenarios emerged during an I/O process. For example, the CSV file can be empty, its header or content can be invalid, or might have special characters embedded to mark the file as written under the UTF-8 format. It was a challenge to account for individual cases.
  * Credits: CSVReader in the [OpenCSV](http://opencsv.sourceforge.net/) was used to process the CSV file given.
  
* **New Feature**: Added the ability to specify Language for each flashcard. ([\#100](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/100))
  * What it does: allows the user to specify a langauge type for each flashcard to be created or edited.
  * Justification: This feature improves the product significantly because a user might have flashcards of multiple languages and the app should provide a convenient way to display only the cards of their chosen language.
  * Highlights: This enhancement affects existing commands and commands to be added in future. Thus, the implementation was challenging as it required changes to existing commands. Futhermore, it was difficult to find a balance between freely naming a language type and yet validating the correctness of the given name.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=jhlee1997&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17)

* **Project management**:
  * Contributed to releases `v1.2` - `v1.4` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Initially created a Filter command with a prefix `/l` for language type such that only the flashcards of a certain language is displayed on the LingoGO!. ([\#100](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/100))
  * Wrote additional tests for existing features to increase coverage. This includes tests for valid CSV file name, test CSV files for Import and Export, etc. ([\#136](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/136), [\#191](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/191))
  
* **Documentation**:
  * User Guide:
    * Added documentation for the current feature `import` and `export`. ([\#32](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/32), [\#60](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/60), [\#198](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/198), [\#200](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/200))
  * Developer Guide:
    * Added implementation details of the `import` and `export` features. ([\#124](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/124))
    * Added the Use Case Diagram ([\#225](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/225))
    
* **Community**:
  * PRs reviewed: [\#28](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/28), [\#30](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/30), [\#92](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/92), [\#94](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/94), [\#106](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/106), [\#114](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/114), [\#145](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/145), [\#195](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/195), [\#196](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/196), [\#205](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/205), [\#222](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/222)
