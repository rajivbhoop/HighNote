# HighNote
You need to have maven and java installed. 
Please dowload the zip file from the link : https://github.com/lifelonglearner108/HighNote as HighNote.zip and unzip it for use.
or 
use the command "git clone https://github.com/lifelonglearner108/HighNote" if you have git installed.

Then run the mvn clean install. This will create the jar file HighNote-1.0-SNAPSHOT.jar as shown in Screenshot 1 in the mail. mvn clean install should also run the tests

Navigate to the target folder to the jar location and then run the command like below :

java -jar HighNote-1.0-SNAPSHOT.jar /Users/anupamojha/InterviewPrep/transactions.txt /Users/anupamojha/InterviewPrep/output.txt

The above command has location of the input file i.e. transactions.txt file and the file where the output is getting written i.e. output.txt. 

