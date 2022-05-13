# FlightGear Simulator Project

This project is a part of an advance software programming course in our 2nd year.

The goal of the FlightGear project is to create a sophisticated and open flight simulator framework.

This project requires a high level of knowledge in programming, with emphasis on design patterns and programming principles such as SOLID and GRASP,
architectural pattern such as MVVM, MVC and developing our own JavaFX desktop application.

The project has 3 parts - Front, Back, Agent.

<h2>Front-</h2>

Desktop app enable the feasibility of controlling and controlling the fleet.
![front]
<img alt="Front architecture" src="./images/architecture/Front.svg">

<h3>components - </h3>

![compomemt]
<img alt="Front Component architecture" src="./images/architecture/Front Component.svg">

<h2>Back-</h2>

Stores the information collected from the fleet (on a DB server) and provides them with managed access.

![back]
<img alt="BackEnd architecture" src="./images/architecture/BackEnd.svg">

<h2>Agent -</h2>

Installed on any aircraft (on any computer on which we run a flight simulator instance),
Collects information about aircraft activity,
streams data during flight and transmits various commands.

![agent]
<img alt="Agent architecture" src="./images/architecture/Agent.svg">

  <h2>Build with</h2>
  <ul>
  <li>Intellij</li>
  <li>  Scene Builder</li>
 
  </ul>
