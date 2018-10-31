This folder contains the source code implementation of MudaBlue. To execute the runner on a small dataset consisting  of 4 GitHub projects, please run the following command:


  ```sh 
  $ mvn exec:java -Dexec.mainClass="it.univaq.disim.mudablue.runner.MudaBlueRun" 
  ```

To parse a new set of repositories, first set the property  (<b>evaluation.properties</b>) of evaluation.properties file as the root path of folder that contains the repository.
Then, to parse new repositories and generate corrispondig dictionaries, please run the following command:

  ```sh 
  $ mvn exec:java -Dexec.mainClass="it.univaq.disim.mudablue.runner.MudaBlueParse"
  ```

