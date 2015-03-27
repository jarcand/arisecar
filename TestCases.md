# Module 1: Remote Control #

**1.1 - Network connection**

> Start the HostServer, RobotClient, and GuardClient programs (in this respective order), check the log consoles for problems, and send a command from the Guard to the Robot to confirm transfer.

**1.2 - Move left and right**

> Start the remote control programs as explain in 1.1, then send a left-turn command, observe the turn, send a right-turn command, and observe the vehicle returning to roughly the original orientation.  The vehicle shouldn't really change position during this test.

**1.3 - Move forward and reverse**

> Start the remote control programs as explain in 1.1, then send a move-forward command, observe the vehicle moving forward, send a move-reverse command, and observe the vehicle returning to roughly the original position.  The vehicle shouldn't really change orientation during this test.

**1.4 - Move in a square**

> Start the remote control programs as explain in 1.1, then send the following commands: move-forward, turn-right, move-forward, turn-right, move-forward, turn-right, move-forward, and turn-right.  The vehicle should move in a square, turning the clockwise direction.  Ideally, the robot would find itself in the same position and same orientation as it started, but this will be verify difficult until the rotary wheel encoders are installed to measure specific distances and turns.

# Module 2: Arduinos #

**2.1 - Locomotive Arduino power up in fail-safe mode**

> Plug in the locomotive Arduino to a power source (either a 9-volt battery, or simply USB) and observe that the on-board LED is flashing quickly, indicating the fail-safe state.

**2.2 - Move each motor in each direction**

> Send the arduino a turn-forward command for the left motor, the stop command, the turn-reverse command, and the stop.  Do the same for the right motor.  Check that both wheels turn in the same direction, that the signals for the second motor are being properly reverse in software.

# Module 3: Video Streaming #

# Module 4: Machine Vision Filters #