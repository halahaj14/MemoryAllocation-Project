# allocationMemory-Project

import java.io.PrintWriter;
import java.util.Scanner;

enum AllocationAlgorithms {
    FIRST_FIT, BEST_FIT, WORST_FIT
};

enum RequestResult {
    SUCCESSFULLY_ALLOCATED,
    NO_FREE_PARTITON_AVAILABLE,
    INSUFFICIENT_ALLOCATION_SIZE
}

 
public class AllocationManager {

    private MemoryPartition[] allocationArray;
    private int size;
    private int maxPartitionSize = 0;

    AllocationManager(int size) {
        this.size = size;
        initializeAllocationArray();
    }

    ;
    
    private void initializeAllocationArray() {
        this.allocationArray = new MemoryPartition[size];
        int startingAddress = 0;
        for (int i = 0; i < this.size; i++) {
            System.out.print("Enter the size of Partition " + (i + 1) + " in KB:");
            Scanner scanner = new Scanner(System.in);
            int partitionSize = scanner.nextInt() * 1024;
            if (partitionSize > this.maxPartitionSize) {
                this.maxPartitionSize = partitionSize;
            }
            this.allocationArray[i] = new MemoryPartition(PartitonState.FREE, partitionSize, startingAddress, startingAddress + partitionSize-1, null);
            startingAddress += partitionSize+1;
        }
    }

    public RequestResult requestProcess(Process process, String algorithm) {
        algorithm = algorithm.toUpperCase();
        boolean foundFreePartirion = false;
        switch (algorithm) {
            //FIRST_FIT
            case "F": {
                for (int i = 0; i < this.size; i++) {
                    if (this.allocationArray[i].getState() == PartitonState.FREE) {
                        foundFreePartirion = true;
                        if (this.allocationArray[i].getSize() >= process.getSize()) {
                            this.allocationArray[i].setProcess(process);
                            return RequestResult.SUCCESSFULLY_ALLOCATED;
                        }
                    }

                }

                break;
            }
            //BEST_FIT
            case "B": {
                int minHoleSize = this.maxPartitionSize, minHoleSizeIndex = -1;
                for (int i = 0; i < this.size; i++) {
                    if (this.allocationArray[i].getState() == PartitonState.FREE) {
                        foundFreePartirion = true;
                        if (this.allocationArray[i].getSize() >= process.getSize()
                                && this.allocationArray[i].getSize() - process.getSize() < minHoleSize) {
                            minHoleSize = this.allocationArray[i].getSize() - process.getSize();
                            minHoleSizeIndex = i;
                        }
                    }
                }
                if (minHoleSizeIndex > -1) {
                    this.allocationArray[minHoleSizeIndex].setProcess(process);
                    return RequestResult.SUCCESSFULLY_ALLOCATED;
                }
                break;
            }
            //WORST_FIT
            case "W": {
                int maxHoleSize = 0, maxHoleSizeIndex = -1;
                for (int i = 0; i < this.size; i++) {
                    if (this.allocationArray[i].getState() == PartitonState.FREE) {
                        foundFreePartirion = true;
                        if (this.allocationArray[i].getSize() >= process.getSize()
                                && this.allocationArray[i].getSize() - process.getSize() > maxHoleSize) {
                            maxHoleSize = this.allocationArray[i].getSize() - process.getSize();
                            maxHoleSizeIndex = i;
                        }
                    }
                }
                if (maxHoleSizeIndex > -1) {
                    this.allocationArray[maxHoleSizeIndex].setProcess(process);
                    return RequestResult.SUCCESSFULLY_ALLOCATED;
                }

                break;
            }
        }
        return foundFreePartirion ? RequestResult.INSUFFICIENT_ALLOCATION_SIZE : RequestResult.NO_FREE_PARTITON_AVAILABLE;
    }

    public boolean releaseProcess(String name) {
        
        for (int i = 0; i < this.size; i++) {

            MemoryPartition mp = this.allocationArray[i];
            if (mp.getState() == PartitonState.FREE) {
                continue;
            }
            if (mp.getProcess().getName().equals(name)) {
                mp.releaseProcess();
                return true;
            }
        }
        return false;
    }

    public String buildReport() {
        StringBuilder report = new StringBuilder();
        report.append("\nPartition No.\tStart Add.\tEnd Add.\tStatus\t\tSize\t\tProcess\t\tInternal frag. size\n");
        for (int i = 0; i < this.size; i++) {
            MemoryPartition mp = this.allocationArray[i];
            report.append(i + 1 + "\t\t");
            report.append(mp.getStartingAddress() + "\t\t" + mp.getEndingAddress() + "\t\t");
            report.append(mp.getState() + "\t\t");
            report.append(mp.getSize() / 1024 + " KB\t\t");
            report.append((mp.getProcess() == null ? null : mp.getProcess().getName()) + "\t\t\t");
            report.append(mp.getInternalFragmentaion() > -1 ? mp.getInternalFragmentaion() / 1024 + " KB" : null + "\t\t");
            report.append("\n");
        }
        report.append("\t\t------------------------------------------------------------------\t\t");
        return report.toString();
    }

}
