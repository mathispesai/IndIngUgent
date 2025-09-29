package be.ugent.tiwi.datastructures.lab6;

import java.io.IOException;

/**
 *
 * @author sleroux
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        
        String filename = "100000 Records.csv";

        long starttime = System.currentTimeMillis();
        Database db = new Database(filename);
        //Database db = new IndexedDatabase(filename);
        System.out.println("Building database took "+(System.currentTimeMillis() - starttime) + "ms");

        for (int id: new int[]{148230, 165393, 211373, 312371, 266352, 1}){
            runQuery(db, id);
        }
    }

    private static void runQuery(Database db, int id) throws IOException{
        long starttime = System.currentTimeMillis();
        Database.Record result = db.findById(id);
        System.out.print("Query took "+(System.currentTimeMillis() - starttime) + "ms\t");
        System.out.println(result);
    }
}
