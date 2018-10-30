This folder contains the source code implementation of MudaBlue. To execute the runner on a small dataset consisting  of 4 GitHub projects, please run the following command:


  ```sh 
  $ mvn exec:java -Dexec.mainClass="it.univaq.disim.mudablue.runner.MudaBlueRun" 
  ```

If you want to parse a new set of repositories, first modify the properties file (<b>evaluation.properties</b>) and set sourceDirectory property as the root of git repositories.
To parse new repositories and generate corrispondig dictionaries, please run the following command:

  ```sh 
  $ mvn exec:java -Dexec.mainClass="it.univaq.disim.mudablue.runner.MudaBlueParse"
  ```

