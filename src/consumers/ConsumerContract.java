package consumers;

/**
 * Aceasta clasa este folosita pentru a stoca contractul conumatorului
 * si a actualiza datele acestuia, la ficare contract nou semnat.
 */
public class ConsumerContract {
    private Integer idDistributors;
    private Long contractLength;
    private Long rate;
    private Boolean delay;
    private Long delayCost;

    /**
     * Se reseteaza contractul unui utilizator.
     */
    public ConsumerContract() {
        this.idDistributors = 0;
        this.contractLength = 0L;
        this.rate = 0L;
        this.delay = false;
        this.delayCost = 0L;
    }


    /**
     *
     * @param idDistributors - id-ul distribuitorului cu care a fost semnat contractul
     */
    public final void setIdDistributors(final Integer idDistributors) {
        this.idDistributors = idDistributors;
    }

    /**
     * Id-ul distribuitorului.
     */
    public final Integer getIdDistributors() {
        return idDistributors;
    }

    /**
     *
     * @param contractLength - lungimea contractului
     */
    public final void setContractLength(final Long contractLength) {
        this.contractLength = contractLength;
    }

    /**
     * Durata contractului scade cu o luna.
     */
    public final void setLength() {
        contractLength--;
    }

    /**
     *
     * @return - lungimea curenta a contractului
     */
    public final Long getContractLength() {
        return contractLength;
    }

    /**
     *
     * @param delay - true daca consumatorul are o factura neachitata.
     */
    public final void setDelay(final Boolean delay) {
        this.delay = delay;
    }

    /**
     *
     * @return starea delay
     */
    public final Boolean getDelay() {
        return delay;
    }

    /**
     *
     * @param rate se seteaza rata lunara a contractului
     */
    public final void setRate(final Long rate) {
        this.rate = rate;
    }

    /**
     *
     * @return rata lunara
     */
    public final Long getRate() {
        return rate;
    }

    /**
     *
     * @return costul facturii neachitate
     */
    public final Long getDelayCost() {
        return delayCost;
    }

    /**
     *
     * @param delayCost - costul facturii neachitate
     */
    public final void setDelayCost(final Long delayCost) {
        this.delayCost = delayCost;
    }
}
