package actions;

import distributors.Distributors;

public class PricesFactory {
    public enum PriceType {
        cost, profit, rate
    }

    /**
     *
     * @param priceType tipul pretului calculat
     * @param distributor-ul pentru care se face calculul
     * @return - pretul final
     */
    public static Prices getType(final PriceType priceType,
                                 final Distributors distributor) {
        return switch (priceType) {
            case cost -> new Prices.Cost(distributor);
            case profit -> new Prices.Profit(distributor);
            case rate -> new Prices.Rate(distributor);
        };
    }
}

