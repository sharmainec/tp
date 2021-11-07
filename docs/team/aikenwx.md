---
layout: page
title: Aiken Wong Xiheng's Project Portfolio Page
---

### Project: LingoGO!

LingoGO! is a **desktop app** for **university students who use English as their first language** and are trying to **learn a
new language**. Founded on the widely established learning technique of **spaced-repetition**, LingoGO! takes all the
benefits of pen-and-paper flashcards in learning, and brings them to the next level with our **powerful search** and **sharing
features** -- *without the hassle* of managing actual physical ones. Coupled with our unique **Command Line Interface (CLI)** and
an elegant **Graphical User Interface (GUI)** to accompany it, LingoGO! is sure to delight you, and empower you on your
journey in mastering the new language you *have always wanted*.

LingoGO! was created as part of a team project for the module CS2103T (Software Engineering) at the National University of Singapore (NUS).

Given below are my contributions to the project.

<hr/>

### Code Contributions

Click [here](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=aikenwx&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false)
to view my contributions.

<hr/>

### Enhancements/Features Implemented

Below is a summary of the enhancements I have implemented in the project.


* **Refactored Storage component**
  * PR(s): [\#70](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/70), [\#72](https://github.com/AY2122S1-CS2103T-T11-2/tp/pulls?q=is%3Apr+author%3Aaikenwx+is%3Aclosed)
  * Refactored the Storage component to work for
  LingoGO!'s requirements .
  * All references of AB3 code were also removed from Storage component.
* **Added test feature**
  * PR(s): [\#80](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/80)
  * Added the ability to test flashcards in List mode
    with an answer given by the user.
  * This feature provided a means for users to interact and practise with their flashcards. It
    was a basic command meant for the first two iterations of the project.
  * This feature is now deprecated and has been integrated into the
    `answer` command of Slideshow mode.
* **Refactored `Flashcard` class**
  * PR(s): [\#95](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/95), [\#107](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/107)
  * Refactored `Flashcard` class to ensure immutable implementation of `isFlipped` attribute as well as its setter
    and getter methods to defend against unwanted mutations.
  * Updated `isSameFlashcards` method to allow for the checking of duplicate flashcards.
* **Enhanced flip feature**
  * PR(s): [\#95](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/95)
  * Added functionality for flip command to show and hide English phrases in List mode.
  * This feature provided a basic means for users to interact with the digital flashcards, as if with real
    flashcards by 'flipping' them over.
  * This feature is now deprecated and has been integrated into the
    `answer` command of Slideshow mode.
* **Enhanced filter feature**
  * PR(s): [\#107](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/107),
    [\#130](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/130), [\#201](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/201)
  * Added range and index filter conditions and the ability to combine multiple filter conditions.
  * This provides greater convenience for users, allowing them to specify several filter conditions at once within one
    filter command.
  * Search flexibility is also enhanced through the filter conditions, allowing users to more quickly select
    flashcards to be loaded into Slideshow mode for practice sessions.
  * Other enhancements made to filter command also include refactoring to allow greater ease of adding more
    additional filter conditions in the future.
* **Enhanced alert feature**
  * PR(s): [\#201](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/201)
  * Wrote alert dialogue popups to greet new users as well as to warn users when they make code-breaking changes to
    the JSON data file.
  * Such alerts can help users to troubleshoot if they have made
    unintended changes to the data file.
* **Debugged edit feature**
  * PR(s): [\#205](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/205)
  * Helped to debug `edit` command to ensure that it works correctly with `filter` command.
  * Added integration tests for `edit` and `filter` command to increase total test coverage.

<hr/>

### Documentation

Below is a summary of my contributions to the project's documentation.

#### User Guide
* **Commands**
  * PR(s): [\#36](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/36), [\#107](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/107),
    [\#130](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/130)
  * `filter` command
  * `flip` command (deprecated)
  * `test` command (deprecated)
* **Command Format**:
  * PR(s): [\#225](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/225)
  * Wrote about the command format to give a detailed breakdown on how LingoGO!'s commands are formatted.

#### Developer Guide

* **Feature Implementation: Filter feature**:
  * PR(s): [\#116](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/116/commits)
  * Wrote up description and implementation details for the `filter` command.
  * Prepared UML sequence diagrams
* **Overall Design: Storage component**:
  * PR(s): [\#116](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/116/commits)
  * Updated overall description of the Storage component.
  * Updated UML class diagram for Storage component.
* **Manual testing**:
  * PR(s): [\#200](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/200/commits)
  * Added manual test cases to test the LingoGO!'s behavior after manipulation of JSON data file.

<hr/>

### Project management

Below are some of the team-based tasks I have contributed to.

* Recorded demo video for v1.2 of LingoGO!.
* Participated in team meetings and code reviews.

<hr/>

### Community

Below are some of the things I have done to help others during this module.

* **Community**:
  * PRs reviewed (with non-trivial review comments):
  [\#81](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/81)
  [\#93](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/93),
  [\#100](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/100),
  [\#104](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/104),
  [\#121](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/121),
  [\#136](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/136)
  * Reported [bugs and suggestions](https://github.com/aikenwx/ped/issues) for team SWEe-book, another team in the module.

<hr/>
