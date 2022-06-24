# Energy system

The project is based on the simulation of an energy system in which we have different 
entities: consumers, distributors, producers, with well-defined attributions. First
was difined metod to read and store entities data, that is in JSON format. In implementation
package are two classes: 

Input - have methods to read and store data about consumers, distributors, manufacturers.

Output - store results of simulation.


Each entity has its own class to store data and perform specific actions:
## Consumers
- They want a source of energy;
- They have an initial budget + monthly income;
- They choose the contract with the lowest monthly rate;
- They pay the rate set at the beginning for a period set at the choice of the contract;
- If he runs out of money, he can postpone the payment of the invoice for a
month (including the payment of the first invoice from the contract), 
provided that in the following month he pays the invoice for the current month and last month,
but also a penalty equal to 20% of the value of the unpaid invoice;
- If the next month he can not pay to the same distributor, then he will declare bankruptcy, 
being excluded from the game;

In Consumers package are classes:

Consumers - used to store the consumers data: id, budget, income, status, contract and specific
consumers actions to pay a rate, to pay a rate with delay, to set a new contract.

ConsumerContract - this class is used to store the consumers contract data: distributor id,
contract length, rate, delay cost and methods to update its data, for each newly signed contract.

ConsumersOutput - this class is used to store the consumers data what will be desplayed in 
output file at the end of simulation.

## Distributors

- Distributors need a monthly amount of energy;
- In the first round, distributors choose one or more producers to provide them with 
the necessary amount of energy the choice is made on the basis of strategies;
- If there is a change in the amount of energy provided by a producer, then at the beginning
of the next month, distributors who take energy from that producer will again apply their
strategy of choosing producers.

In Distributors package are classes:

Distributos - this class is used to store and update distributor data, to store and update
their contracts with consumers and manufacturers.

DistributorContracts - this class is used to store the current contracts of a distributor,
with its consumers.

DistributorsOutput - this class is used to store the distributors data what will be desplayed in 
output file at the end of simulation.


## Producers
- A manufacturer will produce energy of a certain type and sell it to several distributors;
- The type of energy produced can be renewable (wind, solar, hydro) 
or not (coal, nuclear);
- Contains the monthly amount of energy provided to each distributor - provides the 
same amount of energy to each and the maximum number of distributors to which it
can supply energy;

In Producers package are classes:

Producers - this class is used to store data about manufacturers and update their contracts 
with distributors.

MonthlyStats - this class is used to update monthly the contracts signed between
producers and distributors.

ProducersOutput - this class is used to store the producers data what will be desplayed in 
output file at the end of simulation.


## Strategies
Green Strategy - a distributor chooses its producers prioritizing those with renewable
energy first, then by price, then by quantity;
Price Strategy - a distributor chooses his producers prioritizing only by price, then by
quantity;
Quantity Strategy - a distributor chooses its producers by prioritizing the amount of energy
offered per distributor;

We have classes that implements a comporator to compare producers conform strategies.


## Actions

In this package is implements the Factory Method pattern, 
its instance being used to obtain the monthly rate, monthly cost or profit.


The entry point of program is in Main, at the beginning distributors choose their producers 
according to strategies, consumers choose their distributors. After this the simulation 
begins, in each iteration:
- new consumers are added;
- distributors' prices are updated;
- the status of consumers is checked and new contracts are set;
- consumers and distributors pay for their contracts;
- producers change their prices, break old contracts and make new ones;

At the end the results of simulation is write in the ouput file.

