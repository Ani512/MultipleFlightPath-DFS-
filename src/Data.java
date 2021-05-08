// Class data to store all the file data as objects of a structure.
public class Data {
    public String type;
    public String source;
    public String destination;
    public int cost;
    public int time;

    // Main constructor for reading data
    public Data(String source, String destination, int cost, int time) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.time = time;
    }

    // Constructor 2 created for reading input files
    public Data(String source, String destination, String type) {
        this.source = source;
        this.destination = destination;
        this.type = type;
    }
}

/*
16
Austin|Boston|565|100
Boston|Columbus|937|200
Columbus|Dallas|978|150
Boston|Dallas|364|35
Dallas|Fort-Worth|235|94
El-Paso|Boston|355|132
Dallas|El-Paso|654|53
Austin|Dallas|195|29
Austin|El-Paso|489|48
Fort-Worth|Houston|134|58
Houston|El-Paso|58|63
Houston|Austin|900|25
Georgetown|Austin|698|87
El-Paso|Georgetown|276|101
Georgetown|Indianapolis|343|187
Indianapolis|Houston|49|213
 */
