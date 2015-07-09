UTEID: mmd2468; mjr2595;
FIRSTNAME: Mattthew; Michael;
LASTNAME: Davidson; Raymond;
CSACCOUNT: mattd; mjr2595;
EMAIL: matthew.davidson@utexas.edu; michaeljohnraymond@gmail.com;

[Program 5]
[Description]
There are 3 java files: PasswordCrack.java, User.java, and jcrypt.java. We decided to create a User object that holds all necessary information about a user such as username, first/last name, hash, and seed. The constructor for this object takes in a line of text from the password file and uses the split function to extract the data. We felt it was easiest to keep the track of all dictionary words (and subsequent mangled versions) in an ArrayList as well as keep an ArrayList of Users for iteration. We split up the string manipulations in three levels: 1 being the most trivial, and so forth. To run our program, you need to use "java PasswordCrack dictionary passwords”.

[Finish]
We finished all of this assignment.

[Test Cases]
[Input of test 1]
https://www.cs.utexas.edu/~byoung/cs361/passwd1

[Output of test 1]
We can crack 18 cases in 3.77 minutes.
List of cracked: 
Username: michael Password: michael
   Cracked in 36.00ms
Username: abigail Password: liagiba
   Cracked in 66.00ms
Username: maria Password: Salizar
   Cracked in 108.00ms
Username: benjamin Password: abort6
   Cracked in 196.00ms
Username: samantha Password: amazing
   Cracked in 395.00ms
Username: tyler Password: eeffoc
   Cracked in 1168.00ms
Username: morgan Password: rdoctor
   Cracked in 1622.00ms
Username: jennifer Password: doorrood
   Cracked in 1642.00ms
Username: connor Password: enoggone
   Cracked in 2064.00ms
Username: evan Password: Impact
   Cracked in 2243.00ms
Username: nicole Password: keyskeys
   Cracked in 2408.00ms
Username: rachel Password: obliqu3
   Cracked in 2720.00ms
Username: jack Password: sATCHEL
   Cracked in 3143.00ms
Username: alexander Password: squadro
   Cracked in 3302.00ms
Username: victor Password: THIRTY
   Cracked in 3414.00ms
Username: james Password: icious
   Cracked in 3497.00ms
Username: caleb Password: teserP
   Cracked in 208635.00ms
Username: nathan Password: sHREWDq
   Cracked in 226633.00ms

We can not crack 2 cases.
List of uncracked usernames and passwords:
Username: dustin Password: litpeR
Username: paige Password: hI6d$pC2

[Input of test 2]
https://www.cs.utexas.edu/~byoung/cs361/passwd2

[Output of test 2]
We can crack 15 cases in 11.26 minutes.
List of cracked: 
Username: jennifer Password: ElmerJ
   Cracked in 143.00ms
Username: evan Password: ^bribed
   Cracked in 1053.00ms
Username: morgan Password: dIAMETER
   Cracked in 1864.00ms
Username: james Password: enchant$
   Cracked in 2142.00ms
Username: tyler Password: eltneg
   Cracked in 2532.00ms
Username: nicole Password: INDIGNIT
   Cracked in 2888.00ms
Username: abigail Password: Saxon
   Cracked in 4400.00ms
Username: michael Password: tremors
   Cracked in 5023.00ms
Username: jack Password: ellows
   Cracked in 5302.00ms
Username: benjamin Password: soozzoos
   Cracked in 5312.00ms
Username: caleb Password: zoossooz
   Cracked in 5312.00ms
Username: connor Password: nosral
   Cracked in 7115.00ms
Username: alexander Password: Lacque
   Cracked in 433312.00ms
Username: dustin Password: Swine3
   Cracked in 648336.00ms
Username: nathan Password: uPLIFTr
   Cracked in 676172.00ms

We can not crack 5 cases.
List of uncracked usernames:
Username: samantha
Username: victor
Username: rachel
Username: maria
Username: paige
