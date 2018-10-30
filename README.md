

This repository contains tools and dataset for our paper submitted and accepted for publication in:
1. the Proceedings of the 44th Euromicro Conference on Software Engineering and Advanced Applications (SEAA 2018).


<b>CrossSim: exploiting mutual relationships to detect similar OSS projects</b> ([Link](https://ieeexplore.ieee.org/abstract/document/8498236))

Phuong T. Nguyen, Juri Di Rocco, Riccardo Rubei, Davide Di Ruscio

Department of Information Engineering, Computer Science and Mathematics,
Università degli Studi dell'Aquila

Via Vetoio 2, 67100 -- L'Aquila, Italy

The tools and dataset that support this paper are available in the release https://github.com/crossminer/CrossSim/tree/0.0.1 
[![DOI](https://zenodo.org/badge/112594762.svg)](https://zenodo.org/badge/latestdoi/112594762)

2. Software Quality Journal

<b>An Automated Approach to Assess the Similarity of GitHub Repositories</b>
Phuong T. Nguyen, Juri Di Rocco, Riccardo Rubei, Davide Di Ruscio

Department of Information Engineering, Computer Science and Mathematics,
Università degli Studi dell'Aquila

Via Vetoio 2, 67100 -- L'Aquila, Italy
:
We propose CrossSim (exploting **C**ross Project **R**elationships for Computing **O**pen **S**ource **S**oftware **Sim**ilarity), an approach that allows us to represent in a homogeneous manner different project characteristics belonging to different abstraction layers and eventually to compute similarities among projects. An evaluation on a dataset containing 580 GitHub projects shows that CrossSim outperforms an existing technique, which has been proven to have a good performance in detecting similar GitHub repositories.

# Structure of the repository
* <b>dataset</b> contains all dataset used in the evaluation. In particular:
  * <b>queries.txt</b> is the list of queries used in the evaluation;
  * <b>human evaluation.xlsx</b> contains the qualitative analysis results. It inludes the scores given by human evaluators;
  * <b>repository.txt</b> is the list of repositories;
  * RepoPal results are in <b>RepoPal_Results.csv</b>;
  * CrossSim3 results are in <b>CrossSim_Results.csv</b>;
  * MudaBlue results are in <b>MudaBlue_Results.csv</b>;
  * CLAN results are in <b>Clan_Results.csv</b>;
* All supporting tools are stored in <b>tool</b>.

# How to cite
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
