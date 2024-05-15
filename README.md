# TanksGame

Implementation of Tank Game. \n
Game was written in Java Swing. \n
Enemy tanks are going to destroy the ally base. The player's mission is to protect the base. \n

![Start menu](game/src/img/start.jpg)
![Stage one](game/src/img/stage_2.jpg)

## Controls:

### Player 1:

- `W` to move `UP`
- `A` to move `LEFT`
- `S` to move `DOWN`
- `D` to move `RIGHT`
- `J` to `FIRE`

### PLayer 2:

- `UpArrow` to move `UP`
- `LeftArrow` to move `LEFT`
- `DownArrow` to move `DOWN`
- `RightArrow` to move `RIGHT`
- `Numpad 1` to `FIRE`

## Tanks

Each tanks have 3 bullets at the same time, auto reload after 0.3 seconds. \n
Ally tanks have 10HP, 1 damage and 2 speed. \n
Enemy tanks have 3HP, 1 damage and 1 speed. \n

## Levels

Levels are plain text files in that are located in **levels** directory. \n
Each level is a two dimensional array with 26 rows and 26 columns. \n
Each field in the array should be one of following elements: \n

## Objects

- **.** Empty field
- **#** ![Brick wall](game/src/img/brick.png) Brick wall: can be destroyed with three bullets
- **@** ![Stone wall](game/src/img/stone.png) Stone wall: cant be destroyed
- **%** ![Bush](game/src/img/bush.png) Bush: cant be destroyed but can be passed through and bullets can also shoot through it
- **~** ![Water](game/src/img/water.png) Water: cant be destroyed, cant be passed through but bullets can shot thought it

If bullet hits target, brick or stage border, both of them will damage each other's lives, when the durability of one of them returns to 0, they will be destroyed.
