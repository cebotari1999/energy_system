package producers;

import entities.EnergyType;
import java.util.List;

/**
 * Aceasta clasa este folosita pentru a salva informatiile despre producatori
 * ce urmeaza sa fie afisate in fisierele de output.
 */
public class ProducersOutput {
    private final Long id;
    private final Long maxDistributors;
    private final Double priceKW;
    private final EnergyType energyType;
    private final Long energyPerDistributor;
    private final List<MonthlyStats> monthlyStats;


    /**
     *
     * @param id-ul producatorului
     * @param energyType - tipul energiei pe care o ofera
     * @param maxDistributors - numarul maxim de distribuitori
     * @param priceKW - pretul unui KW
     * @param energyPerDistributor - energia oferita per distribuitor
     * @param monthlyStats - istoricul lunar al contractelor
     */
    public ProducersOutput(final Long id, final EnergyType energyType,
                           final Long maxDistributors, final Double priceKW,
                           final Long energyPerDistributor, final List<MonthlyStats> monthlyStats) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = monthlyStats;
    }

    /**
     *
     * @return id-ul producatorului
     */
    public final Long getId() {
        return id;
    }

    /**
     *
     * @return - tipul energiei
     */
    public final EnergyType getEnergyType() {
        return energyType;
    }

    /**
     *
     * @return - cantitatea oferita pentru un distribuitor
     */
    public final Long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    /**
     *
     * @return - numarul maxim de contracte pe care le poate semna cu
     * distribuitorii
     */
    public final Long getMaxDistributors() {
        return maxDistributors;
    }

    /**
     *
     * @return - pretul unui KW
     */
    public final Double getPriceKW() {
        return priceKW;
    }

    /**
     *
     * @return - istoricul lunar
     */
    public final List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }
}
