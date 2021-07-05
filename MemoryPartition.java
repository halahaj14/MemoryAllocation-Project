/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamemoryallocation;
enum PartitonState{
    FREE,ALLOC
}
 
public class MemoryPartition {
    private PartitonState state;
    private int size;
    private int startingAddress;
    private int endingAddress;
    private Process process=null;

    public MemoryPartition(PartitonState status, int size, int startingAddress, int endingAddress, Process process) {
        this.state = status;
        this.size = size;
        this.startingAddress = startingAddress;
        this.endingAddress = endingAddress;
        this.process = process;
      
    }

    public PartitonState getState() {
        return state;
    }

    public void setState(PartitonState state) {
        this.state = state;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(int startingAddress) {
        this.startingAddress = startingAddress;
    }

    public int getEndingAddress() {
        return endingAddress;
    }

    public void setEndingAddress(int endingAddress) {
        this.endingAddress = endingAddress;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.state=PartitonState.ALLOC;
        this.process = process;
    }

    public int getInternalFragmentaion() {     
        return this.process!=null?this.size-this.process.getSize():-1;
    }
    
    public void releaseProcess()
    {
        this.state=PartitonState.FREE;
        this.process = null;
    }

    
    
}
