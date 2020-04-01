# BATTLESHIP Game

## Bring me the Nostalgia

*Welcome back to 2005*, the time when smartphones weren't 85% of our lives. Instead of watching YouTube 
videos or posting cheesy statuses all the time on Facebook, kids back then invented creative games to play 
and spend quality time together.

**BATTLESHIP** hopes to bring you the similar experience, where you can return to 15 years ago and enjoy
this simple, but very brainy and strategic games. In this application, you can: 

- Play with your friend or practice your new, inventive strategy by playing with computer. 
- Customize the game with way bigger board and way way more battleships than you can ever play on paper.
- Try our new, customized rules!
- Kick your friends' asses and become the best in the game.
- Unlock new type of battleships.

But above all, it's free! So why not try to go on the time machine with us to travel back in time and enjoy
your childhood once again?

low_

User stories:

Stage 1:
- As a user, I want to be able to initiate the game.
- As a user, I want to start the game with default ship position on 8x8 fields.
- As a user, I want to attack the opponent during the game.
- As a user, I want to see the current status of the game.
- As a user, I want to add the ships to the game.
- As a user, I want the game to end after any player wins the game.

Stage 2:
- As a user, I want to save the current game and load it when I need.
- As a user, I want to clear data after I finished one game.

Stage 3:

**INSTRUCTION FOR GRADER

- You can generate the first required event (Add "Ship" to your board "Player") by clicking 
"START GAME", choose any mode, and then choose the coordinates and orientation for 
your ship.

- You can generate the second required event, which is just simply by playing the game 
(Add "Move" to the opponent board "Player") after generating ships and start playing.

- You can trigger my audio component by clicking almost any button in the game. And you can
see my visual component in the background of either the menu or in game.

- You can save the state of my application by the button "Save & Quit" after you start the
game and before you finished the game.

- You can reload the state of my application by the button "Load game in the main menu". 
(After you save a game while playing)

Phase 4: Task 2:

- I included a type hierachy in my code. In package src/main/model/players. The superclass
"Player" is inherited by "HumanPlayer", "RandomizedBot" and "SmartBot" (also 
"UITestPlayer", but it is not used in main code). Each subclass represents a type of player 
is chosen into the game after user has chosen a game mode. Specifically: if you choose 
1 Player Easy, it will be HumanPlayer vs RandomizedBot, if you choose Hard, it will be 
HumanPlayer vs SmartBot, and if you choose 2 players, it will be HumanPlayer vs HumanPlayer.

Phase 4: Task 3:

Problem 1: Most Panels have too similar code. I added BattleshipPanel to lessen the amount
of coupling.