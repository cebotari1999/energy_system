package producers;

import entities.EnergyType;
import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa este folosita pentru a stoca date despre producatori si a
 * actualiza contractele acestora cu distribuitorii.
 */
public class Producers {
    private final Long id;
    private final EnergyType energyType;
    private final Long maxDistributors;
    private final Double priceKW;
    private Long energyPerDistributor;
    private final List<MonthlyStats> monthlyStats;
    private Integer numberOfDistributor;


    public Producers(final Long id, final EnergyType energyType,
                     final Long maxDistributors, final Double priceKW,
                     final Long energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = new ArrayList<>();
        this.numberOfDistributor = 0;
    }

    /**
     *
     * @return - id-ul producatorului
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
     * @param energyPerDistributor - cantitatea de energie pe care o poate
     *                             oferi per distribuitor
     */
    public final void setEnergyPerDistributor(final Long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    /**
     *
     * @return - cantitatea pe care o ofera producatorul unui distribuitor
     */
    public final Long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    /**
     *
     * @return numarul maxim de distribuitori cu care producatorul
     * poate semna contracte
     */
    public final Long getMaxDistributors() {
        return maxDistributors;
    }

    /**
     *
     * @return - pretul pentru un KW
     */
    public final Double getPriceKW() {
        return priceKW;
    }

    /**
     *
     * @param month - se salveaza luna pentru care urmeaza sa fie
     *              semnate contracte
     */
    public final void setMonth(final Long month) {
        this.monthlyStats.add(new MonthlyStats(month + 1));
    }

    /**
     *
     * @param month - se salveaza luna
     * @param distributorId - si id-ul distribuitorului cu care sa semnat contractul
     */
    public final void setDistributorId(final Long month, final Long distributorId) {
        this.monthlyStats.get(Math.toIntExact(month)).setDistributorIds(distributorId);
    }

    /**
     *
     * @return - istoricul lunar al contractelor semnate
     */
    public final List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    /**
     * Semneaza un contract cu un distribuitor.
     */
    public final void setContract() {
        this.numberOfDistributor++;
    }

    /**
     * Incheie un contract.
     */
    public final void removeContract() {
        this.numberOfDistributor = this.numberOfDistributor - 1;
    }

    /**
     *
     * @return numarul total de contracte pe care le-a semnat producatorul
     */
    public final Integer getNumberOfDistributor() {
        return numberOfDistributor;
    }
}
