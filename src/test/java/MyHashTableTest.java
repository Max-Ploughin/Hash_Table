import junit.framework.TestCase;
import org.junit.Test;

public class MyHashTableTest extends TestCase {

    @Test
    public void testInsertAndGetValue() {
        MyHashTable hashTable = new MyHashTable();
        hashTable.insertData("key1", "value1");
        hashTable.insertData("key2", "value2");

        assertEquals("value1", hashTable.getValue("key1"));
        assertEquals("value2", hashTable.getValue("key2"));
    }

    @Test
    public void testUpdateValue() {
        MyHashTable hashTable = new MyHashTable();
        hashTable.insertData("key1", "value1");
        hashTable.insertData("key1", "value2");

        assertEquals("value2", hashTable.getValue("key1"));
    }

    @Test
    public void testRemoveValue() {
        MyHashTable hashTable = new MyHashTable();
        hashTable.insertData("key1", "value1");
        hashTable.removeValue("key1");

        assertNull(hashTable.getValue("key1"));
    }

    @Test
    public void testExpandArray() {
        MyHashTable hashTable = new MyHashTable();
        // Insert more data than the initial bucket size to trigger array expansion.
        for (int i = 0; i < 50; i++) {
            hashTable.insertData("key" + i, "value" + i);
        }

        assertEquals("value5", hashTable.getValue("key5"));
        assertEquals("value49", hashTable.getValue("key49"));
    }

    @Test
    public void testCountZeroBuckets(){
        MyHashTable hashTable = new MyHashTable();
        for (int i = 0; i < 50; i++) {
            hashTable.insertData("key" + i, "value" + i);
        }
        for (int i = 0; i < 45; i++) {
            hashTable.removeValue("key" + i);
        }

        assertEquals(8, hashTable.getCountNull());
        assertEquals(12, hashTable.getDataList().length);

    }
}