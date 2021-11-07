---
layout: page
title: Aiken Wong Xiheng's Project Portfolio Page
---

### Project: LingoGO!

LingoGO! is a desktop application created as part of a team project for the module CS2103T (Software Engineering) at the
National University of Singapore (NUS). The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is
written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **[Filter Command](../UserGuide.md/#filtering-flashcards-by-conditions-filter) (enhancement)**: Added range
  and index filter conditions and the ability to combine multiple filter conditions ([\#107](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/107)
  , [\#130](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/130), [\#201](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/201)).
  * What it does: Allow users to specify several filter conditions at once within one filter command. The
    added index and range filter conditions also allow users to search for flashcards by their indices in the
    displayed flashcards list of [List mode](../UserGuide.md/#list-mode).
  * Justification: The enhancements made to filter command makes it a more powerful and convenient search tool for
    users. A better search functionality also helps users to more quickly select flashcards to be loaded into
    [Slideshow mode](../UserGuide.md/#slideshow-mode) for practice sessions.
  * Highlights: Enhancements made to filter command also included refactoring to allow greater ease of adding more
    additional filter conditions in the future.
* **Test Command (deprecated feature)**: Added the ability to test flashcards in [List mode](../UserGuide.md/#list-mode)
  with an answer given by the user ([\#80](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/80)).
  * What it does: Allows the user to test their knowledge with flashcards that they have made by typing in their
    answers.
  * Justification: This feature provided a means for users to interact and practise with their flashcards. It
    was a basic command meant for the first few iterations of the project.
  * Highlights: The test command is now deprecated and has been integrated into before it into the
    [answer command](../UserGuide.md/#answering-a-flashcard--answer) of [Slideshow mode](../UserGuide.md/#slideshow-mode).

* **[Storage](../DeveloperGuide.md/#storage-component) (refactoring)**: Refactored the Storage component to work for
  LingoGO!'s implementation ([\#70](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/70), [\#72](https://github.com/AY2122S1-CS2103T-T11-2/tp/pulls?q=is%3Apr+author%3Aaikenwx+is%3Aclosed)).
  * Justification: The refactoring was done to ensure correctness of Storage component with the new requirements of
    LingoGO! as well as to remove any stale references to deprecated code.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabAuthor=aikenwx&tabRepo=AY2122S1-CS2103T-T11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&tabType=authorship)

* **Project management**:
    * Recorded demo video for `v1.2`.

* **Enhancements to existing features**:
  * Refactored `Flashcard` class ([\#95](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/95)) to ensure immutable
    implementation of `isFlipped` attribute as well as its setter
    and getter methods to defend against unwanted mutations.
  * Wrote alert dialogues popups ([\#201](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/201)) to greet new users as well as to warn them when they make code-breaking changes to
    the
    data file.
  * Added functionality for flip command ([\#95](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/95)) to show and hide English Phrases in [List mode](../UserGuide.md/#list-mode)
    (deprecated).
  * Debugged `edit` feature ([\#205](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/205)) to ensure that it works correctly with `filter` and wrote integration tests.

* **Documentation**:
  * User Guide:
    * Added documentation for the current feature `filter`, as well as the deprecated features `flip` and `test` (
      [\#36](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/36)).
  * Developer Guide:
    * Added implementation details of the `filter` feature.
    * Updated UML class diagram for Storage component.
    * Added manual test cases for data file manipulation.

* **Community**:
  * PRs reviewed (with non-trivial review comments):
  [\#81](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/81)
  [\#93](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/93),
  [\#100](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/100),
  [\#104](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/104),
  [\#121](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/121),
  [\#136](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/136),
