# Sorry

Hello there!

I'm currently looking for a coding job, so I decided to create an app based on the board game "Sorry" to showcase my Java language competency. Please don't sue me, Hasbro! This app was created purely for educational purposes. 

Here's a quick demo video if you'd like to see the app in action:

[https://www.youtube.com/watch?v=v00vlP1eLOI&ab_channel=Zepher319](https://www.youtube.com/watch?v=b-SJQkuDKeU&ab_channel=Zepher319)

The app is relatively simple. There are only two classes, Main Activity and BoardModel. MainActivity manipulates the BoardModel as the game preogresses and updates the UI accordingly. This time, as opposed to the static class I used in my Checkers app, I chose to use a singleton pattern for the BoardModel, just to try something different.

So how does the game logic work?

BoardModel contains five strings, each corresponding to a region of the board. Four of the strings are used to represent the home and goal zones of the four colors, while the fifth string stores the state of all of the ordinary spaces around the board where the pieces move from home to goal. 

In retrospect, I beleive a single string to represent all of the spaces on the board would have been simpler. My original thought was that by using seperate strings, I could simplify the game logic. For instance, I could code that only red pieces could move in red home/goal zones. Whereas, using a single string, I would need to define the range of indexes that represent the red home/goal zones, and prevent other pieces from ouccpying those space. However, in reality, I still had to account for a great deal of index range arithmetic, but now, the game also needed to be able to figure out which string a pieces was in, and how that string connected to the others. As an example, when a red piece moves from a normal board space into the red goal zone, it has to move between strings at a particular index. However, the index it lands in will be completely different. The normal board has 60 spaces, whereas each home/goal zone only has 13. So, the red piece might move from index 45 of the normal string, into index 6 of the red string, if it were moving 5 spaces. By the time I realized the error of my ways, I was too deep into the project to rewrite everything from scratch, as my goal was to finish the app in a week. Still, despite its flaws, or maybe beacuse of them, this was a good experience in buckling down and finishing a project within constraints. Ideally, every project we work on will be designed flawlessly from the ground up. But the reality is we often need to make the best of a bad situation. It's impossible when in the initial design phase to account for every eventuality. Inevitably, something will slip through the cracks, and we just have to push through, knowing what we know and how we would change things if we could start over. It's like life: hindsight is 20/20. But I digress. This readme is getting off topic fast.

The game has two phases, a draw phase and an action phase. The phase system was used to segment a player's turn and prevent events from occuring out of sequence. After drawing a card, the draw phase ends, and the game enters the action phase in which a player can move their pieces. Each numbered move has its own rules governing behavior depending on where a piece is on the board and what color it happnes to be. Card draw is handled by a random number generator that spits out a nunber corresponding to the index of the string array where all of the possible cards are stored.  







