

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This is a chained comparator that is used to sort a list by multiple
 * attributes by chaining a sequence of comparators of individual fields
 * together.
 */
public class PostCodeChainedComparator implements Comparator<WeatherData> {

    private List<Comparator<WeatherData>> listComparators;

    @SafeVarargs
    public PostCodeChainedComparator(Comparator<WeatherData>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }

    @Override
    public int compare(WeatherData wd1, WeatherData wd2) {
        for (Comparator<WeatherData> comparator : listComparators) {
            int result = comparator.compare(wd1, wd2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}