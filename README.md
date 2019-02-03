# firstandroidattempt
First Android game attempt: "Ball Buster"

Alpha v.1.0.0    2/3/1019

Implemented LibGdx.
Implemented ashley.

Full Ball(Bubble) Buster game, with a little twist and a bit harder.

The player can move from left to right and shoot with the mouse towards the same color balls. Only one ball can be popped at a time, so be strategic.
It is time based. When the time hits 0, you are done.
When there are only 1 or 2 balls on screen, you can go to the next level (there can be infinite levels).
Different power ups will pop up on screen, but be careful, because one will make you feel down :(

Every entity has its own component. Some share components, like a texture component.
These are all loaded in the engine. 
All entities have their own systems. In these systems you can give the entity a "behaviour". Movement and or action(after collision for instance).
There also is a collision system, rendering system, control system.

Audio and visuals are done seperately and loaded on the Play Screen. Same goes for the GUI.

Generating levels is done seperately as well. This is done in 3 steps. 1. Level creator (placing entities where you want), 2. level rules (completion and game over), 3. starting, updating and ending the levels.

