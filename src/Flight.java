/*
 * CS 3345.501 - Anirudh Umarji | Flight Project
 * Info -
 * 1. Read data from a file
 * 2. Stored the data as objects of Class Data
 * 3. Converted each City name to a corresponding number for storing it in hashmaps
 * 4. Sent the hashed data to the DFS iterative backtracking algorithm
 * 5. It returns the flight path in the form of a ArrayList which is used reverse hashing to convert to strings
 * 6. Used a double for loop to find the corresponding objects from the Data Class and used it for time and money calculations
 * 7. Displaying all the available paths along with the total time taken at that path and the cost to fly that path
 * 8. Code works for both DIRECTED and UNDIRECTED graphs
 * DISCLAIMER - Not able to find the smallest three paths. My code displays ALL THE PATHS
 * EXTRA CREDIT - My code has functionality to take flight data and input file names from the command line
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.ArrayList;

public class Flight {

    public static int hashSize = 0;
    public static HashMap<Integer, String> map = new HashMap<>();
    static Data[] arrayofObj;
    static String CostVSTime;

    static String inputData;
    static String flightData;
    static String[] argsClone;

    // Assigning the output file globally because different functions write their data to the output file
    static FileWriter write;
    static {
        try {
            write = new FileWriter("output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int path;    // To keep track of number of paths in a particular flight route
    public static void main(String[] args) throws IOException {
        argsClone = args.clone();
        checkCommandLine(argsClone);    // Reading arguments from the command line if any entered
        File file = new File(flightData);
        Scanner sc = new Scanner(file);
        int totalPaths = Integer.parseInt(sc.nextLine());
        arrayofObj = new Data[totalPaths];
        Connect node = new Connect(100);

        int pos = 0;
        while (sc.hasNextLine()) {
            String tempStr = sc.nextLine();
            String[] data = tempStr.split("\\|");
            arrayofObj[pos] = new Data(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
            int src = addToHashMap(data[0]);
            int dest = addToHashMap(data[1]);
            node.addEdge(src, dest);
            pos++;
        }

        File inputFile = new File(inputData);
        Scanner inpSc = new Scanner(inputFile);
        inpSc.nextLine();

        int rpos=1;
        while (inpSc.hasNextLine()) {
            String readData = inpSc.nextLine();
            String[] reader = readData.split("\\|");
            int srcInp = addToHashMap(reader[0]);
            int destInp = addToHashMap(reader[1]);
            CostVSTime = reader[2];
            if (CostVSTime.equals("T")) {
                System.out.println("Flight " + rpos + ": " + reader[0] + ", " + reader[1] + " (Time)");
                write.write("Flight " + rpos + ": " + reader[0] + ", " + reader[1] + " (Time)\n");
            } else if(CostVSTime.equals("C")) {
                System.out.println("Flight " + rpos + ": " + reader[0] + ", " + reader[1] + " (Cost)");
                write.write("Flight " + rpos + ": " + reader[0] + ", " + reader[1] + " (Cost)\n");
            }

            path=1;
            node.paths(srcInp, destInp);
            System.out.print("\n");
            write.write("\n");
            rpos+=1;
        }
        write.close();

        // Trying to Find the Three lowest Time taken

//        if (CostVSTime.equals("T")) {
//            Arrays.sort(timePerPath);
//            for (Integer x : timePerPath) {
//                if (x==0) {
//                }
//            }
//        } else if(CostVSTime.equals("C")) {
//            Arrays.sort(costPerPath);
//        }
    }

    public static void checkCommandLine(String[] args) {
        flightData = "flightdata.txt";
        inputData = "inputdata.txt";
//        outputFile = "output.txt";

        if (args.length > 0) {
            flightData = args[0];
            inputData = args[1];
//            outputFile = args[2];
        }
    }

    static int ret;

    // Adding each city name to the hash map so that each city can have a unique Key value pair which
    // will be used by the DFS iterative backtracking algorithm
    public static int addToHashMap(String word) {
        boolean exists = map.containsValue(word);   // if the value already exists, no need to add it to the hash map
        //System.out.println();
        if (!exists) {
            map.put(hashSize, word);
            ret = hashSize;
            hashSize += 1;
        } else {
            for (Entry<Integer, String> entry : map.entrySet()) {   // Traversing the hash map to add the value
                if (entry.getValue().equals(word)) {
                    ret = entry.getKey();
                }
            }
        }
        return ret;
    }

    // This is the reverse hashing function. After the Algorithm returns the shortest paths, it is in the form of number.
    // This function reverses the hashing and gives me the actual Strings that were passed
    static ArrayList<String> checker = new ArrayList<>();
    public static void reverseHashing(List<Integer> localPathList) throws IOException {
        int count=0;
        for (Integer integer : localPathList) {
            // Function also saves all the paths separately in an array so that it can be used for calculation later
            for (Entry<Integer, String> entry : map.entrySet()) {
                if (entry.getKey().equals(integer)) {
                    checker.add(count, entry.getValue());
                    count++;
                }
            }
        }
        costAndTimeCalc(checker);   // Calls the cost and time calculator function
        checker.clear();
        System.out.print("\n");
    }

    // Allocating Global variables using static keyword so that the values can be used in different functions also
//    static ArrayList<Integer> costPerPath = new ArrayList<>();
//    static ArrayList<Integer> timePerPath = new ArrayList<>();
    static int[] costPerPath = new int[1000];
    static int[] timePerPath = new int[1000];
    static int flag2 = 0;

    // Function to calculate the cost and time taken by particular paths
    public static void costAndTimeCalc(ArrayList<String> list) throws IOException {
        int flag;
        for (int p=0, j=1 ; p< list.size() && j<list.size(); j++, p++) {
            for (flag = 0; flag<arrayofObj.length ; flag++ ) {
                if ((list.get(p).equals(arrayofObj[flag].source) && list.get(j).equals(arrayofObj[flag].destination)) || (list.get(p).equals(arrayofObj[flag].destination) && list.get(j).equals(arrayofObj[flag].source))) {
                    costPerPath[flag2] += arrayofObj[flag].cost;
                    timePerPath[flag2] += arrayofObj[flag].time;
                    break;
                }
            }
        }

        // Using decimal format to print the money in correct format
        System.out.print("Path "+path+": ");
        write.write("Path: ");
        DecimalFormat df = new DecimalFormat("#.00");
        String last = list.get(list.size() - 1);
        for (String ele : list) {
            if (ele.equals(last)) {
                System.out.print(ele + ". ");
                write.write(ele + ". ");
            } else {
                System.out.print(ele + " -> ");
                write.write(ele + " -> ");
            }
        }
        System.out.print("Time: " + timePerPath[flag2] + " | Cost: " + df.format(costPerPath[flag2]));
        write.write("Time: " + timePerPath[flag2] + " | Cost: " + df.format(costPerPath[flag2]) + "\n");
        flag2+=1;
        path+=1;
    }

}