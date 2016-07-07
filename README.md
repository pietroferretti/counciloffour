# Council of Four
*This project, made for the 2016 Software Engineering course at Politecnico di Milano, is an implementation in Java of the board game "Council of Four", originally made by [Cranio Creations](http://www.craniocreations.it/).*

## How to start the game

### Server
**To start the server run the file `src/main/java/it/polimi/ingsw/ps14/server/Server.java`. 
You can pass the maximum number of players for a game as an argument, otherwise it will default to 4.**

The server is in charge of accepting connections from the clients and creating games.
After being started, the server waits for socket and RMI connections.

After two clients connect to the server, the server starts a 20 seconds timer and waits for more players before creating a new game. If the number of player connected reaches the maximum amount of players allowed for a game (set at the creation of the Server) the game starts immediately, otherwise a new game starts anyway after the timer runs out.

The server is able to accept an arbitrary number of connections.
After a game is created the game continues on different threads, while the server goes back to waiting for new connections to start a new game.

 
### Client
**To start the client run the file `src/main/java/it/polimi/ingsw/ps14/client/Client.java`.**

The client will ask for:
* the player name
* if you want to use a CLI or a GUI
* the server's IP address (leave empty if the server is local)
* if you want to connect via sockets or RMI


## How to play
There is turn timer, configurable in Settings, which reset itself every user's action and when it's over it'll be the next player turn.


### How to play with the CLI
You can input 'help' or 'instructions' to see the available commands at any moment in the game.

#### Commands:

*Main Actions:*

* `elect <coast|hills|mountains|king> <color>` -- elect a councillor in the chosen balcony
* `acquire <coast|hills|mountains> <permit_id> <card_color> [<card_color> ...]` -- acquire the permit with id 'permit_id' from the chosen region using the cards specified
* `build-with-king <city_name> <card_color> [<card_color> ...]` -- build an emporium in the city 'city_name' with the help of the king using the cards specified
* `build-with-permit <city_name> <permit_id>` -- build an emporium in the city 'city_name' using the permit specified

*Quick Actions:*

* `engage` -- engage an assistant
* `change <coast|hills|mountains>` -- change the face up business permits in the region specified
* `main` -- perform another main action
* `elect-with-assistant <coast|hills|mountains|king> <color>` -- elect a councillor in the chosen balcony with the help of an assistant

*Market*

* `sell [business <id1>-<price> [<id2>-<price>,...]] [assistants <num>-<price> [<num>-<price> ...]] [politic <color1>-<price> [<color2>-<price>,...]]` -- sell things that you own
* `sell none` -- don't sell anything
* `buy <item_id> [<quantity>]` -- buy stuff, if you want to buy some of the assistants in a bundle insert the quantity
* `buy finish` -- end your buying phase

*Miscellanea*

* `draw` -- draw a politic card (to be used at the beginning of a turn)
* `pass` -- pass the turn (only if the main action has been done already)
* `show <mydetails|otherdetails|gameboard>` -- show details about the game or the players
* `chat <your_message_here>` -- send a message to all the players via the chat
* `choose <id1> [<id2> ...]` -- choose one of the choices offered by the server when asked for
* `results` -- show the end results (after the game is over)
* `exit` -- close the game

Every turn the CLI will suggest the command that can be done in that specific moment.

If you try to do the wrong action, or you don't have all the items necessary to perform one (e.g. not enough coins to move the king) a failure message will appear, and nothing will be changed.


### How to play with the GUI
After the connection is made, the GUI will wait until the game starts to load the map.

#### Understanding the GUI
The GUI is fairly straightforward, but some things probably aren't obvious:

* the map: you can hover over each city to see its bonuses and which players built an emporium there;
* you can see your permits by clicking on the "show permits" button;
* all the informations about the status of the game and the outcomes of your actions will appear in the text box at the bottom of the window.

#### Playing with the GUI
All the buttons in the GUI work really similarly to the CLI commands. When you want to perform an action just press the button and insert the information each dialog needs.

Remember to double check the permit ids and the city names, or else your moves will be rejected.

You can chat with the other players in the chatbox to the bottom left, have fun!


## Customizing games
It's possibile to configure the game and change many parameters from the settings file, located at `src/main/resources/settings.json`.


### Misc. settings
* `turntimer` -- the turn timeout, in seconds.
* `balconycouncillors` -- the number of councillors per balcony.
* `availablecouncillors` -- the number of councillors available in the game, for each color.
* `assistants` -- the number of assistants available in the game.
* `numcoloredcards` -- the number of politic cards in the deck, for each regular color.
* `numjollycards` -- the number of multi-coloured politic cards in the deck.
* `bonuses` -- the various victory points bonuses obtained when completing a set of cities.
* `mapname` -- the name of the map used by the game.
* `tokens` -- the tokens that will be distributed between the cities (they must be at least as many as the cities, excluding the purple one).
* `nobilitytrack` -- the bonuses associated to each level of the nobility track.


### Maps
You can choose the map used in the game from the `mapname` field in `settings.json`.

If you change the map you must make sure that both the server and the client have the necessary files.

The server must have a `<mapname>.json` file that defines the city names, colors, the links between the cities and how are they distributed in the three regions.
Furthermore `<mapname>.json` contains a list of business permits for each region, used as business permit decks.

If you're using a GUI, the client must have three images corresponding to the maps of each region, and their own version of the `<mapname>.json` file, containing the path to the images and the position (in pixels) of the cities inside the map images.

## Directory structure
* `src/main/java` -- the complete source code of the game
* `src/main/resources` -- contains the configuration files and all the resources used by the game
  * `src/main/resources/maps` -- contains the map configuration files
    * `src/main/resources/maps/images` -- contains the images used by the maps
* `src/test/java` -- contains all the tests used to verify the code's correctness

## Authors
Pietro Ferretti
Nicole Gervasoni 
Federico Oldani

*All the images and assets from the Council of Four board game belong to their respective owners.*

## License
   Copyright 2016 Pietro Ferretti, Nicole Gervasoni, Federico Oldani

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use these files except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
