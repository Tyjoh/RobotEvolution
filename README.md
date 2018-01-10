# RobotEvolution
2D platformer for android built from scratch. Mainly meant to end up as a reusable engine at some point. (Unfinished)

## Significant features
+ Implemented custom graphics apis built on top of OpenGLES 2 ([Code](https://github.com/Tyjoh/RobotEvolution/tree/master/app/src/main/java/com/tjgs/robotevolution/graphics))

+ Tile based maps with support for 1:1 and 1:2 slopes.

+ Custom skeletal animation system. ([Code](https://github.com/Tyjoh/RobotEvolution/tree/master/app/src/main/java/com/tjgs/robotevolution/animation))

+ First attempt of a component based entity system. Function of entities is defined by the components they possess.
  * Removes the need for a heirarchical structure for new character types and features.
  * Lack of heirarchical structure removes some potential for bugs when changing base classes high up in the heirarchy.
  * Components only deal with the data and other components that concern it.
  * Easier encapsulation.

+ Game state and entities are saved and serialized using json.

+ Built a separate map editor similar to that of unity's editor (just much simpler). 
  * UI dynamically created based on the entities components and each component classes fields. (with java reflection).
  * Similar to unity you can change the values of literals with text boxes
  * Other custom editor ui's for common primitives such as 2d or 3d vectors, or more complex components such as animations.
  * (Map editor was a separate project and became out of date due to major refractoring of this project, and is not on github)
