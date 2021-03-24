package actions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import consumers.Consumers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import distributors.DistributorContracts;
import distributors.Distributors;
import distributors.DistributorsOutput;
import implementation.*;
import producers.Producers;
import producers.ProducersOutput;
import strategies.GreenStrategy;
import strategies.IdStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;
import strategies.EnergyChoiceStrategyType;
import consumers.ConsumersOutput;

import static actions.PricesFactory.PriceType.rate;

/**
 * Actrions este o clasa Singleton, initializata lazy.
 * Instanta acestei clase este folosita pentru a avea acces la
 * metodele ei, care realizeaza actiunile necesare pentru simulare.
 */
public final class Actions {
    private static Actions actions = null;

    private Actions() { }

    /**
     *
     * @return actions - singura instanta a clasei Actions.
     */
    public static Actions getInstance() {
        if (actions == null) {
            actions = new Actions();
        }
        return actions;
    }

    /**
     * @param distributor - pentru distribuitor se aplica stategia de alegere
     *                    a producatorilor
     */
    public void applingStrategy(final Input input,
                                      final Distributors distributor) {
        if (distributor.getProducersId().size() == 0 && !distributor.getBankrupt()) {
            if (distributor.getProducerStrategy().equals(EnergyChoiceStrategyType.GREEN)) {
                input.getProducers().sort(new GreenStrategy());
            }

            if (distributor.getProducerStrategy().equals(EnergyChoiceStrategyType.PRICE)) {
                input.getProducers().sort(new PriceStrategy());
            }

            if (distributor.getProducerStrategy().equals(EnergyChoiceStrategyType.QUANTITY)) {
                input.getProducers().sort(new QuantityStrategy());
            }

            setProducersContracts(distributor, input.getProducers());
        }

        input.getProducers().sort(new IdStrategy());
    }

    /**
     *Distribuitorii semneaza contractele cu producatorii care le pot oferi energie,
     *  pana nu isicumpara cantitatea de energie necesara. La final se stabileste
     *  costul productiei pe care distribuitorii il achita in fiecare luna.
     */
    public void setProducersContracts(final Distributors distributor,
                                            final List<Producers> producers) {

        int i = 0;
        long totalEnergy = 0;
        double cost = 0;
        while (totalEnergy < distributor.getEnergyNeededKW() && i < producers.size()) {
            if (producers.get(i).getMaxDistributors()
                    > producers.get(i).getNumberOfDistributor()) {
                cost = cost + producers.get(i).getEnergyPerDistributor()
                        * producers.get(i).getPriceKW();
                totalEnergy = totalEnergy + producers.get(i).getEnergyPerDistributor();
                distributor.setNewProducers(producers.get(i).getId());
                producers.get(i).setContract();
            }

            i++;
        }

        distributor.setProductionCost(Math.round(Math.floor(cost / 10)));
    }

    /**
     * In acesta metoda se creeaza contracte noi pentru utilizatorii care nu au dat faliment
     * si nu au un contract sau le-a expirat contractul.
     */
    public void setConsumersContracts(final Input input) {

        int distributor = Math.toIntExact(getBestPrice(input));
        PricesFactory.PriceType type = rate;
        Long rate = PricesFactory.getType(type, input.getDistributors().get(distributor)).getPrice();

        for (Consumers consumer : input.getConsumers()) {
            if (consumer.getContract().getContractLength() == 0 && !consumer.getBankrupt()
                    && !consumer.getContract().getDelay()) {
                input.getDistributors().get(consumer.getContract().getIdDistributors()).
                        removeContract(consumer.getId());
                input.getDistributors().get(distributor).setContracts(consumer.getId(), rate);
                input.getDistributors().get(distributor).setContractCost(rate);
                consumer.setNewContract();
                consumer.setContract(input.getDistributors().get(distributor), rate);

            }
        }
    }

    /**
     * Aici consumatorii isi primiesc salariul si achita contractele, daca nu au dat faliment.
     * In cazul in care un utilizator nu poate achita o factura, atunci delay devine true,
     * asta insemnand ca are o factura neplatita. Daca nu pot achita factura veche si una
     * noua luna viitoare, inseamna ca nu isi permit o sursa de energie si dau faliment.
     */
    public void consumersPay(final Input input) {
        for (Consumers consumer : input.getConsumers()) {
            if (!consumer.getBankrupt()) {
                consumer.setBudgetMonthly();

                if (consumer.getBudget() - consumer.getContract().getRate() >= 0
                        && !consumer.getContract().getDelay()) {
                    consumer.payRate();
                    input.getDistributors().get(consumer.getContract().getIdDistributors()).
                            setBudget(consumer.getContract().getRate());

                } else if (consumer.getBudget() - consumer.getContract().getRate() < 0
                                && !consumer.getContract().getDelay()) {
                        consumer.getContract().setDelay(true);
                        consumer.getContract().setDelayCost(consumer.getContract().getRate());

                } else if (consumer.getBudget() - (Math.round(Math.floor(
                        1.2 * consumer.getContract().getDelayCost()))
                        + consumer.getContract().getRate()) >= 0
                        && consumer.getContract().getDelay()) {
                    consumer.payRateAndDelay();
                    input.getDistributors().get(consumer.getContract().getIdDistributors()).
                            setBudget((Math.round(Math.floor(
                            1.2 * consumer.getContract().getDelayCost()))
                            + consumer.getContract().getRate()));
                } else {
                    consumer.setBankrupt(true);
                }
            }
            consumer.getContract().setLength();
        }
    }

