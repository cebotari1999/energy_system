package distributors;

import strategies.EnergyChoiceStrategyType;
import java.util.ArrayList;

/**
 * Aceasta clasa este folosita pentru a stoca informatile despre distribuitori,
 * ce urmeaza sa fie afisate in fisierul de output.
 */
public class DistributorsOutput {
    private final Long id;
    private final Long energyNeededKW;
    private final Long contractCost;
    private final Long budget;
    private final EnergyChoiceStrategyType producerStrategy;
    private final Boolean isBankrupt;
    private final ArrayList<DistributorContracts> contracts;

    /**
     * @param id-ul distribuitorului
     * @param energyNeededKW - energia necesara lunar
     * @param contractCost - rata lunara pentru un contract
     * @param budget-ul acestuia
     * @param isBankrupt - situatia financiara
     * @param producerStrategy - strategia dupa care isi cauta producatorii
     * @param contracts - contractele semnate cu consumatorii
     */
    public DistributorsOutput(final Long id, final Long energyNeededKW,
                              final Long contractCost,
                              final Long budget, final Boolean isBankrupt,
                              final EnergyChoiceStrategyType producerStrategy,
                              final ArrayList<DistributorContracts> contracts) {
        this.id = id;
        this.energyNeededKW = energyNeededKW;
        this.contractCost = contractCost;
        this.budget = budget;
        this.isBankrupt = isBankrupt;
        this.producerStrategy = producerStrategy;
        this.contracts = contracts;
    }

    /**
     *
     * @return id-ul distribuitorului
     */
    public final Long getId() {
        return id;
    }

    /**
     *
     * @return - cantitatea de energie necesara
     */
    public final Long getEnergyNeededKW() {
        return energyNeededKW;
    }

    /**
     *
     * @return - pretul unui contract
     */
    public final Long getContractCost() {
        return contractCost;
    }

    /**
     *
     * @return - bugetul curent al distribuitorului
     */
    public final Long getBudget() {
        return budget;
    }

    /**
     *
     * @return - situatia financiara
     */
    public final Boolean getIsBankrupt() {
        return isBankrupt;
    }

    /**
     *
     * @return - tipul strategiei
     */
    public final EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    /**
     * @return - lista de contracte curente
     */
    public final ArrayList<DistributorContracts> getContracts() {
        return contracts;
    }

}
