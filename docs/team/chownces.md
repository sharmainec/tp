---
layout: page
title: Chow En Rong's Project Portfolio Page
---

### Project: LingoGO!

LingoGO! is a **desktop app** for **university students who use English as their first language** and are trying to **learn a
new language**. Our unique **Command Line Interface (CLI)** and
elegant **Graphical User Interface (GUI)** is sure to delight you, and empower you on your journey in mastering the new language you *have always wanted*.

<hr>

* **Code contributions**: View my code contributions on RepoSense [here](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=chownces&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17).

* **<u>Enhancements/Features Implemented</u>**
  1. **Refactoring of AB3 Repo to create walking-skeleton for LingoGO!**
  * PR(s): [#40](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/40), [#71](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/71), [#73](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/73)
  * Updated the data representation in `Model` to facilitate the storage and display of flashcard information.
  * Updated the existing commands and their respective parsers to facilitate CRUD operations for flashcards in the application.
    <!-- * [Commands](../UserGuide/#commands) updated include: `add`, `clear`, `delete`, `edit`, `find`, `list` and their corresponding command parsers. -->
  * Rewrote and migrated the AB3 test suite for the above components over to LingoGO!.

  1. **Slideshow feature**
  * PR(s): [#109](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/109), [#145](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/145)
  * Implemented the backend logic for the [slideshow feature](../UserGuide.html#slideshow-mode) in the `Model` and `Logic` components.
  * Wrote unit and integration tests for the new components, which improved overall test coverage by 1.15%.

  1. **Import/ Export to CSV feature**
  * PR(s): [#218](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/218)
  * Refactored the CSV handling logic for the import and export commands.
  * Debugged and fixed an UTF-8 encoding bug whereby exported CSV files from LingoGO! could not be opened correctly in Microsoft Excel, nor CSV files exported from Excel be opened correctly on LingoGO!.

* **<u>User Guide Documentation</u>**
  1. **Command Usage**
    * PR(s): [#41](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/41)
    * Updated the User Guide for the `add` and `delete` commands.

  1. **Screenshots and Overall Flow**
    * PR(s): [#149](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/149), [#200](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/200)
    * Updated the User Guide with screenshots from our updated app UI.
    * Improved the overall flow, look and content of the User Guide.

* **<u>Developer Guide Documentation</u>**
  1. **Overall Design: Architecture, Model Component, Logic Component**
    * PR(s): [#117](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/117), [#118](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/118)
    * Updated the architecture sequence diagram and links to our new repository.
    * Updated the description and 4 UML diagrams for the `Model` and `Logic` sections.
  1. **Slideshow, Import and Export Commands**
    * PR(s): [#225](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/225), [#229](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/229)
    * Updated implementation details of the Slideshow component.
    * Updated implementation details and UML diagrams for the `import` and `export` commands.
    * Added manual test cases for the `import` and `export` commands.

* **<u>Project management and Tools</u>**:
  * Set up documentation preview CI/CD on CloudFlare Pages.
  * Improved team's pull request workflow and set up pull request template on GitHub.
  * Created and assigned issues to teammates. Facilitated PED bug triage for over 50 issues with GitHub Project Board.

* **<u>Community</u>**:
  * PRs reviewed (with non-trivial review comments): [\#39](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/39), [\#80](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/80), [#93](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/93), [\#108](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/108), [\#136](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/136)
  * Total PRs reviewed: 31
  * Reported [bugs and suggestions](https://github.com/chownces/ped/issues) for team CSBook.


