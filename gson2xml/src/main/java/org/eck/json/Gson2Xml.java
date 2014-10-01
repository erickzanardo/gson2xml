package org.eck.json;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Gson2Xml {

    public String parse(JsonObject object, String rootElement) {

        if (object == null) {
            throw new RuntimeException("Cannot parse a null object");
        }

        StringBuilder ret = new StringBuilder();

        processJsonObject(object, ret, rootElement);

        return ret.toString();
    }

    private void processJsonObject(JsonObject object, StringBuilder xml, String nodeName) {
        Set<Entry<String, JsonElement>> entrySet = object.entrySet();
        xml.append("<").append(nodeName).append(">");
        for (Entry<String, JsonElement> entry : entrySet) {
            JsonElement value = entry.getValue();
            if (value.isJsonObject()) {
                processJsonObject(value.getAsJsonObject(), xml, entry.getKey());
            } else if (value.isJsonArray()) {
                xml.append("<").append(entry.getKey()).append(">");

                JsonArray asJsonArray = value.getAsJsonArray();
                for (JsonElement jsonElement : asJsonArray) {
                    if (jsonElement.isJsonObject()) {
                        processJsonObject(jsonElement.getAsJsonObject(), xml, "entry");
                    } else {
                        processElementNode(jsonElement, "entry", xml);
                    }
                }

                xml.append("</").append(entry.getKey()).append(">");
            } else {
                processElementNode(entry.getValue(), entry.getKey(), xml);
            }
        }
        xml.append("</").append(nodeName).append(">");
    }

    private void processElementNode(JsonElement value, String key, StringBuilder xml) {
        String nodeValue = "";
        if (!value.isJsonNull()) {
            nodeValue = value.getAsString();
        }

        createNode(nodeValue, key, xml);
    }

    private void createNode(String nodeValue, String key, StringBuilder xml) {
        xml.append("<").append(key).append(">").append(nodeValue).append("</").append(key).append(">");
    }

}
