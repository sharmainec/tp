---
layout: page
title: User Guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------
## About LingoGO!
LingoGO! is a **desktop app for university students who use English as their first language and are trying to learn a new language, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). With digital flashcards, LingoGO! can make learning faster and more convenient compared to using traditional flashcards.

LingoGO! currently supports *all languages* that can be represented on your computer and has the following main features:
* addition, deletion, and editing of flashcards
* finding and filtering of flashcards by keywords and conditions
* importing and exporting of flashcards to be shared with others
<!-- * slideshow mode -->

Detailed information about these features can be found under the [Features](#features) section below.

## Purpose of the user guide
This user guide enables users to familiarise themselves with the commands of LingoGo! and use the application effectively.

## How to use the user guide
New users can refer to the [Quick Start](#quick-start) guide for a quick set-up tutorial. Experienced users can refer to the [Command Summary](#command-summary) for a quick overview of all the commands in LingoGo!. A detailed outline of the features can be found under [Features](#features). A [Table of Contents](#) with clickable links can be found above to help with navigating across the user guide quickly. A [Glossary](#glossary) is provided to help explain certain important terms used in this guide.

## Quick start

1. Ensure you have Java `11` or above installed in your Computer (you may download Java from [here](https://www.oracle.com/java/technologies/downloads/)).

1. Download the latest `lingogo.jar` from [here](https://github.com/AY2122S1-CS2103T-T11-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for LingoGO!.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the
   app contains some sample data.<br>
   ![Ui](images/Ui-explanation.png)

   * Menu
     * A clickable menu bar.
   * Command box
     * Type a command into the command box and press Enter to execute it.
     * Some example commands you can try (refer to the [Features](#features) section below for a full list of commands and their details):
       * `list` : Lists all flashcards.
       * `add l/Chinese e/Good Morning f/早安` : Adds a flashcard with the `Chinese` language, English phrase `Good Morning`, and corresponding foreign phrase `早安` to LingoGO!.
       * `delete 3` : Deletes the 3rd flashcard shown in the current displayed list.
       * `find Good Morning` : Finds flashcard(s) with the matching English phrase `Good Morning`.
       * `filter l/Chinese` : Shows only the flashcards with the `Chinese` language in the current displayed list.
   * Command result
     * Shows a message after you execute a command.
   * Displayed flashcard list
     * A list of your flashcards.

--------------------------------------------------------------------------------------------------------------------

## Commands

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by you.
  * e.g. a usage of `add l/LANGUAGE e/ENGLISH_PHRASE f/FOREIGN_PHRASE` could be `add l/Chinese e/Good Morning f/早安`.

* Items in square brackets are optional.
  * e.g. `edit INDEX [l/LANGUAGE] [e/ENGLISH_PHRASE] [f/FOREIGN_PHRASE]` can be used as `edit 1 l/Chinese e/Good Morning f/早安` or `edit 1 e/Good Morning`.

* Parameters can be in any order.
  * e.g. if the command specifies `add l/LANGUAGE e/ENGLISH_PHRASE f/FOREIGN_PHRASE`, `add f/FOREIGN_PHRASE l/LANGUAGE e/ENGLISH_PHRASE` is also acceptable.

* If a parameter is expected only once in the command, but you specified it multiple times, only the last occurrence of the parameter will be taken.
  * e.g. `edit 2 e/Hi e/Hello` is the same as `edit 2 e/Hello`.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit`, and `clear`) will be ignored.
  * e.g. `help 123` is the same as `help`.

</div>


### Viewing help : `help`

Shows a message explaining how to access the help page, as well as dropdowns with brief explanations
for each command.

![help message](images/helpMessage.png)

Format: `help`


### Adding a flashcard: `add`

Adds a flashcard to LingoGO!.

Format: `add l/LANGUAGE e/ENGLISH_PHRASE f/FOREIGN_PHRASE`

Examples:
* `add l/Chinese e/Good Morning f/早安`
* `add l/Chinese e/Hello f/你好`

### Listing all flashcards : `list`

Shows a list of all flashcards in LingoGO!.

Format: `list`

### Editing a flashcard : `edit`

Edits an existing flashcard in LingoGO!.

Format: `edit INDEX [l/LANGUAGE] [e/ENGLISH_PHRASE] [f/FOREIGN_PHRASE]`

* Edits the flashcard at the specified `INDEX`. The index refers to the index number shown in the displayed flashcard
  list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
* `edit 1 l/German` Edits the language of the 1st flashcard to be `German`.
* `edit 1 e/Good Morning` Edits the English phrase of the 1st flashcard to be `Good Morning`.
* `edit 2 f/Guten Morgen` Edits the foreign phrase of the 2nd flashcard to be `Guten Morgen`.
* `edit 2 l/German e/Good Morning f/Guten Morgen` Edits the language, English phrase, and foreign phrase of the 2nd flashcard to be `German`, `Good Morning`, and `Guten Morgen` respectively.

### Locating flashcards by keyword: `find`

Finds flashcards based on the keyword specified.

Format: `find [e/ENGLISH_KEYWORDS] [f/FOREIGN_KEYWORD]`

* The search is case-insensitive. e.g `HELLO` will match `Hello`
* The order of the keywords does not matter. e.g. `Good morning` will match `Morning good`
* Only full words will be matched e.g. `Hello` will not match `Helloooo` for English keywords
* Non-full words match will be accepted for foreign keywords e.g. `早` with match `早安`
* Flashcard(s) matching at least one keyword will be returned.
  e.g. `e/Good Hello` will return `e/Good Morning f/早安`, `e/Good Morning f/Guten Morgen` and `e/Hello f/你好`
* At least one of the optional fields must be provided.

Examples:
* `find e/Good` returns `e/Good Morning f/早安` and `e/Good Morning f/Guten Morgen`
* `find e/Good Hello` returns `e/Good Morning f/早安`, `e/Good Morning f/Guten Morgen` and `e/Hello f/你好`
* `find f/早` return `e/Good Morning f/早安` and `e/Morning f/早晨`
* `find f/安 好` return `e/Good Morning f/早安`, `e/Good Afternoon f/午安`, `e/Good Night f/晚安` and `e/Hello f/你好`
* `find e/Hello f/早` return `e/Hello f/你好`, `e/Good Morning f/早安` and `e/Morning f/早晨`<br>

### Filtering flashcards by condition(s): `filter`

Filters flashcards based on the specified condition(s).

Format: `filter [l/LANGUAGE] [i/INDEX_LIST]`

* `LANGUAGE` is not case-sensitive (e.g. "Chinese" matches "CHINESE").
* `INDEX_LIST` is a list of space separated indices, that refer to the indices shown in the current displayed list.
*  The indices **must be positive integers** 1, 2, 3, …

Examples:
* `filter l/Chinese` returns all flashcards with the `Chinese` language like `e/Good Morning f/早安`.
* `filter l/German` returns all flashcards with the `German` language like `e/Good Morning f/Guten Morgen`.
* `filter i/1 2 3` returns flashcards in the current displayed list indexed at 1, 2 and 3.
* `filter i/1 3 6 l/Tamil` returns all flashcards in the current displayed list indexed at 1, 3 and 6 with the `Tamil`
  language.


### Deleting a flashcard : `delete`

Deletes the specified flashcard from LingoGO!.

Format: `delete INDEX`

* Deletes the flashcard at the specified `INDEX`.
* The index refers to the index number shown in the current displayed list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `delete 2` deletes the 2nd flashcard in LingoGO!.
* `find Hello` followed by `delete 1` deletes the 1st flashcard in the results of the `find` command.

### Clearing all entries : `clear`

Clears all flashcards from LingoGO!.

Format: `clear`

### Importing cards : `import`

Imports cards to LingoGO! using a CSV file.

Format: `import CSV_FILE_PATH`

* The file path can be absolute or relative to the location of the LingoGO! file.

<div markdown="block" class="alert alert-info">

**:information_source: Notes about `CSV_FILE_PATH`:**<br>

* Users with no knowledge about file paths can just place the CSV file in the `data` folder, and type the file name as the `CSV_FILE_PATH`.

</div>

* The CSV file must have 2 columns. The first column is for the English phrase, and the second column is for the foreign phrase.
  ![sample CSV file](images/SampleCSVFile.png)

Examples:
* `import ./dictionary.csv` will load all cards stored in the dictionary.csv to LingoGO!.

### Exporting cards : `export`

Exports cards from LingoGO! to a CSV file.

Format: `export FILE_NAME`

* Provides a file name with .csv extension in which the flash cards will be stored and exported.
* The exported file will be added to the `data` folder in a CSV format.
* The CSV file will have 2 columns. The first column is for the English phrase, and the second column is for the foreign phrase.

Examples:
* `export myCards.csv` will save all cards in LingoGO! to a CSV file named `myCards.csv`.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Flipping a flashcard : `flip`

Toggles the flashcard to either show or hide its English phrase.

Format: `flip INDEX`

* Toggles the flashcard at the specified `INDEX`.
* The index refers to the index number shown in the current displayed list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `flip 3` followed by `flip 3` shows and then hides the English phrase for the 3rd flashcard in the current displayed list.

### Testing with a flashcard : `test`

Checks whether the English phrase of a flashcard matches a given phrase.

Format: `test INDEX e/ENGLISH_PHRASE`

* Checks the English phrase of the flashcard at the specified `INDEX` against the given `ENGLISH_PHRASE`.
* The app will then show user the correct English phrase and tell the user whether he got it right.
* The index refers to the index number shown in the current displayed list.
* The index **must be a positive integer** 1, 2, 3, …
* Testing is only allowed for flashcards that are flipped down (i.e. the English phrase is hidden).
* `ENGLISH_PHRASE` is not case-sensitive (e.g. "HeLLo" matches "hello").

Examples:
* `test 4 e/hello` checks the 4th card on display to see if `hello` matches the English phrase of the flashcard.

### Saving the data

LingoGO! data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

LingoGO! data are saved as a JSON file `[JAR file location]/data/lingogo.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, LingoGO! will discard all data and start with an empty data file at the next run.
</div>


--------------------------------------------------------------------------------------------------------------------

## Glossary

### CSV
A CSV file, short for comma-separated values, is a special text file that uses commas for formatting.

### File paths
File paths specify the location of a file on the computer. They tell the computer how to find a specific file. An absolute file path tells the computer how to find the file from the root folder. A relative file path tells the computer how to find a file from the current folder that it is in. For more on file paths, you may want to visit [here](https://docs.oracle.com/javase/tutorial/essential/io/path.html).


## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous LingoGO! home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format | Example
--------|------------------
**Add** | `add l/LANGUAGE e/ENGLISH_PHRASE f/FOREIGN_PHRASE` | `add l/Chinese e/Good Morning f/早安`
**Clear** | `clear` | `clear`
**Delete** | `delete INDEX` | `delete 3`
**Edit** | `edit INDEX [l/LANGUAGE] [e/ENGLISH_PHRASE] [f/FOREIGN_PHRASE]` | `edit 2 f/Guten Morgen`
**Filter** | `filter [l/LANGUAGE] [i/INDEX_LIST]` | `filter l/Chinese i/1 2 3`
**Find** | `find [e/ENGLISH_KEYWORDS] [f/FOREIGN_KEYWORDS]` | `find e/Hello f/早`
**List** | `list` | `list`
**Import** | `import CSV_FILE_PATH` | `import ./dictionary.csv`
**Export** | `export FILE_NAME` | `export myCards.csv`
**Help** | `help` | `help`
**Flip** | `flip INDEX` | `flip 2`
**Test** | `test INDEX e/ENGLISH_PHRASE` | `test 17 e/hello`
**Exit** | `exit` | `exit`

