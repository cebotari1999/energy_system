package consumers;

/**
 * Aceasta clasa este folosita pentru a stoca informatile despre consumatori,
 * ce urmeaza sa fie afisate in fisierul de output.
 */
public class ConsumersOutput {
    private final Long id;
    private final Boolean isBankrupt;
    private final Long budget;

    /**
     *
     * @param id-ul consumatorului
     * @param isBankrupt - statutul financiar
     * @param budget - bugetul consumatorului
     */
    public ConsumersOutput(final Long id, final Boolean isBankrupt, final Long budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
    }

    /**
     *
     * @return id-ul consumatorului
     */
    public final Long getId() {
        return id;
    }

    /**
     *
     * @return satutul financiar
     */
    public final Boolean getIsBankrupt() {
        return isBankrupt;
    }

    /**
     *
     * @return bugetul
     */
    public final Long getBudget() {
        return budget;
    }


}
