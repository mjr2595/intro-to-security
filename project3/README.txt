UTEID: mjr2595;
FIRSTNAME: Michael;
LASTNAME: Raymond;
CSACCOUNT: mjr2595;
EMAIL: michaeljohnraymond@gmail.com;

[Program 3]
[Description]
There are 2 java files: Encoder.java is the home to the Encoder class as well as the main method. The Encoder class holds all necessary data and methods to run everything, leaving the main method to just get the command line arguments and initialize a new Encoder object. The getFrequencies method reads through the provided file and fills up a map with the character and corresponding count. It then goes through the keySet of that map and fills up another map, this time with the probabilities (or frequencies) as well as an array with those same frequencies; this is for the random generator method later on. For my random char generator method I used Math.random() to get a random number between 0-1 then traversed through the frequency array and compared the random number with the frequencies to choose which char would be selected. The Huffman.java class was taken both from my Data Structures Huffman code as well as the Princeton Huffman code found at http://algs4.cs.princeton.edu/55compression/Huffman.java.html. To compile our program, you need to use "javac *.java". To run our program, you need to use "java Encoder frequenciesFile k”, where k is the number of letters in the output file to be generated.

[Finish]
I finished the all of this assignment. There are some bugs, though. The encodings sometimes come out wrong. I believe this is because of my Huffman implementation. The entropy, frequencies, and probabilities seems to be all correct. I tried to debug this but could not figure it out (why this project is late).

[Test Cases]
[Input of test 1]
[command line]java Encoder frequenciesFile 4

please copy your input file(frequenciesFile) here
4
2
3
1

[Output of test 1]
please copy your output here.
	+---------+
	| RESULTS |
	+---------+

Character: D, Encoding: 110, Probability: 1/10
Character: A, Encoding: 0, Probability: 4/10
Character: B, Encoding: 10, Probability: 2/10
Character: C, Encoding: 111, Probability: 3/10
________________________________________________

Entropy = 1.8464393446710154

Encryption 1 has 3.0 bits per symbol
The percent difference from entropy is 16.24748740682038
   
[Input of test 2]
[command line]java Encoder frequenciesFile 4

[Output of test 2]
	+---------+
	| RESULTS |
	+---------+

Character: D, Encoding: 110, Probability: 1/10
Character: A, Encoding: 0, Probability: 4/10
Character: B, Encoding: 10, Probability: 2/10
Character: C, Encoding: 111, Probability: 3/10
________________________________________________

Entropy = 1.8464393446710154

Encryption 1 has 2.5 bits per symbol
The percent difference from entropy is 13.539572839016984

[Input of test 3]
[command line]java Encoder frequenciesFile2 15

[Output of test 3]
	+---------+
	| RESULTS |
	+---------+

Character: D, Encoding: 111111110, Probability: 1/44
Character: E, Encoding: 0, Probability: 1/44
Character: F, Encoding: 10, Probability: 16/44
Character: G, Encoding: 110, Probability: 6/44
Character: A, Encoding: 1110, Probability: 5/44
Character: B, Encoding: 11110, Probability: 1/44
Character: C, Encoding: 111110, Probability: 1/44
Character: H, Encoding: 1111110, Probability: 9/44
Character: I, Encoding: 11111110, Probability: 1/44
Character: J, Encoding: 111111111, Probability: 3/44
________________________________________________

Entropy = 2.6320764369575698

Encryption 1 has 6.8 bits per symbol
The percent difference from entropy is 25.835115973532112

[Input of test 4]
[command line]java Encoder frequenciesFile3 5

[Output of test 4]
	+---------+
	| RESULTS |
	+---------+

Character: D, Encoding: 1111111111111111111110, Probability: 1/23
Character: E, Encoding: 0, Probability: 1/23
Character: F, Encoding: 10, Probability: 1/23
Character: G, Encoding: 110, Probability: 1/23
Character: A, Encoding: 1110, Probability: 1/23
Character: B, Encoding: 11110, Probability: 1/23
Character: C, Encoding: 111110, Probability: 1/23
Character: L, Encoding: 1111110, Probability: 1/23
Character: M, Encoding: 11111110, Probability: 1/23
Character: N, Encoding: 111111110, Probability: 1/23
Character: O, Encoding: 1111111110, Probability: 1/23
Character: H, Encoding: 11111111110, Probability: 1/23
Character: I, Encoding: 111111111110, Probability: 1/23
Character: J, Encoding: 1111111111110, Probability: 1/23
Character: K, Encoding: 11111111111110, Probability: 1/23
Character: U, Encoding: 111111111111110, Probability: 1/23
Character: T, Encoding: 1111111111111110, Probability: 1/23
Character: W, Encoding: 11111111111111110, Probability: 1/23
Character: V, Encoding: 111111111111111110, Probability: 1/23
Character: Q, Encoding: 1111111111111111110, Probability: 1/23
Character: P, Encoding: 11111111111111111110, Probability: 1/23
Character: S, Encoding: 111111111111111111110, Probability: 1/23
Character: R, Encoding: 1111111111111111111111, Probability: 1/23
________________________________________________

Entropy = 4.523561956057013

Encryption 1 has 13.4 bits per symbol
The percent difference from entropy is 29.622673747305505