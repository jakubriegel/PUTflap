# PUTflap
PUTflap is an extension of JFLAP, which makes JFLAP features available from the command line and can report results as JSON

## features
The aim of this project is to support full JFLAP functionality from command line. Currently only some part of it is available.

Every operation provides its results as JSON file. Optionally if the result can be written as JFLAP file it can output such file.

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

#### Mealy and Moore Machine
- generation of random, deterministic machine
- generation of random valid word
- run machine for given input
- test for nondeterminism
- test for lambda transitions
- retrieve input and output alphabet

#### Pushdown Automaton
- generation of random, deterministic PDA
- generation of random valid word
- run PDA for given input
- test for nondeterminism
- test for lambda transitions
- retrieve alphabet
- conversion to grammar

#### grammar
- generation of random regular grammar
- retrieve alphabet
- conversion of regular grammar to FSA

## usage
Gradle tasks implemented in project generate three equally capable version of PUTflap: **jar**, **Linux zip** and **Windows exe**.

### build and run
#### jar
**build:** `./gradlew clean build shadowJar` build result is saved in `build/libs`

 **run:** `java -jar putflap.jar ARGS`

#### Linux 
**build:** `./gradlew clean build shadowJar` build result is saved in `build/distributions`

**run:** `./putflap ARGS`

#### Windows
**build:** `gradlew.exe clean build shadowJar createExe` build result is saved in `build/exe`

**run:** `putflap.exe ARGS`

### CLI
tba
 
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
This is a project made by Jakub Riegel and supervised by dr Krzysztof Zwierzyński from [Poznan University of Technology](https://www4.put.poznan.pl/en) .

---
[<img src="https://yt3.ggpht.com/a-/AJLlDp0OnTj3ja34dx-_Z0-aAV9prQz2qJ1wxEKMEg=s900-mo-c-c0xffffffff-rj-k-no" width="100dp" />](https://www4.put.poznan.pl/en)
[<img src="http://www.cie.put.poznan.pl/images/nowelogo3eng.png" height="70dp"/>](http://www.cie.put.poznan.pl/index.php?lang=en)