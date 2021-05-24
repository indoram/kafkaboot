# kafkaboot
HALC Kafka Producer , Consumer Application

Kafka Application to Demonstrate transaction between two Accounts, debit account one, credit account two


Account Transfer Model
======================
![image](https://user-images.githubusercontent.com/2889476/119256120-66778f00-bc02-11eb-96af-4a3159130157.png)



Single Account Transfer URL : http://localhost:8081/kafka/accounttransfers/setup

![image](https://user-images.githubusercontent.com/2889476/119256031-f963f980-bc01-11eb-890a-ffb4b2bd8766.png)


Click On Transfer Funds button publishes  One Transaction to Kafla Topic 
Transaction details debit account 64534,  to account 64535, amount $1.12, Using unique Transaction Reference.

![image](https://user-images.githubusercontent.com/2889476/119256228-0a613a80-bc03-11eb-8c97-2f65c1eb372c.png)


Kafka Load Generator
=====================
URL : http://localhost:8081/kafka/loadgenerator/setup

Total transactions: 1000
Randomly selects debit account , credit amount from pool of 4 accounts, amount : $1.12, generates unique transaction reference
Four accounts with current account balance

![image](https://user-images.githubusercontent.com/2889476/119256369-bacf3e80-bc03-11eb-9468-460c8b6ceb1e.png)

Click on Generate Load Button , publishes 1000 transactions to Kafka topic,
Shows total time taken to publish 1000 transactions


![image](https://user-images.githubusercontent.com/2889476/119256648-0b936700-bc05-11eb-8581-036646897586.png)
