UTEID: mjr2595; agc853;
FIRSTNAME: Michael; Alexander;
LASTNAME: Raymond; Collins;
CSACCOUNT: mjr2595; alexcoll;
EMAIL: michaeljohnraymond@gmail.com; agcollins@utexas.edu;

[Program 2]
[Description]
This project heavily builds off the previous project. SecureSystem.java was  changed to CovertChannel.java and some changes were made. These include printing to log in verbose mode as well as a changing the functionality of the executeFileCommands function. The function now reads the input text file each line at a time as a byte array. An important thing to check for is is newline characters, especially the lines just containing a newline character. ReferenceMonitor also has a few new methods: executeCreate and executeDestroy which call ObjectManager's create and destroy methods, respectively. To complie our program, you need to use "javac *.java". To run our program, you need to use "java CovertChannel inputFileName" or "java CovertChannel v inputFileName" for verbose mode.

[Machine Information]
I tested the code on a 2013 Macbook Pro clocked at 2.8 GHz. 

[Source Description]
All test file (except test.txt) were downloaded from https://www.gutenberg.org. Note: project gutenberg includes headeds and footers in their text files not in the original volumes.

[Finish]
We finished all of this assignment.

[Results Summary]
[No.]	[DocumentName] 		  [Size] 	 	  [Bandwidth]
1	Pride and Prejudice	  717,575 bytes	          258.5157 bits/ms
2	Metamorphosis		  141,419 bytes	          239.6424 bits/ms
3	Test			  45 bytes	          3.7113 bits/ms
4	Moby Dick		  1,235,164 bytes	  252.3446 bits/ms
