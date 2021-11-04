package lingogo.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Pair;
import lingogo.commons.core.index.Index;
import lingogo.logic.commands.FilterCommand.FilterBuilder;
import lingogo.model.flashcard.LanguageType;

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
     * @param languageType
     */
    public FilterBuilderBuilder withLanguageType(LanguageType languageType) {
        filterBuilder.setLanguageType(languageType);
        return this;
    }


    /**
     * Sets the {@code languageType} of the {@code FilterBuilder} that we are building.
     * @param languageType
     */
    public FilterBuilderBuilder withLanguageType(String languageType) {
        filterBuilder.setLanguageType(new LanguageType(languageType));
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

    /**
     * Sets the {@code indexRangePair} of the {@code FilterBuilder} that we are building.
     */
    public FilterBuilderBuilder withRange(Integer... pair) {
        Pair<Index, Index> temp = new Pair<>(Index.fromOneBased(pair[0]), Index.fromOneBased(pair[1]));
        return withRange(temp);
    }

    /**
     * Sets the {@code indexRangePair} of the {@code FilterBuilder} that we are building.
     */
    public FilterBuilderBuilder withRange(Pair<Index, Index> pair) {
        filterBuilder.setIndexRangePair(pair);
        return this;
    }


    public FilterBuilder build() {
        return filterBuilder;
    }

}
