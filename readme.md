# PUTflap
PUTflap is an extension of JFLAP, which makes JFLAP features available from the command line. It can report results as JSON and is capable of performing operations on many arguments at once. PUTflap will help students and researchers with their work and also it is a foundation for developing web application, which will visualize JFLAP operations in web browser and provide REST API.

Task can be done with _[CLI](#cli)_ or automated with _[smart](#smart)_.

Application is usable, although some features are still missing. Contribution is open, but as it is university project, further consultation with maintainers is urged. More: [development](#development) and [credits](#credits)

## features
The aim of this project is to support full JFLAP functionality from command line.

Every operation provides its results as a JSON file. Optionally if the result can be written as JFLAP file, it can output such file.

### current support
#### Finite State Automaton
- generation of random, deterministic FSA
- generation of random valid word
- run FSA for given input
- test for nondeterminism
- test for lambda transitions
- test of equivalence of two FSAs
- retrieve alphabet
- conversion to deterministic FSA
- conversion to minimal FSA
- conversion to grammar
- conversion to regular expression
- convert from `.jff` to `.json`

#### Mealy and Moore Machine
- generation of random, deterministic machine
- generation of random valid word
- run machine for given input
- test for nondeterminism
- test for lambda transitions
- retrieve input and output alphabet
- convert from `.jff` to `.json`

#### Pushdown Automaton
- generation of random valid word
- run PDA for given input
- test for nondeterminism
- test for lambda transitions
- retrieve alphabet
- conversion to grammar
- convert from `.jff` to `.json`

> In order to properly run PDA or generate word for it, transitions to its final state **must** read _lambda_, pop and push _Z_. 

#### grammar
- generation of random regular grammar
- retrieve alphabet
- conversion of regular grammar to FSA
- convert from `.jff` to `.json`

## usage
Gradle tasks implemented in project generate two equally capable version of PUTflap: **jar** and **Windows exe**.

### build and run
#### jar
**build:** `./gradlew clean build shadowJar` build result is saved in `build/libs`

 **run:** `java -jar putflap.jar ARGS`

#### Windows
**build:** `gradlew.exe clean build shadowJar createExe` build result is saved in `build/exe`

**run:** `putflap.exe ARGS`

### CLI
The _command line interface_ is divided into six sections. Each of them is described below. Short description and arguments are also available from _CLI_ with `-h` flag.

#### random
generation of random automatons and grammars

flags:
* `-t`, `--type` - type of structure to generate: `fsa` - finite state automaton, `moore` - Moore machine, `mealy` - Mealy machine, `regr` - regular grammar. All generated structures are deterministic.
* `-n`, - number of states
* `-f`, `--finals` - number of final states. Has no effect on Mealy and Moore machines. Default = 1
* `-m`, `--multiple` - number of structures to generate. Default = 1
* `-j`, `--json` - write answer as json file

arguments:
* `alpahbet` - alphabet to generate automaton on. Symbols can be single or multiple letters. The generator might but does not have to use all of given symbols. For Mealy and Moore machines is a template for input and output alphabet

sample usage: `java -jar putflap.jar random -t fsa -n 10 -f 3 a b c d e f g h`

#### run
running automatons for given inputs

flags:
* `-i`, `--input` - name of file with automaton to run

arguments:
* `words` - words to run given automaton on

sample usage: `java -jar putflap.jar run -i automaton.jff abc acb bac`

#### test
check of specific characteristics of given automatons and grammars

flags:
* `-t`, `--type` - type of test to perform: `ndet` - check if automaton is deterministic, `eq` - check equivalence of two or more FSAs, `al` - retrieve alphabet of automaton or grammar

arguments:
* `inputs` - names of files with structures to test

sample usage: `java -jar putflap.jar test -t ndet automaton_1.jff automaton_2.jff`

#### word
generation of valid words for given automatons

flags:
* `-m`, `--multiple` - number of words to generate. Default = 1
* `-j`, `--json` - write answer as json file

arguments:
* `automatonFile` - name of file with automaton

sample usage: `java -jar putflap.jar word automaton.jff`

#### convert
perform various conversion tasks on automaton and grammars

flags:
* `-t`, `--type` - type of conversion to perform: `dfa` - FSA to deterministic FSA, `mini` - FSA to minimal FSA, `gra` - automaton to grammar, `re` - FSA to regular expression, `pda` - grammar to PDA, `fsa` - grammar to FSA, `json` - automaton or grammar as `.jff` file to `.json` file. All generated structures are deterministic.
* `-j`, `--json` - write answer as json file

arguments:
* `inputs` - names of files with structures to convert

sample usage: `java -jar putflap.jar convert -t re -j fsa.jff`


### **smart** 
perform tasks from other sections with parameters written in `.json` file

sample usage: `java -jar putflap.jar smart random_fsa.json`

> *Smart* currently supports all _PUTflap_ functionality except conversions from grammar and testing grammars. 

#### configuration format
Parameters in smart configurations are equivalent to CLI parameters. General structure of `json` looks like this:
```json
{
  "instruction": "name of section to perform task from",
  "parameters": "dictionary of task parameters"
}
```  

Main feature of *smart* is that each input can be specified as array of automatons and *PUTflap* will perform requested task for all of them. 

In `parameters` longer names are used. Sample configurations are provided in `samples/smart`. 

## development
Some features worth of implementing:
- exceptions handling (error reports in JSON)
- possibility to specify path to automaton or folder with automatons to use in _smart_
- Turing Machine
- edition of automatons from _CLI_
- traffic statistics after running automaton
- random PDA
- nondeterministic radom automatons

## license
PUTflap is distributed free of charge based on JFLAP license and CC BY-NC-SA 4.0	 

### JFLAP 7.1 LICENSE

Susan H. Rodger
Computer Science Department
Duke University
July 27, 2018

Duke University students contributing to JFLAP source include: Thomas Finley,
Ryan Cavalcante, Stephen Reading, Bart Bressler, Jinghui Lim, Chris Morgan,
Kyung Min (Jason) Lee, Jonathan Su, Henry Qin and Jay Patel.

Copyright (c) 2002-2018.
All rights reserved.


I)  You are allowed distribute unmodified copies of JFLAP under the following two conditions:
    1) You must include a copy of this license text.
    2) You cannot charge a fee for any product that includes any part of JFLAP, in source or binary form.


