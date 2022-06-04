import java.util.HashMap;
import java.util.Map;

public class Specifications {
    private HashMap<String, HashMap<String, String>> specifications;
    private String lastCategory = null;

    public Specifications() {
        specifications = new HashMap<>();
    }

    public void addCategory(String categoryName) {
        categoryName = categoryName.strip();
        if (!specifications.containsKey(categoryName)) {
            specifications.put(categoryName, new HashMap<>());
            lastCategory = categoryName;
        }
        else {
            throw new IllegalStateException("New category (categoryName) already exist!");
        }
    }

    public void addCharacteristic(String characteristicName, String characteristicValue) {
        addCharacteristic(lastCategory, characteristicName, characteristicValue);
    }

    public void addOrUpdateCharacteristic(String characteristicName, String characteristicValue) {
        addOrUpdateCharacteristic(lastCategory, characteristicName, characteristicValue);
    }

    public void addCharacteristic(String categoryName, String characteristicName, String characteristicValue) {
        categoryName = categoryName.strip();
        characteristicName = characteristicName.strip();
        characteristicValue = characteristicValue.strip();

        try {
            if (!specifications.get(categoryName).containsKey(characteristicName)) {
                specifications.get(categoryName).put(characteristicName, characteristicValue);
                lastCategory = categoryName;
            }
            else if (!specifications.get(categoryName).get(characteristicName).equals(characteristicValue))
                throw new IllegalStateException("New data (characteristicValue) contradict existing!");
        }
        catch (NullPointerException e) {
            specifications.put(categoryName, new HashMap<>());
            specifications.get(categoryName).put(characteristicName, characteristicValue);
            lastCategory = categoryName;
        }
    }

    public void addOrUpdateCharacteristic(String categoryName, String characteristicName, String characteristicValue) {
        try {
            addCharacteristic(categoryName, characteristicName, characteristicValue);
        }
        catch (IllegalStateException e) {
            specifications.get(categoryName).put(characteristicName, characteristicValue);
            lastCategory = categoryName;
        }
    }

    public HashMap<String, String> getCharacteristicsByCategory(String category) {
        return (HashMap<String, String>) specifications.get(category.strip()).clone();
    }

    public String getCharacteristicByName(String name) {
        name = name.strip();
        for (Map.Entry<String, HashMap<String, String>> category: specifications.entrySet())
            for (Map.Entry<String, String> characteristic: category.getValue().entrySet())
                if (characteristic.getKey().equals(name))
                    return characteristic.getValue();

        return null;
    }

    public String toJson() {
        String res = "{";
        boolean flag = true;
        for (Map.Entry<String, HashMap<String, String>> category: specifications.entrySet()) {
            if (!flag)
                res += ";\"" + category.getKey() + "\":{";
            else
                res += "\"" + category.getKey() + "\":{";
            flag = true;
            for (Map.Entry<String, String> characteristic : category.getValue().entrySet()) {
                if (!flag)
                    res += ";\"" + characteristic.getKey() + "\":\"" + characteristic.getValue() + "\"";
                else {
                    res += "\"" + characteristic.getKey() + "\":\"" + characteristic.getValue() + "\"";
                    flag = false;
                }
            }
            res += "}";
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "";
        for (Map.Entry<String, HashMap<String, String>> category: specifications.entrySet()) {
            res += category.getKey() + ":\n";
            for (Map.Entry<String, String> characteristic : category.getValue().entrySet())
                res += "\t" + characteristic.getKey() + ":\t" + characteristic.getValue() + "\n";
        }
        return res;
    }
}
