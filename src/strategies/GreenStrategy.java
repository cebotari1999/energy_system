package strategies;

import java.util.Comparator;
import producers.Producers;

/**
 * Acest query este folosit pentru sortarea producatorilor pentru care
 * se aplica strategia GREEN.
 * Clasificarea se face dupa tipul de energie. Daca doi producatori produc
 * energie de acelasi tip, atunci acestia se sorteaza aplicand strategia
 * PRICE.
 */
public class GreenStrategy implements Comparator<Producers> {
    @Override
    public final int compare(final Producers producer1, final Producers producer2) {
        if (producer1.getEnergyType().isRenewable() && !producer2.getEnergyType().isRenewable()) {
            return -1;
        }

        if (!producer1.getEnergyType().isRenewable() && producer2.getEnergyType().isRenewable()) {
            return 1;
        }

        return new PriceStrategy().compare(producer1, producer2);
    }
}
