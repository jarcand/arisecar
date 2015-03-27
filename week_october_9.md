# Introduction #

Update 1:
I've been reading into ROS but most of the terminology is above my understanding. Hopefully Bao has absorbed more information than I have.

BUT, there was mention of a robot server called Player.

Update 2:
Battery Simulation


# Details #

Update 1:
http://playerstage.sourceforge.net/index.php?src=player
http://playerstage.sourceforge.net/wiki/Main_Page
http://java-player.sourceforge.net/

"Player's client/server model allows robot control programs to be written in any programming language and to run on any computer with a network connection to the robot."

Player uses a TCP socket-based client/server model, so we can write our control programs in Java and maybe use SSL for secure connections?

Player also contains a 2d simulator called "Stage"
"Stage simulates a population of mobile robots, sensors and objects in a two-dimensional bitmapped environment."

Youtube video of Player in action:
http://www.youtube.com/watch?v=sXFNl_Rf18I




Update 2:
Do we need the RobotBattery class? I just had the battery life as a value in RobotData class. I committed my code but it does not show up on the google home page, Marc-André can you see it?