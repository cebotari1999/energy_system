package producers;

import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa este folosita a actualiza lunar contractele semnate dintre
 * producatori si consumatori.
 */
public class MonthlyStats {
   private Long month;
   private final List<Long> distributorsIds;

    /**
     *
     * @param month - se salveaza luna in care se adauga datele
     */
    public MonthlyStats(Long month) {
        this.month = month;
        this.distributorsIds = new ArrayList<>();
    }

    /**
     *
     * @param month - luna simularii in care s-au semnat contractele
     */
    public final void setMonth(final Long month) {
        this.month = month;
    }

    /**
     *
     * @return - luna pentru care s-au semnat contractele
     */
    public final Long getMonth() {
        return month;
    }

    /**
     *
     * @param distributorIds - se salveaza id-ul distribuitorului
     *                       cu care sa semnat contractul
     */
    public final void setDistributorIds(final Long distributorIds) {
        this.distributorsIds.add(distributorIds);
    }

    /**
     *
     * @return - lista cu id-urile distribuitorilor cu care producatorul
     * respectiv a semnat contracte
     */
    public final List<Long> getDistributorsIds() {
        return distributorsIds;
    }
}
