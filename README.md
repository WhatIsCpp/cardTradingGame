 # INSTRUCTIONS
 
 install postgresql
 create datababe Login/Group Role
- Name:cardtradeservice
- Password:cardtradeservice

 create database with the user you created
- database name:cardtradeservice

 insert the initial card.sql
 
Clone repository with one of the following ways and start coding.

with HTTPS:

```shell
git clone https://github.com/WhatIsCpp/cardTradingGame.git
```

with SSH:

```shell
git clone git@github.com:WhatIsCpp/cardTradingGame.git
```

## Install

For linux go to the folder where you cloned the project and execute the script below
```shell
mvn install
```

For Windows import the project to the IDE you are using
After it is imported use your maven to install

## Start The Project

Execute the below script with correct path
```shell
java -jar /path-to-your-project/target/demo-0.0.1-SNAPSHOT.jar
```

 ## Endpoints
 
 **/api/v1/card/{cardName}**
 Returns the card with the given card name
 
 **/api/v1/game/draw/{playerName}**
 Player with the given name will draw a card if it's his turn to draw
 
 **/api/v1/game/endturn/{playerName}**
 Player with the given name will end his turn if it's his turn
 
 **/api/v1/game/play/{cardName}/{playerName}**
 Player with the given name will play the card with the given name if it's in his hand and it's his turn and has enough mana
 
 **/api/v1/game/start/{player1},{player2}**
 If the players with the given name's are not already in a game start a game from 0
 
 **/api/v1/players/{playerName}**
 Returns the player with the given name
 
 **/api/v1/players/create/{playerName}**
 Creates a user with the given name if it's not already existing
 
 **/api/v1/players/hand/{playerName}**
 Returns the current hand of the player
 
 **swagger url for testing**
 
 http://localhost:8080/swagger-ui.html#/

- enjoy ;)
