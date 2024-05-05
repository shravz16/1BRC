# 1BRC
1 billion row challenge
*Problem Statement:

The task is to develop a program that processes temperature measurements from various weather stations and calculates
* aggregate statistics for each station. The program should read temperature data from a file,
* compute the minimum, maximum, and average temperatures for each station, and output the results.

Requirements:

    Read temperature measurements from a text file named "measurements.txt".
    Each line in the file represents a temperature measurement and
    consists of two fields separated by a semicolon: the name of the weather station and the recorded temperature.
    Process the temperature data concurrently using parallel stream processing.
    Calculate aggregate statistics for each weather station:
        Minimum temperature recorded.
        Maximum temperature recorded.
        Average temperature.
        Total number of temperature measurements.
    Display the aggregate statistics for each weather station.
    Ensure thread safety during concurrent processing of temperature data.
    Round all temperature values to one decimal place for display purposes.

Constraints:

    The input file "measurements.txt" may contain a large number of temperature measurements from multiple weather stations.

*
* measurements.txt - https://1brcshravalogy.s3.us-east-2.amazonaws.com/measurements.txt
*
* */
