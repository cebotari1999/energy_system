package implementation;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileReader;

import consumers.Consumers;
import distributors.Distributors;
import entities.EnergyType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import producers.Producers;
import strategies.EnergyChoiceStrategyType;


import java.util.ArrayList;
import java.util.List;

/**
 * Metodele acestei clase parseaza din fisierul de input datele despre consumatori,
 * distribuitori si producatori in instante ale claselor Consumers, Distributors si Producers,
 * care sunt stocate in vectori.
 * De asemenea sunt metode, care actualizeaza datele acestora, conform datelor din
 * fisierul de input.
 * Instanta acestei clase este utilizata pentru a avea acces la toate datele stocate despre
 * utilizatori si distribuitori.
 */

public class Input {
    private Long numberOfTurns;
    private final List<Consumers> consumers;
    private final List<Distributors> distributors;
    private final List<Producers> producers;
    private final JSONObject object;

    /**
     *
     * @param args - contine calea fisierului de input
     * @throws IOException in caz de exceptii de scriere/citire
     * @throws ParseException in caz ca fisierul primit ca parametru nu exista
     */
    public Input(final String[] args) throws IOException, ParseException {
        numberOfTurns = 0L;
        consumers = new ArrayList<>();
        distributors = new ArrayList<>();
        producers = new ArrayList<>();

        Path path = Paths.get(args[0]);
        JSONParser parser = new JSONParser();
        object = (JSONObject) parser.parse(new FileReader(String.valueOf(path)));
    }

    /**
     *
     * @return numarul total de runde
     */
    public final Long getNumberOfTurns() {
        return numberOfTurns;
    }

    /**
     *
     * @return vectorul ce contine obiecte de tip Consumers
     */
    public final List<Consumers> getConsumers() {
        return consumers;
    }

    /**
     *
     * @return vectorul ce contine instante ale clasei Distributors
     */
    public final List<Distributors> getDistributors() {
        return distributors;
    }

    /**
     *
     * @return lista producatorilor
     */
    public List<Producers> getProducers() {
        return producers;
    }

    /**
     * Apeleaza metodele ce realizeaza citirea datelor din fisierul de input
     * si le stocheaza.
     */
    public final void loadInput() {
        loadNumberOfTurns();
        loadConsumers();
        loadDistributors();
        loadProducers();
    }

    /**
     * Se stocheaza numarul total de runde.
     */
    public final void loadNumberOfTurns() {
        this.numberOfTurns = (Long) object.get("numberOfTurns");
    }

    /**
     * Se stocheaza datele consumatorilor.
     */
    public final void loadConsumers() {
        JSONObject objectInitialData = (JSONObject) object.get("initialData");
        JSONArray arrayConsumers = (JSONArray) objectInitialData.get("consumers");

        if (arrayConsumers.size() > 0) {
            for (Object objectConsumer : arrayConsumers) {
                consumers.add(Math.toIntExact((Long) ((JSONObject) objectConsumer).get("id")),
                        new Consumers(
                        (Long) ((JSONObject) objectConsumer).get("id"),
                        (Long) ((JSONObject) objectConsumer).get("initialBudget"),
                        (Long) ((JSONObject) objectConsumer).get("monthlyIncome")));
            }
        }
    }

    /**
     * Se stocheaza datele distribuitorilor.
     */
    public final void loadDistributors() {
        JSONObject objectInitialData = (JSONObject) object.get("initialData");
        JSONArray arrayDistributors = (JSONArray) objectInitialData.get("distributors");

        if (arrayDistributors.size() > 0) {
            for (Object objectDistributor : arrayDistributors) {
                distributors.add(new Distributors(
                        (Long) ((JSONObject) objectDistributor).get("id"),
                        (Long) ((JSONObject) objectDistributor).get("contractLength"),
                        (Long) ((JSONObject) objectDistributor).get("initialBudget"),
                        (Long) ((JSONObject) objectDistributor).get("initialInfrastructureCost"),
                        (Long) ((JSONObject) objectDistributor).get("energyNeededKW") ,
                         EnergyChoiceStrategyType.valueOf((String)
                                 ((JSONObject) objectDistributor).get("producerStrategy"))));
            }
        }
    }

    /**
     * Se stocheaza datele producatorilor.
     */
    public final void loadProducers() {
        JSONObject objectInitialData = (JSONObject) object.get("initialData");
        JSONArray arrayProducers = (JSONArray) objectInitialData.get("producers");

        if (arrayProducers.size() > 0) {
            for (Object objectProducers : arrayProducers) {
                producers.add(new Producers(
                        (Long) ((JSONObject) objectProducers).get("id"),
                        EnergyType.valueOf((String)
                                ((JSONObject) objectProducers).get("energyType")),
                        (Long) ((JSONObject) objectProducers).get("maxDistributors"),
                        (Double) ((JSONObject) objectProducers).get("priceKW"),
                        (Long) ((JSONObject) objectProducers).get("energyPerDistributor")));
            }
        }
    }

    /**
     * Se adauga consumatorii din luna i al simlarii.
     */
    public final void loadNewConsumers(final Integer i) {
        JSONArray arrayMonthlyUpdates = (JSONArray) object.get("monthlyUpdates");
        JSONObject objectNewConsumers = (JSONObject) arrayMonthlyUpdates.get(i);
        JSONArray arrayNewConsumer = (JSONArray) objectNewConsumers.get("newConsumers");

        if (arrayNewConsumer.size() > 0) {
            for (Object objectConsumer : arrayNewConsumer) {
                consumers.add(new Consumers(
                        (Long) ((JSONObject) objectConsumer).get("id"),
                        (Long) ((JSONObject) objectConsumer).get("initialBudget"),
                        (Long) ((JSONObject) objectConsumer).get("monthlyIncome")));
            }
        }
    }

    /**
     * Se actualizeaza datele distribuitorilor pentru luna i al simularii.
     */
    public final void loadDistributorsCostsChanges(final Integer i) {
        JSONArray arrayMonthlyUpdates = (JSONArray) object.get("monthlyUpdates");
        JSONObject objectCostsChanges = (JSONObject) arrayMonthlyUpdates.get(i);
        JSONArray arrayCostsChanges = (JSONArray) objectCostsChanges.get("distributorChanges");

        if (arrayCostsChanges.size() > 0) {
            for (Object costsChange : arrayCostsChanges) {
                long id = (Long) ((JSONObject) costsChange).get("id");
                distributors.get(Math.toIntExact(id)).setInfrastructureCost(
                        (Long) ((JSONObject) costsChange).get("infrastructureCost"));
            }
        }
    }

    /**
     * Se actualizeaza informatiile despre producatori in luna i al simularii.
     */
    public final List<Producers> loadProducersCostsChanges(final Integer i) {
        JSONArray arrayMonthlyUpdates = (JSONArray) object.get("monthlyUpdates");
        JSONObject objectCostsChanges = (JSONObject) arrayMonthlyUpdates.get(i);
        JSONArray arrayCostsChanges = (JSONArray) objectCostsChanges.get("producerChanges");
        List<Producers> producersChanges = new ArrayList<>();

        if (arrayCostsChanges.size() > 0) {
            for (Object costsChange : arrayCostsChanges) {
                long id = (Long) ((JSONObject) costsChange).get("id");
                producers.get(Math.toIntExact(id)).setEnergyPerDistributor(
                        (Long) ((JSONObject) costsChange).get("energyPerDistributor"));
                producersChanges.add(producers.get(Math.toIntExact(id)));
            }
        }

        return producersChanges;
    }
}
