[![DOI](https://zenodo.org/badge/112594762.svg)](https://zenodo.org/badge/latestdoi/112594762)

This repository contains tools and dataset for our paper submitted and accepted for publication in the Proceedings of the 44th Euromicro Conference on Software Engineering and Advanced Applications (SEAA 2018).

<b>CrossSim: exploiting mutual relationships to detect similar OSS projects</b>

Phuong T. Nguyen, Juri Di Rocco, Riccardo Rubei, Davide Di Ruscio

Department of Information Engineering, Computer Science and Mathematics,
Università degli Studi dell'Aquila

Via Vetoio 2, 67100 -- L'Aquila, Italy

We propose CrossSim (exploting **C**ross Project **R**elationships for Computing **O**pen **S**ource **S**oftware **Sim**ilarity), an approach that allows us to represent in a homogeneous manner different project characteristics belonging to different abstraction layers and eventually to compute similarities among projects. An evaluation on a dataset containing 580 GitHub projects shows that CrossSim outperforms an existing technique, which has been proven to have a good performance in detecting similar GitHub repositories.

# Structure of the repository
* <b>dataset</b> contains all dataset used in the evaluation. In particular:
  * <b>correlation_results.xlsx</b> stores the result of spearman correlation analysis;
  * <b>correlationScript.r</b> is the R script used to correlate human's score and sim tool rank;
  * <b>human evaluation.xlsx</b> contains the qualitative analysis results. It inludes the scores given by human evaluators;
  * <b>queries.txt</b> is the list of queries used in the evaluation;
  * <b>repository.txt</b> is the list of repositories;
  * RepoPal results are in <b>RepoPal_Results.pdf</b>;
  * CrossSim3 results are in <b>CrossSim_Results.pdf</b>;
* All supporting tools are stored in <b>tool</b>.
