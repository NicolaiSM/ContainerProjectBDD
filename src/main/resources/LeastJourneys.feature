Feature: Get the container that went on the least journeys

  Scenario: Successful determination of container that went on the least journeys
    Given A client
  	| clientname | address | contactperson | email		 | password |
		| HM	  		 | Nyvej 2 | Jens Ole 		 | JO@hm.com | admin    |
		Given Ports
		| port 	 		|
		| Oslo 	 		|
		| London 		|
		| Stockholm |
		Given A container
		| port | journey |
		| Oslo | none		 |
		Given A journey
		| port of origin | destination | content | client |
		| Oslo					 | Stockholm	 | Cheese	 | given	|
		Given A container
		| port 	 | journey |
		| London | none		 |
		When Determining the container that went on the least journeys
    Then The container that went on the least journeys is found
    
  Scenario: Unsuccesful determination of container that went on the least journeys: no containers exist
    When Determining the container that went on the least journeys
    Then No container found