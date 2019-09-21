# TRANSACTION PROCESSING

#Development Notes
- The Program has been written using Java 8.
- CSV parsing has been done using opencsv & google-guava api's.
- I have added appropriate junit test cases to cover edge cases and code coverage. 
- I have kept transaction data into file `transactions.csv` present under `src/main/resources` folder.
- I have also kept input data into file `input.csv` under project's root folder.

# Assumptions Made
- `input.csv` file will be provided and will contain data in the expected format

# Building and running Program

The program can be invoked by running the below mentioned command and using a csv file containing account, to & from details.
Details of the input can be found in `PROBLEM.md` file.

# Build
To build the project, run command `mvn clean package`.
The built JAR can be found in `./target/` directory.

# Test

Building with `mvn clean package` runs the unit tests.

#Run
Run command `mvn exec:java` from the path where `pom.xml` is present.
 - Program can also be run by using command `java -jar bank-transaction-1.0.jar`

Enter any file name described above or create a new file and account details in it.
 - If you are creating your own file, please specify absolute path of the file.

Output will be written to console by Logger.

