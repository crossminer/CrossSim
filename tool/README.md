This repository contains the Java implemenation of SimRank, a program for computing similarities among nodes in a graph. The original paper for the algorithm is available in this link: https://dl.acm.org/citation.cfm?id=775126

We applied SimRank in CrossSim to compute similarites among GitHub open source software projects. First we need to crawl metadata from GitHub and represent it in a graph. Graph is stored in file "graph", and each line in the file has the following format:

```node1#node2```

which specifies the representation of one edge in the graph: node1 -> node2.

File "dictionary" is used to store all the artifacts included in the computation. They are either users/developers, projects, or dependencies (third party library). The dictionary is necessary to maintain a reference to the nodes.

SimRank then performs computation on the graph using the two files graph and dictionary to produce similarity files. For each project, a file will be generated containing all similarity scores for all the other projects of the dataset.

You need to changes the parameter specified in evaluations.properties to meet your configurations.



