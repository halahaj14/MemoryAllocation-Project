/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamemoryallocation;

 
public class Process {
    private String name;
    private int size;

    public Process(String name, int size) {
        this.name = name;
        this.size = size*1024;//convert to Bytes
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size*1024;//convert to Bytes
    }
    
    
}
