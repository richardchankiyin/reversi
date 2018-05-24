# reversi
This is a java based reversi game

Prerequsites
=============
jdk 1.8<br/>
maven<br/>

Detail
=======
This is an application to play with Othello/Reversi.<br/><br/>
In order to build, please type:<br/>
cd Reversi<br/>
./build.sh<br/><br/>
In order to run batch mode, please type:<br/>
cd Reversi<br/>
./run_batch.sh "[your arg here]"<br/><br/>
In order to run interactive mode, please type:<br/>
cd Reversi<br/>
./run_interactive.sh<br/><br/>
For log checking, please check at Reversi/log/out.log

Release Note
=============
1.0
----
Initial version

1.1
----
1. update the log level to INFO for testing part to get rid of excessive log<br/>
2. Introduce Displacement interface to replace Operators functions. Displacement.next() is a function to accept current position (zero-based integer) and return the next position in zero-based integer. This can make the searching of same disk type item logic more generic by abstracting the position next logic<br/>
3. Some terms are not in standard java camel-casing. Fixed in this release<br/>
4. For batch mode, there will be a printout of chess board after each step<br/>
5. There is a new build.sh created to run test, install and package (creating a fat jar for running application)<br/>
6. run*sh commands are updated to use java command rather than mvn exec command as the latter in/out console seems problematic occasionally. It is tested in mac os and mvn version does not work.<br/> 

