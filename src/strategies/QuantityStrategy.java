package strategies;

import java.util.Comparator;
import producers.Producers;

/**
 * Producatorii se clasifica dupa cantitatea de energie pe care o ofera fiecarui
 * distribuitor, daca ofere aceiasi cantitate, se vor clasifica dupa ID-uri,
 * folosind IdStrategy.
 */
public class QuantityStrategy implements Comparator<Producers> {
    @Override
    public final int compare(final Producers producer1, final Producers producer2) {
        if (producer1.getEnergyPerDistributor() < producer2.getEnergyPerDistributor()) {
            return 1;
        }

        if (producer1.getEnergyPerDistributor() > producer2.getEnergyPerDistributor()) {
            return -1;
        }

        return new IdStrategy().compare(producer1, producer2);
    }
}