    /**
     * Distribuitorii care nu au dat faliment achita costurile lunare,
     * se verifica daca careva a dat faliment.
     * Distribuitorul care au dat faliment sunt eliminati din joc.
     */
    public void distributorsPay(final Input input) {
        for (Distributors distributor : input.getDistributors()) {
            if (!distributor.getBankrupt()) {
                distributor.pay(PricesFactory.getType(PricesFactory.PriceType.cost, distributor).getPrice());
                verifyDistributorStatus(distributor, input);
            }
        }

        setContractsLength(input);

    }

    /**
     * Se retin toti distribuitorii cu care producatorii au avut contracte,
     * in luna respectiva.
     */
    public void setMonthlyStat(final Input input, final Integer month) {
        for (Producers producer : input.getProducers()) {
            producer.setMonth(Long.valueOf(month));
            for (Distributors distributor : input.getDistributors()) {
                if (distributor.getProducersId().contains(producer.getId())) {
                    producer.setDistributorId(Long.valueOf(month), distributor.getId());
                }
            }
        }
    }

    /**
     * Se reseteaza durata contractelor in fiecare luna.
     */
    public void setContractsLength(final Input input) {
        for (Distributors distributors : input.getDistributors()) {
            for (DistributorContracts distributorContracts : distributors.getContracts()) {
                distributorContracts.setRemainedContractMonths();
            }
        }
    }

    /**
     * Distribuiitorii lunar isi calculeaza costul contractelor contractelor.
     */
    public void setRates(final Input input) {
        for (Distributors distributor : input.getDistributors()) {
            distributor.setContractCost(PricesFactory.getType(rate, distributor).getPrice());
        }
    }

    /**
     * Se verifica daca un utilizator a dat faliment.
     */
    public void verifyDistributorStatus(final Distributors distributor,
                                              final Input input) {
        if (!distributor.getBankrupt()) {
            if (distributor.getBudget() < 0L) {
                distributor.setBankrupt();
            }

            if (distributor.getBankrupt()) {
                bankruptDistributor(input);
            }
        }
    }

    /**
     * Daca un distribuitor a dat faliment, atunci pentru toti consumatorii lui,
     * se incheie contractele.
     */
    public void bankruptDistributor(final Input input) {
        for (Distributors distributor : input.getDistributors()) {
            if (distributor.getBankrupt()) {
                for (int j = distributor.getContracts().size() - 1; j >= 0; j--) {
                    input.getConsumers().get(Math.toIntExact(
                            distributor.getContracts().get(j).getConsumerId())).setNewContract();
                    distributor.getContracts().remove(j);
                }
            }
        }
    }

    /**
     * Pentru toti distribuitorii, ai caror producatori, si-au modifcat cantitatea de
     * energie pe care o ofera lunar, se rup toate contractele si se semneaza
     * altele noi.
     */
    public void removeProducersContracts(final Input input,
                                         final List<Producers> producers) {
        boolean state;
        for (Distributors distributor : input.getDistributors()) {
            state = false;

            for (Producers producer : producers) {
                if (distributor.getProducersId().contains(producer.getId())) {
                    producer.removeContract();
                    state = true;
                }
            }

            if (state) {
                distributor.resetProducers();
                applingStrategy(input, distributor);
            }
        }
    }

    /**
     * Contractele utilizatorilor care au dat faliment se incheie.
     */
    public void verifyConsumersStatus(final Input input) {
        for (Consumers consumer : input.getConsumers()) {
            if (consumer.getBankrupt()) {
                int delete = consumer.getContract().getIdDistributors();
                input.getDistributors().get(delete).removeContract(consumer.getId());
            }
        }
    }

    /**
     * Aceasta functie returneaza ID- ul distribuitorul care ofera cea
     * mai buna rata lunara.
     */
    public Long getBestPrice(final Input input) {
        long bestPrice = 0;
        long distributorId = 0;
        PricesFactory.PriceType rate = PricesFactory.PriceType.rate;

        for (Distributors distributor : input.getDistributors()) {
            if (!distributor.getBankrupt()) {
                bestPrice = PricesFactory.getType(rate, distributor).getPrice();
                distributorId = distributor.getId();
                break;
            }
        }

        for (Distributors distributor : input.getDistributors()) {
            if (!distributor.getBankrupt()) {
                if (bestPrice > PricesFactory.getType(rate, distributor).getPrice()) {
                    distributorId = distributor.getId();
                    bestPrice = PricesFactory.getType(rate, distributor).getPrice();
                }
            }
        }

        return distributorId;
    }

    /**
     * Se scriu rezultatele simularii in fisierul de output.
     */
    public void writeOutput(final Input input,
                            final String args) throws IOException {
        Output output = new Output();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Consumers consumer : input.getConsumers()) {
            output.setConsumers(new ConsumersOutput(
                    consumer.getId(),
                    consumer.getBankrupt(),
                    consumer.getBudget()));

        }

        for (Distributors distributor : input.getDistributors()) {
            output.setDistributors(new DistributorsOutput(
                    distributor.getId(),
                    distributor.getEnergyNeededKW(),
                    distributor.getContractCost(),
                    distributor.getBudget(),
                    distributor.getBankrupt(),
                    distributor.getProducerStrategy(),
                    distributor.getContracts()));
        }

        for (Producers producer : input.getProducers()) {
            output.setEnergyProducers(new ProducersOutput(
                    producer.getId(),
                    producer.getEnergyType(),
                    producer.getMaxDistributors(),
                    producer.getPriceKW(),
                    producer.getEnergyPerDistributor(),
                    producer.getMonthlyStats()));
        }



        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(args), output);

    }

}
