import distributors.Distributors;
import implementation.Input;
import actions.Actions;
import producers.Producers;

import java.util.List;

/**
 * Punctul de plecare pentru simulare.
 */
public final class Main {

    /**
     * pentru coding style
     */
    private Main() {
    }

    /**
     *
     * @param args contine calea spre fisierul de input si output
     * @throws Exception in caz de exceptii de scriere/citire
     *
     * Se ruleaza numberOfTurns + 1 runde si se realizeaza toate actiunile necesare.
     */
    public static void main(final String[] args) throws Exception {

        Input input = new Input(args);
        Actions actions = Actions.getInstance();

        input.loadInput();
        for (Distributors distributor : input.getDistributors()) {
            actions.applingStrategy(input, distributor);
        }
        actions.setConsumersContracts(input);
        actions.consumersPay(input);
        actions.distributorsPay(input);
        actions.setRates(input);

        for (int i = 0; i < input.getNumberOfTurns(); i++) {
            input.loadNewConsumers(i);
            input.loadDistributorsCostsChanges(i);
            actions.setRates(input);
            actions.verifyConsumersStatus(input);
            actions.setConsumersContracts(input);
            actions.consumersPay(input);
            actions.distributorsPay(input);
            List<Producers> producersChanges = input.loadProducersCostsChanges(i);
            actions.removeProducersContracts(input, producersChanges);
            actions.setMonthlyStat(input, i);
        }

        actions.writeOutput(input, args[1]);
    }
}
