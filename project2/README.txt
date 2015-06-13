UTEID: mjr2595; agc853;
FIRSTNAME: Michael; Alexander;
LASTNAME: Raymond; Collins;
CSACCOUNT: mjr2595; alexcoll;
EMAIL: michaeljohnraymond@gmail.com; agcollins@utexas.edu;

[Program 1]
[Description]
There are 6 java files: In SecureSubject.java, we store all necessary information about a subject (name, temp) and we also have a function for setting temp. In SecureObject.java, we have the same fields and methods. In InstructionObject.java, we used an enum to store all info pertaining to a line of instructions. There are three types - BAD, READ, WRITE. ReferenceMonitor.java has two parts. The first is the monitor that keeps track of all subjects and objects and keeps a mapping from subject/object to its corresponding level of security. The other part is the ObjectManager class which keeps an ArrayList of SecureObjects. SecurityLevel.java is a enum that holds a state of high or low and has the dominates method. SecureSystem.java is the main class which executes the commands. Not only does it hold the main method, but also the SecureSystem class. This class holds a ReferenceMonitor nested class. 
In order to run this file, you must first compile the source code (if you don't have the .class files) using 'javac *.java' while in the directory of the source files of the project. Once this has been done, you will now use 'java SecureSystem instructionList', where 'instructionList' is the name of a file containing the commands to be executed as specified in the assignment 1 page, in order to run the program. The program WILL NOT run without a file containing instructions. The output of the program will be printed to stdout (for the purposes of this assignment, a method called printstate() is called after every line of the instructionList file is read in order to display state information - this can be disabled).

[Finish]
We finished all of the assignment.

[Test Cases]
[Input of Test 1]
write Hal HObj 
read Hal 
write Lyle LObj 10
read Hal LObj 
write Lyle HObj 20
write Hal LObj 200
read Hal HObj
read Lyle LObj
read Lyle HObj
foo Lyle LObj
Hi Lyle,This is Hal
The missile launch code is 1234567

[Output of Test 1]
Reading from file: instructionList

Bad Instruction
The current state is: 
   LObj has value: 0
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

Bad Instruction
The current state is: 
   LObj has value: 0
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

lyle writes value 10 to lobj
The current state is: 
   LObj has value: 10
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

hal reads lobj
The current state is: 
   LObj has value: 10
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 10

lyle writes value 20 to hobj
The current state is: 
   LObj has value: 10
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 10

hal writes value 200 to lobj
The current state is: 
   LObj has value: 10
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 10

hal reads hobj
The current state is: 
   LObj has value: 10
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 20

lyle reads lobj
The current state is: 
   LObj has value: 10
   HObj has value: 20
   Lyle has recently read: 10
   Hal has recently read: 20

lyle reads hobj
The current state is: 
   LObj has value: 10
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 20

Bad Instruction
The current state is: 
   LObj has value: 10
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 20

Bad Instruction
The current state is: 
   LObj has value: 10
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 20

Bad Instruction
The current state is: 
   LObj has value: 10
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 20

[Input of test 2]
The missile launch code is 1234567
write Hal HObj 999
read Hal LObj
write Lyle LObj 84
read Hal LObj 
write Lyle HObj 20
writte Hal LObj 200
read Hal HObj
read Lyle LObj
read Lyle HObj
foo Lyle LObj

[Output of test 2]
Reading from file: instructionList

Bad Instruction
The current state is: 
   LObj has value: 0
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

hal writes value 999 to hobj
The current state is: 
   LObj has value: 0
   HObj has value: 999
   Lyle has recently read: 0
   Hal has recently read: 0

hal reads lobj
The current state is: 
   LObj has value: 0
   HObj has value: 999
   Lyle has recently read: 0
   Hal has recently read: 0

lyle writes value 84 to lobj
The current state is: 
   LObj has value: 84
   HObj has value: 999
   Lyle has recently read: 0
   Hal has recently read: 0

hal reads lobj
The current state is: 
   LObj has value: 84
   HObj has value: 999
   Lyle has recently read: 0
   Hal has recently read: 84

lyle writes value 20 to hobj
The current state is: 
   LObj has value: 84
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 84

Bad Instruction
The current state is: 
   LObj has value: 84
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 84

hal reads hobj
The current state is: 
   LObj has value: 84
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 20

lyle reads lobj
The current state is: 
   LObj has value: 84
   HObj has value: 20
   Lyle has recently read: 84
   Hal has recently read: 20

lyle reads hobj
The current state is: 
   LObj has value: 84
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 20

Bad Instruction
The current state is: 
   LObj has value: 84
   HObj has value: 20
   Lyle has recently read: 0
   Hal has recently read: 20

[Input of test 3]
write hal hobj 9
read hal lobj
write lyle lobj 5
read hal lobj

[Output of test 3]
Reading from file: instructionList

hal writes value 9 to hobj
The current state is: 
   LObj has value: 0
   HObj has value: 9
   Lyle has recently read: 0
   Hal has recently read: 0

hal reads lobj
The current state is: 
   LObj has value: 0
   HObj has value: 9
   Lyle has recently read: 0
   Hal has recently read: 0

lyle writes value 5 to lobj
The current state is: 
   LObj has value: 5
   HObj has value: 9
   Lyle has recently read: 0
   Hal has recently read: 0

hal reads lobj
The current state is: 
   LObj has value: 5
   HObj has value: 9
   Lyle has recently read: 0
   Hal has recently read: 0

[Input of test 4]
The missile launch code is 1234567
We are all going to die!
This is a bad instruction
lobj hal read

[Output of test 4]
Reading from file: instructionList

Bad Instruction
The current state is: 
   LObj has value: 0
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

Bad Instruction
The current state is: 
   LObj has value: 0
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

Bad Instruction
The current state is: 
   LObj has value: 0
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0

Bad Instruction
The current state is: 
   LObj has value: 0
   HObj has value: 0
   Lyle has recently read: 0
   Hal has recently read: 0
