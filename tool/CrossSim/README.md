This repository contains the Java implemenation of SimRank, a program for computing similarities among nodes in a graph. The original paper for the algorithm is available in this link: https://dl.acm.org/citation.cfm?id=775126

We applied SimRank in CrossSim to compute similarites among GitHub open source software projects. First we need to crawl metadata from GitHub and represent it in a directed graph. Graph is stored in file "graph", and each line in the file has the following format:

```node1#node2```

which specifies the representation of one edge in the graph: node1 -> node2.

File Dictionary.txt is used to store all artifacts included in the computation. It serves as a reference to the graph nodes. The nodes are either users/developers, projects, or dependencies (third party libraries).

SimRank then performs computation on the graph using the two files Graph.txt and Dictionary.txt to produce similarity files. For each project, a file will be generated containing all similarity scores to all the other projects of the dataset.

You should change the parameters specified in evaluations.properties to meet your configurations.

To execute the runner on a dataset consisting of 580 GitHub projects, please run the following command:

  ```sh 
  $ mvn -e exec:java -Dexec.mainClass="org.crossminer.similaritycalculator.CrossSim.Runner"
  ```
