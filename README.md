# Tanks

Implementation of Tank Game.
Game was written in Java Swing.

![Start menu](game/resources/img/start.jpg)
![Stage one](game/resources/img/stage_2.jpg)

## Controls:

### Player 1:

- W to move UP
- A to move LEFT
- S to move DOWN
- D to move RIGHT
- J to FIRE

### PLayer 2:

- UP ARROW to move UP
- LEFT ARROW to move LEFT
- DOWN ARROW to move DOWN
- RIGHT ARROW to move RIGHT
- NUMPAD1 to FIRE

## Tanks

Each tanks have 3 bullets at the same time, auto reload after 0.3 seconds.
Ally tanks have 10HP, 1 damage and 2 speed.
Enemy tanks have 3HP, 1 damage and 1 speed.

Each enemy may fire only one bullet in the same time.
If bullet hits target, brick or stage border and explodes then enemy may fire next one.
Enemies may have one of four different armour levels. Each level have a different colour.
After bullet hit armour level decrease.
If the armour level falls to zero, then enemy is destroyed.

If enemy blinks, each hit create new bonus item on a map.

## Levels

Levels are plain text files in that are located in **levels** directory.
Each level is a two dimensional array with 26 rows and 26 columns.
Each field in the array should be one of following elements:

## Objects

- **.** Empty field
- **#** ![Brick wall](game/resources/img/brick.png) Brick wall: can be destroyed with three bullets
- **@** ![Stone wall](game/resources/img/stone.png) Stone wall: cant be destroyed
- **%** ![Bush](game/resources/img/bush.png) Bush: cant be destroyed but can be passed through and bullets can also shoot through it
- **~** ![Water](game/resources/img/water.png) Water: cant be destroyed, cant be passed through but bullets can shot thought it

If bullet hits target, brick or stage border, both of them will damage each other's lives, when the durability of one of them returns to 0, they will be destroyed.
