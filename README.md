# MemoryAllocation-Project

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

