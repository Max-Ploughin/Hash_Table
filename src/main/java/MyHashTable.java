import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable {

    private LinkedList<DataEntry>[] dataList;
    private static final int ARRAY_SIZE = 16;
    private static final int BUCKET_SIZE = 10;
    //List for storing index values with the purpose of tracking their counts to reduce collisions.
    private ArrayList<Integer> indexList;
    //Counter for controlling the size of buckets.
    private int count;
    private int zeroBuckets = 0;

    //Constructor to initialize each element of the array as a new LinkedList.
    public MyHashTable() {
        this.count = 0;
        this.indexList = new ArrayList<>();
        this.dataList = new LinkedList[ARRAY_SIZE];
        for (int i = 0; i < dataList.length; i++) {
            dataList[i] = new LinkedList<>();
        }
    }

    // Custom hash function.
    private int customHashCode(String key) {
        int hash = 0; //Initial value for the hash value.
        int primeValue = 31; //A prime number for multiplication, based on standard practices.
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * primeValue) + key.charAt(i);
        }
        return hash;
    }

    // Obtaining an index based on the hash code and array size.
    private int getIndex(String key) {
        int hash = customHashCode(key);
        int index = hash % dataList.length;

        return index;
    }

    //Method for adding data.
    public void insertData(String key, String value) {

        int index = getIndex(key);
        //Collision check: how many times a particular index is encountered determines the size of the bucket.
        if (indexList.contains(index)) {
            count++;
        }
        indexList.add(index);

        //Check if the bucket size has not been exceeded.
        if (!checkCount()) {
            LinkedList<DataEntry> bucket = dataList[index];

            //Check if the key already exists.
            for (DataEntry dataEntry : bucket) {
                if (dataEntry.key.equals(key)) {
                    dataEntry.value = value;
                    return;
                }
            }
            //If the key does not exist, then add the data.
            bucket.add(new DataEntry(key, value));
        } else {
            //If the bucket size is exceeded, it is necessary to increase the array to reduce collisions.
            expandArray();
        }


    }

    // Method for removing values.
    public void removeValue(String key) {
        int index = getIndex(key);
        LinkedList<DataEntry> bucket = dataList[index];

        // Using an iterator to safely remove elements while iterating.
        Iterator<DataEntry> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            DataEntry dataEntry = iterator.next();
            if (dataEntry.key.equals(key)) {
                iterator.remove();
                //Collision check: how many times a particular index is encountered determines the size of the bucket.
                if (indexList.contains(index)) {
                    int indexToRemove = indexList.indexOf(index);
                    indexList.remove(indexToRemove);
                    count--;
                }
                zeroBuckets = 0;
                countZeroBuckets();
                if (((double) zeroBuckets / dataList.length) >= 0.7 && dataList.length > 16){
                    reduceArray();
                }
                return;
            }
        }
    }

    //Method for getting data.
    public String getValue(String key) {
        int index = getIndex(key);
        LinkedList<DataEntry> bucket = dataList[index];

        for (DataEntry dataEntry : bucket) {
            if (dataEntry.key.equals(key)) {
                return dataEntry.value;
            }
        }
        return null;
    }

    //Method for checking the size of the bucket.
    private boolean checkCount() {
        if (count > BUCKET_SIZE) {
            return true;
        }
        return false;
    }

    //Method for increasing the array.
    private void expandArray() {
        //We create a temporary list to store the existing data in it.
        LinkedList<DataEntry>[] temporaryList = dataList;
        //We reset the counter because we are increasing the size of the array, and we will be obtaining new indices.
        count = 0;
        //We clear the list of indices.
        indexList.clear();
        //We create an array with a new dimension (all arrays will be increased by 16).
        dataList = new LinkedList[ARRAY_SIZE + temporaryList.length];
        for (int i = 0; i < dataList.length; i++) {
            dataList[i] = new LinkedList<>();
        }

        //We place the data into the new list.
        for (int i = 0; i < temporaryList.length; i++) {
            LinkedList<DataEntry> dataEntries = temporaryList[i];
            for (DataEntry dataEntry : dataEntries) {
                insertData(dataEntry.key, dataEntry.value);
            }
        }
    }

    // The method for counting buckets in an array with a size of 0 (i.e., without values).
    private void countZeroBuckets() {
        for (LinkedList<DataEntry> bucket : dataList) {
            if (bucket.size() == 0) {
                zeroBuckets++;
            }
        }
    }

    //
    private void reduceArray() {
        // Calculate the new size of the array after reducing it by 30%.
        int newSize = (int) (dataList.length * 0.7);
        // Create a temporary list to store the existing data.
        LinkedList<DataEntry>[] temporaryList = dataList;
        // Reset the counter because we are reducing the size of the array, and we will be obtaining new indices.
        count = 0;
        // Clear the list of indices.
        indexList.clear();
        // Create a new array with the reduced size.
        dataList = new LinkedList[newSize];
        for (int i = 0; i < dataList.length; i++) {
            dataList[i] = new LinkedList<>();
        }

        // Place the data into the new array.
        for (int i = 0; i < temporaryList.length; i++) {
            LinkedList<DataEntry> dataEntries = temporaryList[i];
            for (DataEntry dataEntry : dataEntries) {
                insertData(dataEntry.key, dataEntry.value);
            }
        }
    }

    // Getter
    public LinkedList<DataEntry>[] getDataList() {
        return dataList;
    }

    // Getter for test. Getting the number of zero size buckets.
    public int getCountNull() {
        return zeroBuckets;
    }
}