II) You are allowed to distribute modified copies of JFLAP under the following conditions:
    1) You must include a copy of this license text.
    2) You cannot charge a fee for any product that includes any part of your modified JFLAP, in source or binary form.
    3) If you made the changes yourself, you must clearly describe how to contact you.
       When the maintainer asks you (in any way) for a copy of the modified JFLAP you distributed, you
       must make your changes, including source code, available to the maintainer without fee.  
       The maintainer reserves the right to include your changes in the official version of JFLAP. 
       The current maintainer is Susan Rodger . If this changes, it will be announced at www.jflap.org.
       

The name of the author may not be used to
endorse or promote products derived from this software without
specific prior written permission.

THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.

## credits
This is a project made by Jakub Riegel and supervised by dr Krzysztof Zwierzyński from [Poznan University of Technology](https://www4.put.poznan.pl/en) as a part of Formal Languages and Compilers course and will be further developed by future course participants.

The maintainer of the original JFLAP is Susan Rodger. More information can be found on [www.jflap.org](http://www.jflap.org/)

### contact
Jakub Riegel: jakub.riegel@student.put.poznan.pl

dr Krzysztof Zwierzyński: krzysztof.zwierzynski@put.poznan.pl

---
[<img src="http://iim.put.poznan.pl/Szata/PP.gif" width="100dp" />](https://www4.put.poznan.pl/en)
[<img src="http://www.cie.put.poznan.pl/images/nowelogo3eng.png" height="70dp"/>](http://www.cie.put.poznan.pl/index.php?lang=en)
