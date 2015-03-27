# Introduction #

> I realize that I never really explain how to run the java project, so I will do it now. The project is build in eclipse and use the library JInput. Everything is connected to the HostServer so you need to launch that file first. The HostServer is in the network.server package. Then you can launch either InfoGUI in the user package or SimulationGame in the simulation package in any order you want.


You will see when you launch InfoGUI that it crash because of an error with JInput. The reason is that you need to set the native library of JInput. To do that, right click the library JInput and go in option. Then chose native and select the folder Native JInput into the lib folder of the project.


Then you will see that it crash again but for another reason. This time is because there is no XBOX controller connected to your PC. That mean that you can't control the car. I'm planning to add a functionality to control with keyboard so there is a fallback option when there is no xbox controller.