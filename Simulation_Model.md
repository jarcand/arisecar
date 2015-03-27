#Describe the characteristics of the model in the simulation

# Introduction #

This page describe the characteristics of the model used in the simulation.

This document should be up to date in all time so someone can know what the model in the simulation can and cannot do.

# Current model #

The elements in the current model are the following.

  * The body of the robot
  * 2 wheels and 2 motors together
  * a red dot to indicate where the front of the vehicle is.

_**Moving behavior**_

Currently a wheel/motor can only be in 3 state : idle, full speed forward or full speed backward. That mean there is currently 9 moving possibilities

  * Not moving
  * Moving forward
  * Moving backward
  * Rotating forward around left wheel
  * Rotating backward around right wheel
  * Rotating backward around left wheel
  * Rotating backward around right wheel
  * Rotating in place counter clockwise
  * Rotating in place clockwise

# Next Model #

The goal of the next model is to get a better simulation of the moving behavior of the robot as well as getting some data about the size of the robot.

The measure that are required

  * The size of the body (the platform)
  * The diameter of a wheel
  * The space between the wheel

Change to the code

  * Movement in every direction will be possible. Need to find the center of the circle.
  * Calculate the speed relative to the number of rotation\*2PI\*Diameter of a wheel