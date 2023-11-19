import java.util.Objects;

public class DataEntry {

    public String key;
    public String value;

    public DataEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataEntry{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

//
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DataEntry otherDataEntry = (DataEntry) obj;
        return Objects.equals(key, otherDataEntry.key) && Objects.equals(value, otherDataEntry.value);
    }
}
