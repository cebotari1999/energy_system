package strategies;

import java.util.Comparator;
import producers.Producers;

/**
 * Producatori se compara dupa pretul energiei, daca ambii au stablit acelasi
 * pret, se aplica stategia Quantity.
 */
public class PriceStrategy implements Comparator<Producers> {
    @Override
    public final int compare(final Producers producer1, final Producers producer2) {
        if (producer1.getPriceKW() > producer2.getPriceKW()) {
            return 1;
        }

        if (producer1.getPriceKW() < producer2.getPriceKW()) {
            return -1;
        }

        return new QuantityStrategy().compare(producer1, producer2);
    }
}
