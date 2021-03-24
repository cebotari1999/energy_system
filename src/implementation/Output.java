package implementation;
import consumers.ConsumersOutput;
import distributors.DistributorsOutput;
import producers.ProducersOutput;

import java.util.ArrayList;

/**
 * Aceasta clasa este folosita pentru a stoca si scrie in fisiere de tip JSON
 * starea finala a simularii.
 */

public class Output {
    private final ArrayList<ConsumersOutput> consumers = new ArrayList<>();
    private final ArrayList<DistributorsOutput> distributors = new ArrayList<>();
    private final ArrayList<ProducersOutput> energyProducers = new ArrayList<>();

    /**
     *
     * @return vectorul de instante ale clasei ConsumersOutput
     */
    public final ArrayList<ConsumersOutput> getConsumers() {
        return consumers;
    }

    /**
     *
     * @param consumer - contine datele necesare ale unui consumator
     */
    public final void setConsumers(final ConsumersOutput consumer) {
        consumers.add(consumer);
    }

    /**
     *
     * @return vectorul de instante ale clasei DistributorsOutput
     */
    public final ArrayList<DistributorsOutput> getDistributors() {
        return distributors;
    }

    /**
     *
     * @param distributor - contine informatiile necesare ale unui distribuitor
     */
    public final void setDistributors(final DistributorsOutput distributor) {
        distributors.add(distributor);
    }

    /**
     *
     * @return lista producatoriilor
     */
    public final ArrayList<ProducersOutput> getEnergyProducers() {
        return energyProducers;
    }

    /**
     *
     * @param producer - datele necesare despre un producator
     */
    public final void setEnergyProducers(final ProducersOutput producer) {
        energyProducers.add(producer);
    }
}
