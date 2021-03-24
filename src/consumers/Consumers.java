package consumers;

import distributors.Distributors;

public class Consumers {
    private final Long id;
    private Long budget;
    private final Long monthlyIncome;
    private Boolean isBankrupt;
    private ConsumerContract contract;

    /**
     * @param id-ul unui utilizator
     * @param budget-ul initial al unui utilizator
     * @param monthlyIncome - salariul lunar
     */
    public Consumers(final Long id, final Long budget, final Long monthlyIncome) {
        this.id = id;
        this.budget = budget;
        this.monthlyIncome = monthlyIncome;
        this.isBankrupt = false;
        this.contract = new ConsumerContract();
    }

    /**
     * @return id
     */
    public final Long getId() {
        return id;
    }

    /**
     * @return salariul lunar
     */
    public final Long getMonthlyIncome() {
        return monthlyIncome;
    }

    /**
     * Consumatorul primeste salariul.
     */
    public final void setBudgetMonthly() {
        budget += monthlyIncome;
    }

    /**
     * @return Situatia financiara.
     */
    public final Boolean getBankrupt() {
        return isBankrupt;
    }

    /**
     *
     * @param bankrupt - true daca utilizatorul a dat faliment
     */
    public final void setBankrupt(final Boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    /**
     *
     * @return bugetul
     */
    public final Long getBudget() {
        return budget;
    }


    /**
     * Consumatorul achita rata lunara.
     */
    public final void payRate() {
        budget -= contract.getRate();
    }

    /**
     * Consumatorul achita 2 facturi, curenta si pentru luna
     * trecuta.
     */
    public final void payRateAndDelay() {
        budget -= Math.round(Math.floor(1.2 * contract.getDelayCost())
                + contract.getRate());
    }

    /**
     *
     * @return contractul pe care il are un consumator.
     */
    public final ConsumerContract getContract() {
        return contract;
    }

    /**
     * Se reseteaza aprametrii contractului vechi.
     */
    public final void setNewContract() {
        this.contract = new ConsumerContract();
    }

    /**
     * Se seteaza parametrii contractului nou.
     */
    public final void setContract(final Distributors distributors,
                                  final Long monthPrice) {
        contract.setContractLength(distributors.getContractLength());
        contract.setRate(monthPrice);
        contract.setIdDistributors(Math.toIntExact(distributors.getId()));
    }
}
