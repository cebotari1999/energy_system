package distributors;

/**
 * Aceasta clasa este folosita pentru a stoca contractele curente a unui distribuitor,
 * cu consumatorii sai.
 */
public class DistributorContracts {
    private final Long consumerId;
    private final Long price;
    private Long remainedContractMonths;

    /**
     *
     * @param consumerId - id-ul consumatorului
     * @param price - rata lunara
     * @param remainedContractMonths - durata contractului
     */
    public DistributorContracts(final Long consumerId, final Long price,
                                final Long remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;

    }

    /**
     *
     * @return id-ul consumatorului
     */
    public final Long getConsumerId() {
        return consumerId;
    }

    /**
     *
     * @return costul lunar al contractului
     */
    public final Long getPrice() {
        return price;
    }

    /**
     *
     * @return lunile pentru care nu sa achitat rata inca
     */
    public final Long getRemainedContractMonths() {
        return remainedContractMonths;
    }

    /**
     * Se reduce durata contractului cu o luna.
     */
    public final void setRemainedContractMonths() {
        this.remainedContractMonths--;
    }

}
