# OOP-02
Exercise in oop in java 02.

# Authors
[Talor Langnas](https://github.com/TalorLangnas)  
[Elroei Avraham](https://github.com/elroei1avraham).


# About the project - part 1
In the project we create num of text files and in the calculate the num of line in total of all the files together.
We use in 3 options to calculate the line:
1) Normal method without tread.
2) Method with tread.
3) Method with treadpool.


# Which function we have and what they have in it?
createTextFiles(int n, int seed, int bound):
the function get:
n: the number of files to create.
seed: the seed for the random number generator.
bound: the upper bound for the number of lines in each file.

the function take a randon number and create a file with the random number of file with the string "Hello World".

getNumOfLines(String[] fileNames):
the function get:
fileNames: Array of file names.

the function return the num of line in the each file without thread and threadpool.

getNumOfLinesThreads(String[] fileNames):
the function get:
fileNames: Array of file names.

the function return the num of line in the each file with thread and do the calculate in parallel.

getNumOfLinesThreadPool(String[] fileNames):
the function get:
fileNames: Array of file names.

the function return the num of line in the each file with threadpool and do the calculate in parallel and do reuse the threads and take less time in big number of files.

singleFileNumOfLines(String fileName):
the function get:
fileNames: Array of file names.

the function return the number of line in single file.

 
# What the difference in the time in the 3 functions?
we run the project in a difference count of files:

in 10 files:
in getNumOfLines() take: 12 Millis
in getNumOfLinesThreads() take: 15 Millis
in getNumOfLinesThreadPool() take: 18 Millis

in 100 files:
in getNumOfLines() take: 179 Millis
in getNumOfLinesThreads() take: 62 Millis
in getNumOfLinesThreadPool() take: 46 Millis


in 10000 files:
in getNumOfLines() take: 993 Millis
in getNumOfLinesThreads() take: 968 Millis
in getNumOfLinesThreadPool() take: 312 Millis

when we do this function in a small number of files(in our main, 10 files), the function getNumOfLines() was fastest because using in thread and threadpool using time to allocating memory for every thread.
the function getNumOfLinesThreadPool() take less time when the num of file is bigger.
this happened becuase of the function getNumOfLinesThreadPool() work with ThreadPool that it have a count of threads and the system reuse a number of thread many times instead of allocating memory for all the treads.
becuase all we write we see that the using in thread and threadpool id faster and as we increase the amount of files using in threadpool is the fastest of all the function.
