package org.eck.json;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Gson2Xml {

    private Map<String, String> arrayNameResolver;

    public Gson2Xml(Map<String, String> arrayNameResolver) {
        this.arrayNameResolver = arrayNameResolver;
    }

    public Gson2Xml() {
        this(null);
    }

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
                processJsonArray(xml, entry.getKey(), value.getAsJsonArray());
            } else {
                processElementNode(entry.getValue(), entry.getKey(), xml);
            }
        }
        xml.append("</").append(nodeName).append(">");
    }

    private void processJsonArray(StringBuilder xml, String key, JsonArray value) {
        xml.append("<").append(key).append(">");

        String entryName = resolveEntryName(key);
        
        for (JsonElement jsonElement : value) {
            if (jsonElement.isJsonObject()) {
                processJsonObject(jsonElement.getAsJsonObject(), xml, entryName);
            } else if (jsonElement.isJsonArray()) {
                processJsonArray(xml, entryName, jsonElement.getAsJsonArray());
            } else {
                processElementNode(jsonElement, entryName, xml);
            }
        }

        xml.append("</").append(key).append(">");
    }

    private String resolveEntryName(String key) {
        String name = "entry";
        if (arrayNameResolver != null && arrayNameResolver.get(key) != null) {
            name = arrayNameResolver.get(key);
        }
        return name;
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
