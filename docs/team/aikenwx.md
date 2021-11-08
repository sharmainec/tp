---
layout: page
title: Aiken Wong Xiheng's Project Portfolio Page
---

### Project: LingoGO!

LingoGO! is a **desktop app** for **university students who use English as their first language** and are trying to **learn a
new language**. Our unique **Command Line Interface (CLI)** and
elegant **Graphical User Interface (GUI)** is sure to delight you, and empower you on your journey in mastering the new language you *have always wanted*.

<hr/>

* **Code contributions**: View my code contributions on RepoSense [here](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=aikenwx&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false)

* **<u>Enhancements/Features Implemented</u>**
  1. **Refactored Storage component**
  * PR(s): [\#70](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/70), [\#72](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/72)
  * Refactored the Storage component to work for LingoGO!'s requirements.
  * All references of AB3 code were also removed from Storage component.
  1. **Added test feature** *(deprecated)*
  * PR(s): [\#80](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/80)
  * Added the ability for users to test themselves with flashcards.
  * This provided a basic means for users to practise with their flashcards.
  1. **Refactored `Flashcard` class**
  * PR(s): [\#95](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/95), [\#107](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/107)
  * Refactored `Flashcard` class to ensure immutable implementation of setter and getter methods of `isFlipped` 
    attribute to defend against unwanted mutations.
  * Updated `isSameFlashcard` method to allow for the checking of duplicate flashcards.
  1. **Enhanced flip feature** *(deprecated)*
  * PR(s): [\#95](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/95)
  * Added functionality for flip command to show and hide English phrases.
  * This allowed users to interact with their flashcards by "flipping" them.
  1. **Enhanced filter feature**
  * PR(s): [\#107](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/107),
    [\#130](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/130), [\#201](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/201)
  * Added range and index filter conditions and enabled combining of multiple filter conditions.
  * This provides greater convenience for users, allowing them to specify several filter conditions at once within one
    filter command.
  * Search flexibility is also enhanced through the added filter conditions, allowing users to more quickly select
    flashcards to be loaded into Slideshow mode for practice sessions.
  1. **Added alert dialogues**
  * PR(s): [\#201](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/201)
  * Wrote alert dialogue popups to greet new users as well as to warn users when they make code-breaking changes to
    the JSON data file.
  * Such alerts help users to troubleshoot against unintended data file changes.
* **<u>User Guide Documentation</u>**
  1. **Command format**:
  * PR(s): [\#225](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/225)
  * Adapted the _Notes about the command format_ section into a fully fledged out _Command format_ section to give a 
    detailed breakdown on how LingoGO!'s commands are formatted.
  1. **Command usage**
  * PR(s): [\#36](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/36), [\#107](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/107),
    [\#130](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/130)
  * Updated the User Guide for the `filter`, `flip` (deprecated) and `test` (deprecated) commands. 
* **<u>Developer Guide Documentation</u>**
  1. **Overall design: Storage component**:
  * PR(s): [\#116](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/116/commits)
  * Updated overall description of the Storage component.
  * Updated UML class diagram for Storage component.
  1. **Filter feature**:
  * PR(s): [\#116](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/116/commits)
  * Wrote description and implementation details for the `filter` command.
  * Prepared UML sequence diagrams for `filter` command.
  1. **Manual testing**:
  * PR(s): [\#200](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/200/commits)
  * Added manual test cases to test LingoGO!'s behavior after manipulation of JSON data file.
* **<u>Community</u>**:
  * Recorded demo video for v1.2 of LingoGO!.
  * PRs reviewed (with non-trivial review comments):
  [\#81](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/81)
  [\#93](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/93),
  [\#100](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/100),
  [\#104](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/104),
  [\#121](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/121),
  [\#136](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/136)
  * Total PRs reviewed: 13
  * Reported [bugs and suggestions](https://github.com/aikenwx/ped/issues) for team SWEe-book, another team in the module.
