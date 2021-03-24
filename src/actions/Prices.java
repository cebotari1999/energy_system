package actions;

import distributors.Distributors;

/**
 * Aceasta clasa implementeaza  pattern-ul Factory Method,
 * instanta ei find utilizate pentru a obtine rata lunara, costul
 * lunar sau profitul.
 */


abstract class Prices {
    public abstract Long getPrice();
    private final Distributors distributor;

    /**
     * Se stocheaza distribuitorul pentru care se vor face calculele.
     */
    Prices(final Distributors distributor) {
        this.distributor = distributor;
    }

    /**
     * Astfel metodele au acces la distribuitor.
     */
    public final Distributors getDistributor() {
        return distributor;
    }

    static class Rate extends Prices {

        Rate(final Distributors distributor) {
            super(distributor);
        }

        /**
         * Se calculeaza rata contracului.
         */
        @SuppressWarnings("IntegerDivisionInFloatingPointContext")
        public final Long getPrice() {
            long rate;
            Profit profit = new Profit(getDistributor());
            if (getDistributor().getContracts().size() > 0L) {
                rate = Math.round(Math.floor(getDistributor().getInfrastructureCost()
                        / getDistributor().getContracts().size())
                        + getDistributor().getProductionCost()
                        + profit.getPrice());
            } else {
                rate = getDistributor().getInfrastructureCost()
                        + getDistributor().getProductionCost()
                        + profit.getPrice();
            }

            return rate;
        }
    }


    static class Profit extends Prices {
        Profit(final Distributors distributor) {
            super(distributor);
        }

        /**
         * Se calculeaza profitul unui distribuitor.
         */
        public final Long getPrice() {
            long profit;
            Double precent = 0.2;

            profit = Math.round(Math.floor(precent * getDistributor().getProductionCost()));
            return profit;
        }
    }


    static class Cost extends Prices {
        Cost(final Distributors distributor) {
            super(distributor);
        }

        /**
         * Se calculeaza costul lunar al unui distribuitor.
         */
        public final Long getPrice() {
            long cost;
            cost = getDistributor().getInfrastructureCost()
                    + getDistributor().getProductionCost()
                    * getDistributor().getContracts().size();
            return cost;
        }
    }

}
