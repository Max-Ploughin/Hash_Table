

public class Main {

    public static void main(String[] args) {

        MyHashTable myHashTable = new MyHashTable();
        String key1 = "key";
        String value1 = "value";
        String key2 = "key2";
        String value2 = "value2";
        String key3 = "key3";
        String value3 = "value3";
        myHashTable.insertData(key1, value1);
        System.out.println(myHashTable.getValue("key"));
        myHashTable.insertData(key2, value2);
        myHashTable.insertData(key3, value3);

        System.out.println(myHashTable.getValue("key"));

    }

}
