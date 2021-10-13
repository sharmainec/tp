package lingogo.model.flashcard;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.util.CollectionUtil.requireAllNonNull;
import static lingogo.logic.LogicManager.FILE_OPS_ERROR_MESSAGE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.flashcard.exceptions.DuplicateFlashcardException;
import lingogo.model.flashcard.exceptions.FlashcardNotFoundException;

/**
 * A list of flashcards that enforces uniqueness between its elements and does not allow nulls.
 * A flashcard is considered unique by comparing using {@code Flashcard#isSameFlashcard(Flashcard)}.
 * As such, adding and updating of flashcards uses Flashcard#isSameFlashcard(Flashcard) for equality
 * so as to ensure that the flashcard being added or updated is unique in terms of identity in the
 * UniqueFlashcardList. However, the removal of a flashcard uses Flashcard#equals(Object) so as to
 * ensure that the flashcard with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Flashcard#isSameFlashcard(Flashcard)
 */
public class UniqueFlashcardList implements Iterable<Flashcard> {
    private final ObservableList<Flashcard> internalList = FXCollections.observableArrayList();
    private final ObservableList<Flashcard> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent flashcard as the given argument.
     */
    public boolean contains(Flashcard toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFlashcard);
    }

    /**
     * Adds a flashcard to the list.
     * The flashcard must not already exist in the list.
     */
    public void add(Flashcard toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFlashcardException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the flashcard {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the list.
     * The flashcard identity of {@code editedFlashcard} must not be the same as another existing flashcard in the list.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FlashcardNotFoundException();
        }

        if (!target.isSameFlashcard(editedFlashcard) && contains(editedFlashcard)) {
            throw new DuplicateFlashcardException();
        }

        internalList.set(index, editedFlashcard);
    }

    /**
     * Removes the equivalent flashcard from the list.
     * The flashcard must exist in the list.
     */
    public void remove(Flashcard toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FlashcardNotFoundException();
        }
    }

    public void setFlashcards(UniqueFlashcardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code flashcards}.
     * {@code flashcards} must not contain duplicate flashcards.
     */
    public void setFlashcards(List<Flashcard> flashcards) {
        requireAllNonNull(flashcards);
        if (!flashcardsAreUnique(flashcards)) {
            throw new DuplicateFlashcardException();
        }

        internalList.setAll(flashcards);
    }

    /**
     * Exports the contents of internalList to a CSV file named {@code fileName}.
     * {@code fileName} must be a valid file name with .csv extension.
     */
    public void downloadFlashcards(String fileName) throws CommandException {
        String filePath = "./data/" + fileName;
        try {
            new File(filePath).createNewFile();
            FileWriter fw = new FileWriter(filePath, false);
            fw.append("Foreign,English\n");
            fw.flush();
            for (Flashcard card : internalList) {
                String line = card.toCsvString();
                fw.append(line);
                fw.flush();
            }
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    /**
     * Imports the contents of the CSV file in {@code filePath} to LingoGO!.
     * {@code filePath} must be a valid file path with .csv extension.
     */
    public void uploadFlashcards(String filePath) throws CommandException {
        try {
            File f = new File(filePath);
            Scanner sc = new Scanner(f);
            sc.nextLine(); // skip the title row
            while (sc.hasNextLine()) { // works as long as you don't allow "\n" in phrases
                String line = sc.nextLine();
                Flashcard card = new Flashcard(line);
                if (!internalList.contains(card)) {
                    internalList.add(card);
                }
            }
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Flashcard> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Flashcard> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueFlashcardList
                        && internalList.equals(((UniqueFlashcardList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code flashcards} contains only unique flashcards.
     */
    private boolean flashcardsAreUnique(List<Flashcard> flashcards) {
        for (int i = 0; i < flashcards.size() - 1; i++) {
            for (int j = i + 1; j < flashcards.size(); j++) {
                if (flashcards.get(i).isSameFlashcard(flashcards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
