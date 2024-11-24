# Game of GwentStoneLite

*Name*: Plesa Marian Cosmin 

*Group*: 321CAb

### *Package: RunHelpers*

This package contains the ActionDecider, MagicNumbers, ToJson classes.

- *ActionDecider Class*:
    - do the ifs in the run in Game class
    - decide what action to do
    - decide if action is valid
- *MagicNumbers Class*:
    - contains the magic numbers
- *ToJson Class*:
    - contains the method that converts the game state to json
  
### *Package: game*

This package handles the game's main structure and the method which goes to
the gameplay.

- *Game*:  
    - Does the run of the game
    - Does the deep copy of the decks
    - Does the endTurn method and the drawCard method
    - It sets the game
- *WinsManager*:  
    - It is a singleton class that manages the wins of the players
- *Command*:  
    - It is an enum that contains the commands that the players can do

### *Package: Decks*

This package contains the Decks class that handles the decks of the players.

- *Decks Class*:
    - It gives a kind of interface to the decks of the players

### *Package: cards*

This package contains the Card, Hero, Minion classes.

- *Card Class*:
    - It is the superclass of the Hero and Minion classes
    - It contains the methods that are common to both classes
- *Hero Class*:
    - It is the subclass of the Card class
    - It contains the methods that are specific to the Hero class
- *Minion Class*:
    - It is the subclass of the Card class
    - It contains the methods that are specific to the Minion class

## FEEDBACK

1. *What was the hardest part of the project?*  
    - The hardest part of the project was to vision the whole project and
how to structure it. I had to think about the classes and the methods that
I will need in the future.

2. *What did you learn from this project?*  
    - I learned how to structure a project and how to make it scalable and I
hope that I understood better the OOP principles.

3. *What I wish was different?*  
    - I wish it was not needed for the changes that were made to the repository.
The first change about the input tests could have been avoided if the right
package was created from the beginning. The second change about the structure
of the project and the ierarchy of the directories should have never happened,
because it should all be planned from the beginning. 

Per total, I am happy with the project and I hope that I will get a good grade :)