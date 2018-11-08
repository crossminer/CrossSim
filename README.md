CrossSim: Exploting **C**ross Project **R**elationships for Computing **O**pen **S**ource **S**oftware **Sim**ilarity
================
 
CrossSim is a versatile tool by exploiting the graph representation: it can incorporate various features into the similarity computation, e.g., third-party libraries, API funtion calls, package names, to name a few. An evaluation on a dataset containing 580 GitHub projects shows that the tool outperforms MUDABlue, CLAN, and RepoPal with respect to different quality metrics.

This repository contains tools and dataset for the following papers:  

1. A paper published in the Proceedings of the 44th Euromicro Conference on Software Engineering and Advanced Applications (SEAA 2018).

<b>CrossSim: exploiting mutual relationships to detect similar OSS projects</b> ([Link](https://ieeexplore.ieee.org/abstract/document/8498236))

Phuong T. Nguyen, Juri Di Rocco, Riccardo Rubei, Davide Di Ruscio

Department of Information Engineering, Computer Science and Mathematics,
Università degli Studi dell'Aquila

Via Vetoio 2, 67100 -- L'Aquila, Italy

The tools and dataset that support this paper are available in the release https://github.com/crossminer/CrossSim/tree/0.0.1 

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1252866.svg)](https://doi.org/10.5281/zenodo.1252866)


2. A paper submitted to the Software Quality Journal

<b>An Automated Approach to Assess the Similarity of GitHub Repositories</b>

Phuong T. Nguyen, Juri Di Rocco, Riccardo Rubei, Davide Di Ruscio

Department of Information Engineering, Computer Science and Mathematics,
Università degli Studi dell'Aquila

Via Vetoio 2, 67100 -- L'Aquila, Italy
The tools and dataset that support this paper are available in the release https://github.com/crossminer/CrossSim/releases/tag/0.0.2 

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1479309.svg)](https://doi.org/10.5281/zenodo.1479309)

Running CrossSim
-----------
To execute CrossSim on a dataset consisting of 580 (tool/CrossSim/evalaution.properties file specifies the input path of mined data)  GitHub projects, please run the following command:

  ```sh 
  $ mvn -e exec:java -Dexec.mainClass="org.crossminer.similaritycalculator.CrossSim.Runner"
  ```

Input/Output Formats
--------------------

#### Input Format

CrossSim takes as input two files: Graph is stored in file "graph", and each line in the file has the following format:

```node1#node2```

which specifies the representation of one edge in the graph: node1 -> node2.

File "dictionary" is used to store all the artifacts included in the computation. It serves as a reference to the graph nodes. The nodes are either:
* users/developers, 
*projects, or 
*dependencies (third party library).


#### Output Format

CrossSim outputs a matrix of similarity scores. This matrix is stored in a set of files (one file for each project in the dataset). Each file contains the ranked list of similarity to all the other projects in the dataset.
For example, the first few lines in the output file ```AKSW_RDFUnit.txt``` for the usage example above are:

```text
git://github.com/AKSW/RDFUnit.git git://github.com/AKSW/RDFUnit.git 1.0
git://github.com/AKSW/RDFUnit.git git://github.com/pyvandenbussche/sparqles.git 0.0020606061443686485
git://github.com/AKSW/RDFUnit.git git://github.com/dbpedia/links.git  0.001839826931245625
git://github.com/AKSW/RDFUnit.git git://github.com/rdfhdt/hdt-java.git  0.001507760607637465
git://github.com/AKSW/RDFUnit.git git://github.com/AKSW/Sparqlify.git 9.407114703208208E-4
git://github.com/AKSW/RDFUnit.git git://github.com/streamreasoning/CSPARQL-engine.git 8.780991774983704E-4
git://github.com/AKSW/RDFUnit.git git://github.com/jprante/elasticsearch-plugin-rdf-jena.git  7.993730832822621E-4
git://github.com/AKSW/RDFUnit.git git://github.com/AKSW/jena-sparql-api.git 7.858243770897388E-4
git://github.com/AKSW/RDFUnit.git git://github.com/nkons/r2rml-parser.git 7.024793303571641E-4
git://github.com/AKSW/RDFUnit.git git://github.com/castagna/freebase2rdf.git  6.818181718699634E-4
```


RepoPal, MUDABlue, and CLAN
----------------

For comparison purposes, we implemented [RepoPal](http://ieeexplore.ieee.org/document/7884605/), [MUDABlue](http://ieeexplore.ieee.org/document/1371919/), addition [CLAN](http://ieeexplore.ieee.org/document/6227178/). These are provided in the *tool/RepoPal*, *tool/MudaBlue*, and *tool/CLAN* folder respectively. Each folder contains a readme file that describes how to run the corresponding tool.                                                                  

Datasets
--------

The dataset used in the paper is available in the ```dataset/``` subdirectory. In particular:
  * <b>queries.txt</b> is the list of queries used in the evaluation;
  * <b>human evaluation.xlsx</b> contains the qualitative analysis results. It inludes the scores given by human evaluators;
  * <b>repository.txt</b> is the list of repositories;
  * RepoPal results are in <b>RepoPal_Results.csv</b>;
  * CrossSim3 results are in <b>CrossSim_Results.csv</b>;
  * MudaBlue results are in <b>MudaBlue_Results.csv</b>;
  * CLAN results are in <b>Clan_Results.csv</b>;


Bugs
----

Please report any bugs using GitHub's issue tracker.


How to cite
================
If you use the tool or the dataset in your research, please cite our work using the following BibTex entry:

```
@INPROCEEDINGS{8498236, 
   author={P. T. Nguyen and J. Di Rocco and R. Rubei and D. Di Ruscio}, 
   booktitle={2018 44th Euromicro Conference on Software Engineering and Advanced Applications (SEAA)}, 
   title={CrossSim: Exploiting Mutual Relationships to Detect Similar OSS Projects}, 
   year={2018}, 
   volume={}, 
   number={}, 
   pages={388-395}, 
   keywords={Libraries;Open source software;Ecosystems;Semantics;Computational modeling;Software systems;Mining software repositories, software similarities, SimRank}, 
   doi={10.1109/SEAA.2018.00069}, 
   ISSN={}, 
   month={Aug}
}

```

