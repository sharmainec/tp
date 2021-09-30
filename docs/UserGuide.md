---
layout: page
title: User Guide
---

LingoGO! is a **desktop app for university students who use English as their first language and are trying to learn a new language, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). With digital flashcards, LingoGO! can make learning faster and more convenient compared to using than traditional flashcards.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `lingogo.jar` from [here](https://github.com/AY2122S1-CS2103T-T11-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for LingoGO!.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the 
   app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will 
   open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all flashcards.

   * **`add`**`e/Good Morning f/早安` : Adds a flashcard with English phrase `Good Morning` and corresponding foreign phrase `早安` to LingoGO!.

   * **`delete`**`3` : Deletes the 3rd flashcard shown in the current list.

   * **`clear`** : Deletes all flashcards.
   
   * **`edit`**`3`**`f/Guten Morgen`** : Edits the foreign phrase of the 3rd flashcard shown in the current list to `Guten Morgen`.
   
   * **`upload`**`./dictionary.csv` : Imports cards from a CSV file to LingoGO!.
   
   * **`download`** : Exports cards from LingoGO! to a CSV file.

   * **`exit`** : Exits the app.

   * **`flip`**`2`: Toggles the 2nd flashcard to hide or show the correct translation.

   * **`test`**`17`**`hello`**: Checks flashcard 17's translation against the word **`hello`** and then shows 
     whether it is correct.
   

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add e/ENGLISH_PHRASE f/FOREIGN_PHRASE`, `ENGLISH_PHRASE` and `FOREIGN_PHRASE` are parameters which can be used as `add e/Good Morning f/早安`.

* Items in square brackets are optional.<br>
  e.g. `edit INDEX [e/ENGLISH_PHRASE] [f/FOREIGN_PHRASE]` can be used as `edit 1 e/Good Morning f/早安` or as `edit 1 f/早安` or as `edit 1 e/Good Morning`

* Parameters can be in any order.<br>
  e.g. if the command specifies `add e/ENGLISH_PHRASE f/FOREIGN_PHRASE`, `f/FOREIGN_PHRASE e/ENGLISH_PHRASE` is also acceptable.

* If a parameter is expected only once in the command, but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `edit 2 e/Hi e/Hello`, only `e/Hello` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help` 

Shows a message explaining how to access the help page.

![help message](images/helpMessage.JPG)

Format: `help [COMMAND]`

Examples:
* `help`
* `help add`

### Adding a flashcard: `add`

Adds a flashcard to LingoGO!.

Format: `add e/ENGLISH_PHRASE f/FOREIGN_PHRASE`

Examples:
* `add e/Good Morning f/早安`
* `add e/Hello f/你好`

### Listing all flashcards : `list`

Shows a list of all flashcards in LingoGO!.

Format: `list`

### Editing a flashcard : `edit`

Format: `edit INDEX [e/ENGLISH_PHRASE] [f/FOREIGN_PHRASE]`

* Edits the flashcard at the specified `INDEX`. The index refers to the index number shown in the displayed flashcard
  list. The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
* `edit 1 e/Good Morning` Edits the English phrase of the 1st flashcard to be `Good Morning`.
* `edit 2 f/Guten Morgen` Edits the foreign phrase of the 2nd flashcard to be `Guten Morgen`.
* `edit 2 e/Good Morning f/Guten Morgen` Edits the English phrase and foreign phrase of the 2nd flashcard to be `Good Morning` and `Guten Morgen` respectively.

### Locating flashcards by keyword: `find`

Finds flashcards based on the keyword specified.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `HELLO` will match `Hello`
* The order of the keywords does not matter. e.g. `Good morning` will match `Morning good`
* Only full words will be matched e.g. `Hello` will not match `Helloooo`
* Flashcard matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Good Hello` will return `e/Good Morning f/早安`, `e/Good Morning f/Guten Morgen` and `e/Hello f/你好`

Examples:
* `find Good` returns `e/Good Morning f/早安` and `e/Good Morning f/Guten Morgen`
* `find Good Hello` returns `e/Good Morning f/早安`, `e/Good Morning f/Guten Morgen` and `e/Hello f/你好`<br>

### Deleting a flashcard : `delete`

Deletes the specified flashcard from LingoGO!.

Format: `delete INDEX`

* Deletes the flashcard at the specified `INDEX`.
* The index refers to the index number shown in the displayed flashcard list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `delete 2` deletes the 2nd flashcard in LingoGO!.
* `find Hello` followed by `delete 1` deletes the 1st flashcard in the results of the `find` command.

### Clearing all entries : `clear`

Clears all flashcards from LingoGO!.

Format: `clear`

### Uploading cards : `upload`

Imports cards to LingoGO! using a CSV file.

Format: `upload CSV_FILE_PATH`

* The file path can be absolute or relative to the location of the LingoGO! file.
* The CSV file must have 2 columns. The first column is for English and the second column is for Foreign language.
  ![sample CSV file](images/SampleCSVFile.png)

Examples:
* `upload ./dictionary.csv` will load all cards stored in the dictionary.csv to LingoGO!

### Downloading cards : `download`

Exports cards from LingoGO! to a CSV file.

Format: `download`

* The exported file will be added to `data` folder in a CSV format.
* The CSV file will have 2 columns. The first column is for the English phrase, and the second column is for foreign phrase.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Flipping a flashcard : `flip`

Toggles the flashcard to either show or hide its English translation.

Format: `flip INDEX`

* Toggles the flashcard at the specified `INDEX`.
* Similar to delete, the index refers to the index number shown in the displayed flashcard list.
* Similar to delete, the index **must be a positive integer** 1, 2, 3, …

Examples:
* `flip 3` followed by `flip 3` shows and then hides the English translation for the 3rd flashcard on display.

### Testing with a flashcard : `test`

Checks whether the English translation of a flashcard matches a given phrase.

Format: `test INDEX ENGLISH_PHRASE`

* Checks the translation of the flashcard at the specified `INDEX` with the given `ENGLISH_PHRASE`.
* The app will then show user the correct English translation and tell the user whether he got it right.
* We can only against with a flashcard that is flipped down (i.e. the translation is hidden).
* The `ENGLISH_PHRASE` is not case-sensitive (e.g. "HeLLo" matches "hello"). 

Examples:
* `test 4 hello` checks the 4th card on display to see if `hello` matches the English translation of the flashcard.

### Saving the data

LingoGO! data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

LingoGO! data are saved as a JSON file `[JAR file location]/data/lingogo.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, LingoGO! will discard all data and start with an empty data file at the next run.
</div>

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous LingoGO! home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add e/ENGLISH_PHRASE f/FOREIGN_PHRASE` <br> e.g., `add e/Good Morning f/早安`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [e/ENGLISH_PHRASE] [f/FOREIGN_PHRASE]`<br> e.g.,`edit 2 f/Guten Morgen`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Upload** | `upload CSV_FILE_PATH`<br> e.g., `upload ./dictionary.csv`
**Download** | `download`<br> e.g., `download`
**Help** | `help`
**Flip** | `flip INDEX` <br> e.g.,  `flip 2`
**Test** | `test INDEX ENGLISH_PHRASE` <br> e.g.,  `test 17 hello`
