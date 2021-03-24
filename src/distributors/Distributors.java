package distributors;
import strategies.EnergyChoiceStrategyType;

import java.util.ArrayList;

/**
 * Aceasta clasa este folosita pentru a stoca si actualiza datele distribuitorilor,
 * pentru a stoca si actualiza contractele acestora cu consumatorii si
 * producatorii.
 */

public class Distributors {
    private final Long id;
    private final Long contractLength;
    private Long budget;
    private Long infrastructureCost;
    private Long energyNeededKW;
    private EnergyChoiceStrategyType producerStrategy;
    private Boolean isBankrupt;
    private Long contractCost;
    private Long productionCost;
    private final ArrayList<DistributorContracts> contracts;
    private ArrayList<Long> producersId;


    /**
     *
     * @param id-ul distribuitorului
     * @param contractLength - durata contractului
     * @param budget - bugetul
     * @param infrastructureCost - costul infrastructurii
     * @param energyNeededKW - cantitatea de energie necesara lunar
     * @param producerStrategy - strategia pe care o aplica la cautarea
     *                         producatorilor
     */
    public Distributors(final Long id, final Long contractLength, final Long budget,
                        final Long infrastructureCost, final Long energyNeededKW,
                        final EnergyChoiceStrategyType producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = budget;
        this.infrastructureCost = infrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = producerStrategy;
        this.isBankrupt = false;
        this.contracts = new ArrayList<>();
        this.producersId = new ArrayList<>();
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
     * @return durata contractului
     */
    public final Long getContractLength() {
        return contractLength;
    }

    /**
     *
     * @return costul infrastructurii
     */
    public final Long getInfrastructureCost() {
        return infrastructureCost;
    }

    /**
     *
     * @param infrastructureCost - se modifica costul infrastructurii
     */
    public final void setInfrastructureCost(final Long infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     *
     * @return cantitatea de energie lunara necesara
     */
    public final Long getEnergyNeededKW() {
        return energyNeededKW;
    }

    /**
     *
     * @param energyNeededKW - se modifica cantitatea de energie lunara necesara
     */
    public final void setEnergyNeededKW(final Long energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }


    /**
     *
     * @param producerStrategy - se seteaza strategia
     */
    public void setProducerStrategy(final EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    /**
     *
     * @return tipul strategiei
     */
    public final EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    /**
     *
     * @return bugetul distribuitorului
     */
    public final Long getBudget() {
        return budget;
    }

    /**
     *
     * @param rate - se aduna la buget ratele pe care
     *             le primeste distributorul lunar de la consumatori
     */
    public final void setBudget(final Long rate) {
        this.budget = this.budget + rate;
    }

    /**
     *
     * @param cost - se scad costurile pe care le achtia distribuitorul lunar
     */
    public final void pay(final Long cost) {
        this.budget = this.budget - cost;
    }

    /**
     *
     * @return situatia financiara
     */
    public final Boolean getBankrupt() {
        return isBankrupt;
    }

    /**
     * Se apeleaza atunci cand un distribuitor da faliment.
     */
    public final void setBankrupt() {
        isBankrupt = true;
    }

    /**
     *
     * @param contractCost - rata lunara pentru un contract
     */
    public final void setContractCost(final Long contractCost) {
        this.contractCost = contractCost;
    }

    /**
     *
     * @return - rata lunara
     */
    public final Long getContractCost() {
        return contractCost;
    }

    /**
     *
     * @param productionCost - se seteaza costul productiei
     */
    public final void setProductionCost(Long productionCost) {
        this.productionCost = productionCost;
    }

    /**
     *
     * @return - costul productiei
     */
    public final Long getProductionCost() {
        return productionCost;
    }

    /**
     *
     * @return contractele semnate cu acest distribuitor
     */
    public final ArrayList<DistributorContracts> getContracts() {
        return contracts;
    }

    /**
     * Se adauga un contract nou in vectorul de obiecte de tip
     * DistributorContract.
     */
    public final void setContracts(final Long consumerId, final Long price) {
        DistributorContracts newContract;
        newContract = new DistributorContracts(consumerId, price, contractLength);
        contracts.add(newContract);
    }

    /**
     *
     * @param consumerId - se sterge contractul consumatorului respectiv
     */
    public final void removeContract(final Long consumerId) {
        for (int i = contracts.size() - 1; i >= 0; i--) {
            if (contracts.get(i).getConsumerId().equals(consumerId)) {
                contracts.remove(i);
            }
        }
    }

    /**
     * Se anuleaza contractele cu toti producatorii.
     */
    public final void resetProducers() {
        producersId = new ArrayList<>();
    }

    /**
     *
     * @param producerId - se semneaza contractul cu un producator nou
     */
    public final void setNewProducers(final Long producerId) {
        this.producersId.add(producerId);
    }

    /**
     * Returneaza lista cu id-urile tuturor producatorilor cu care distribuitorul
     * a semnat contracte.
     */
    public final ArrayList<Long> getProducersId() {
        return producersId;
    }

}
