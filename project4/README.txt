UTEID: mmd2468; mjr2595;
FIRSTNAME: Mattthew; Michael;
LASTNAME: Davidson; Raymond;
CSACCOUNT: mattd; mjr2595;
EMAIL: matthew.davidson@utexas.edu; michaeljohnraymond@gmail.com;

[Program 4]
[Description]
There are 4 java files: AES, AESConstants, Encryptor, and Decryptor. AES is home to main. The only thing that it does is take in the arguments from the command line and pass them in to either an Encryptor or Decryptor class. It is in these respective class where the bulk of the work is done. Each constructor acts as a main function, providing the overall order of the Encryption or Decryption process. We decided to keep all the constants in one file (AESConstants) as an Interface in order to rid the other files of clutter. We tried to no avail to get our mixColumns code to work properly so we used Dr. Young's code for both mixColumns and invMixColumns. To compile our program, you need to use "javac *.java". To run our program, you need to use "java AES e keyFile inputFile" to encrypt a file and "java AES d keyFile inputFile.enc" to decrypt that same file.

[Finish]
We finished all of this assignment.

[Test Cases]
[Input of test 1]
[command line]
java AES e key plaintext
java AES d key plaintext.enc

plaintext

[Output of test 1]
plaintext.enc
plaintext.enc.dec
   
[Input of test 2]
[command line]
You need to write down command line.

You need to write down filename.

[Output of test 2]

You need to write down filename.

[Input of test 3]
[command line]

[Output of test 3]

[Input of test 4]
[command line]

[Output of test 4]