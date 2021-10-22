package lingogo.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lingogo.commons.core.index.Index;
import lingogo.logic.commands.FilterCommand.FilterBuilder;
import lingogo.model.flashcard.Phrase;

/**
 * A utility class to help with building FilterBuilder objects.
 */
public class FilterBuilderBuilder {

    private FilterBuilder filterBuilder;

    public FilterBuilderBuilder() {
        filterBuilder = new FilterBuilder();
    }

    /**
     * Sets the {@code languageType} of the {@code FilterBuilder} that we are building.
     */
    public FilterBuilderBuilder withLanguagePhrase(Phrase languagePhrase) {
        filterBuilder.setLanguageType(languagePhrase);
        return this;
    }


    /**
     * Sets the {@code languageType} of the {@code FilterBuilder} that we are building.
     */
    public FilterBuilderBuilder withLanguagePhrase(String languagePhrase) {
        filterBuilder.setLanguageType(new Phrase(languagePhrase));
        return this;
    }


    /**
     * Sets the {@code IndexList} of the {@code FilterBuilder} that we are building.
     */
    public FilterBuilderBuilder withIndexList(List<Index> indexList) {
        filterBuilder.setIndexList(indexList);
        return this;
    }

    /**
     * Sets the {@code IndexList} of the {@code FilterBuilder} that we are building.
     */
    public FilterBuilderBuilder withIndexList(Integer... indices) {

        List<Integer> listOfIndexIntegers = Arrays.asList(indices);
        filterBuilder.setIndexList(
            listOfIndexIntegers.stream().map(indexInteger -> Index.fromOneBased(indexInteger)).collect(
                Collectors.toList()));
        return this;
    }

    public FilterBuilder build() {
        return filterBuilder;
    }

}
