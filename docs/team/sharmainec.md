---
layout: page
title: Sharmaine Cheong Shi Min's Project Portfolio Page
---

### Project: LingoGO!

LingoGO! is a desktop application created as part of a team project for the module CS2103T (Software Engineering) at the
National University of Singapore (NUS). The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is
written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to find flashcards.
    * What it does: It allows the user to find flashcards in LingoGO! with english and/or foreign keywords. Flashcards matching the keywords will be shown in the displayed flashcard list.
    * Justification: This feature improves the product significantly because a user can find specific flashcards in LingoGO! instead of scrolling through the entire list of flashcards.
    * Highlights: This feature allows the user to search through flashcards by its english or foreign keywords. The implementation of `find` works differently for english and foreign keywords. The `find` for english keyword is not case-sensitive but the `find` for foreign keyword is case-sensitive. Additionally, `find` also supports multiple keywords.

* **New Feature**: Added the ability to list flashcards.
    * What it does: It allows the user to generate a random list of flashcards.
    * Justification: This feature improves the product significantly because a user can randomly generate a list of flashcards. The user can then use this randomly generated list of flashcards in the slideshow mode to test themselves.
    * Highlights: Depending on the user input, this feature will work differently. If there are no extraneous inputs after `list` it will return the list of flashcards in LingoGO!. However, if a valid integer, n, comes after the `list`, a new list of randomly selected n flashcards will be shown in the displayed flashcard list. 

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=sharmainec&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&zFR=false&tabAuthor=sharmainec&tabRepo=AY2122S1-CS2103T-T11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&tabType=authorship)


* **Enhancements to existing features**:
    * Updated the GUI color scheme (Pull requests [\#141](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/141), [\#146](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/146))

* **Documentation**:
    * User Guide:
        * Added documentation for the features `list ` and `find` (Pull requests [\#108](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/108), [\#122](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/122))
    * Developer Guide:
        * Added implementation details of the `list` and `find` features. (Pull requests [\#108](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/108), [\#122](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/122))
        * Added sequence diagram for `list` and `find` features [\#108](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/108), [\#122](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/122))
        * Added class diagram for `SlideShowApp` (Pull request [\#200](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/200/commits/8ed658a7789543cc5e804949df0fb7eedaee6de9))

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#109](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/109)
    * PRs reviewed (with trivial review comments): [\#73](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/73), [\#93](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/93), [\#100](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/100), [\#109](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/109), [\#191](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/191), [\#198](https://github.com/AY2122S1-CS2103T-T11-2/tp/pull/198)
