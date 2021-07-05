/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamemoryallocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

 
public class JavaMemoryAllocation {

    /**
     * @param args the command line arguments
     */
    private static String reportPath = "Report.txt";
    private static File reportFile;
    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        reportFile = new File(reportPath);
        reportFile.createNewFile();//create report file if not existed
        PrintWriter reportWriter;
        scanner = new Scanner(System.in);
        int allocationArraySize;
        int command;
        
        System.out.println("****Java Memory Allocation Managment*****");
        System.out.print("Enter number of partitions:");
        allocationArraySize = scanner.nextInt();
        //initialize allocation manager instance
        AllocationManager allocationManager = new AllocationManager(allocationArraySize);
        do {
            displayMenu();
            command = scanner.nextInt();
            switch (command) {
                case 1: {
                    System.out.println("Enter ProcessName ProcessSize Algorithm:");
                    System.out.println("F:First Fit\tB:Best Fit\tW:Worst Fit");

                    String processName = scanner.next();
                    int processSize = Integer.parseInt(scanner.next());
                    String algorithm = scanner.next().toUpperCase();
                    if (!algorithm.equals("F")
                            && !algorithm.equals("B") && !algorithm.equals("W")) {
                        System.out.println("Error:You should only use (F:First Fit B:Best Fit W:Worst Fit)");
                        break;
                    }
                    Process process = new Process(processName, processSize);
                    RequestResult result = allocationManager.requestProcess(process, algorithm);
                    if (result != RequestResult.SUCCESSFULLY_ALLOCATED) {
                        System.out.println("Error:Alocation Request Rejected - " + result);
                    } else {
                        System.out.println(processName + " Successfully Allocated !");
                    }
                    break;
                }
                case 2: {
                    System.out.print("Enter Process Name to release:");
                    String processName = scanner.next();
                    allocationManager.releaseProcess(processName);
                    break;
                }
                case 3: {

                    System.out.println(allocationManager.buildReport());
                    reportWriter = new PrintWriter(reportFile);
                    reportWriter.append(allocationManager.buildReport());
                    reportWriter.close();
                    break;
                }
                default:
                    break;
            }
        } while (command != 4);

    }

    private static void displayMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("|\t\tMain Menu\t\t|");
        System.out.println("-----------------------------------------");
        System.out.println("| 1-Request Process Allocation\t\t|");
        System.out.println("| 2-Release Process Allocation\t\t|");
        System.out.println("| 3-Status Report\t\t\t|");
        System.out.println("| 4-Exit\t\t\t\t|");
        System.out.println("-----------------------------------------");
        System.out.print("Your choice :");
    }
}
