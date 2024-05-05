/*
 *  Copyright 2023 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package dev.demo;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
/*
*
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
*
*
* */
public class CalculateAverageFunction {

    private static final String FILE = "./measurements.txt";

    static record Stats (double min, double max, double mean, long count) {
        @Override
        public String toString() {
            return round(min) + "/" + round(mean) + "/" + round(max);
        }
        private double round(double value) {
            return Math.round(value * 10.0) / 10.0;
        }
    }

    static record MeasurementTuple (String station, double temp) {
        public MeasurementTuple (String[] values) {
            this(values[0], Double.parseDouble(values[1]));
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("===================== Starting to compute 500 Million lines of data =====================");
        long time=System.currentTimeMillis();

        //All the lines in the file to Stream of Strings
        Stream<String> lines = lines(get(FILE));

        //Used concurrent skiplist map to toore average mean min max and latest count
        ConcurrentMap<String, Stats> aggregatedStats = new ConcurrentSkipListMap<>();


        // functional programming to run the streams in parallel and compute mean min, max and count for the cities
        lines.forEach(s -> {

            MeasurementTuple tuple = new MeasurementTuple(s.split(";"));

            //compute functional programming is a method to compute a function for the key and return a value for the key and storing it
            aggregatedStats.compute(tuple.station(), (s1, stats) -> {

                if (stats == null) {
                    //new value
                    return new Stats(tuple.temp, tuple.temp, tuple.temp, 1L);
                }
                else {
                    //update value
                    long latestCount = stats.count + 1;
                    double min = Math.min(stats.min, tuple.temp);
                    double max = Math.max(stats.max, tuple.temp);
                    double mean = ((stats.mean * stats.count) + tuple.temp) / latestCount;
                    return new Stats(min, max, mean, latestCount);
                }
            });
        });

        System.out.println(aggregatedStats);

        System.out.println("time taken to compute 500 million lines in seconds = " + ((System.currentTimeMillis()-time)/1000));
        System.out.println("===================== End =====================");


    }
}



