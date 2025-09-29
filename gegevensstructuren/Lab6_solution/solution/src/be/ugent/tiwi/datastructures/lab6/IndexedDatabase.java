package be.ugent.tiwi.datastructures.lab6;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author sleroux
 */
public class IndexedDatabase extends Database{
    private BTree index;
    
    public IndexedDatabase(String filename) throws FileNotFoundException, IOException {
        super(filename);
        index = new BTree(1001);
        
        long location = 0;
        file.seek(location);
        String line = file.readLine();
        
        while (line != null) {
            Record r = getRecordFromLine(line);
            index.add(r.id, location);          
            //System.out.println(""+location+" "+r);
            location = file.getFilePointer();
            line = file.readLine();
            
        }
    }

    @Override
    public Record findById(int value) throws IOException {
        long offset = index.get(value);
        if (offset < 0) {
            return null;
        }
        file.seek(offset);
        String line = file.readLine();
        Record r = getRecordFromLine(line);
        return r;
    }
    
    
    
}
