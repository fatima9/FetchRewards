# FetchRewards

Export the project in any IDE as existing gradle project. 
In the terminal go to the project directory and build the project with ./gradlew build. 
Start the application in the IDE as spring boot app.
In the postman, make a post call by importing the below curl

TO ADD A TRANSACTION

curl --location --request POST 'http://localhost:8080/fetchRewardsService/transactions' \
--header 'Content-Type: application/json' \
--data-raw '{ "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00Z" }'

TO GET POINTS

curl --location --request GET 'http://localhost:8080/fetchRewardsService/points'

TO SPEND POINTS

curl --location --request POST 'http://localhost:8080/fetchRewardsService/points' \
--header 'Content-Type: application/json' \
--data-raw '{
    "points": 5000
}'


