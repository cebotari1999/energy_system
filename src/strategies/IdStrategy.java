package strategies;

import java.util.Comparator;
import producers.Producers;

/**
 * Producatorii se clasifica dupa ID-uri.
 */
public class IdStrategy implements Comparator<Producers> {
    @Override
    public final int compare(final Producers producer1, final Producers producer2) {
        if (producer1.getId() < producer2.getId()) {
            return -1;
        }

        return 1;
    }
}
