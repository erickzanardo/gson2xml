package org.eck.json;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Gson2XmlTest {

    @Test(expected = RuntimeException.class)
    public void testNull() {
        Gson2Xml parser = new Gson2Xml();
        parser.parse(null, "doc");
    }

    @Test
    public void testParsePrimitives() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("FieldInteger", 1);
        jsonObject.addProperty("FieldDouble", 1.3);
        jsonObject.addProperty("FieldString", "Test");
        jsonObject.addProperty("FieldBoolean", true);

        Gson2Xml parser = new Gson2Xml();
        String xml = parser.parse(jsonObject, "doc");
        assertEquals(
                "<doc><FieldInteger>1</FieldInteger><FieldDouble>1.3</FieldDouble><FieldString>Test</FieldString><FieldBoolean>true</FieldBoolean></doc>",
                xml);
    }

    @Test
    public void testParseNestedObject() {
        JsonObject jsonObject = new JsonObject();
        JsonObject nested = new JsonObject();
        nested.addProperty("FieldInteger", 1);
        jsonObject.add("Nested", nested);

        Gson2Xml parser = new Gson2Xml();
        String xml = parser.parse(jsonObject, "doc");
        assertEquals(
                "<doc>" +
                  "<Nested>" +
                     "<FieldInteger>1</FieldInteger>"+
                   "</Nested>" +
                 "</doc>",
                xml);
    }

    @Test
    public void testParseSimpleArray() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive(1));
        jsonArray.add(new JsonPrimitive(2));
        jsonObject.add("Values", jsonArray);

        Gson2Xml parser = new Gson2Xml();
        String xml = parser.parse(jsonObject, "doc");
        assertEquals(
                "<doc>" +
                  "<Values>" +
                     "<entry>1</entry>"+
                     "<entry>2</entry>"+
                   "</Values>" +
                 "</doc>",
                xml);
    }

    @Test
    public void testParseArrayObject() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        
        JsonObject arrayObject = new JsonObject();
        arrayObject.addProperty("Field", 1);
        jsonArray.add(arrayObject);
        jsonObject.add("Values", jsonArray);

        Gson2Xml parser = new Gson2Xml();
        String xml = parser.parse(jsonObject, "doc");
        assertEquals(
                "<doc>" +
                  "<Values>" +
                     "<entry><Field>1</Field></entry>"+
                   "</Values>" +
                 "</doc>",
                xml);
    }

    @Test
    public void testParseArrayInsideArray() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        JsonArray jsonArray2 = new JsonArray();
        jsonArray2.add(new JsonPrimitive(1));

        jsonArray.add(jsonArray2);
        jsonObject.add("Values", jsonArray);

        Gson2Xml parser = new Gson2Xml();
        String xml = parser.parse(jsonObject, "doc");
        assertEquals(
                "<doc>" +
                  "<Values>" +
                     "<entry>" +
                        "<entry>1</entry>"+
                      "</entry>"+
                   "</Values>" +
                 "</doc>",
                xml);
    }
    
    @Test
    public void testParseArrayItemNomeResolver() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive(1));
        jsonArray.add(new JsonPrimitive(2));
        jsonObject.add("Values", jsonArray);

        Map<String, String> arrayNameResolver = new HashMap<String, String>();
        arrayNameResolver.put("Values", "Value");
        Gson2Xml parser = new Gson2Xml(arrayNameResolver);
        String xml = parser.parse(jsonObject, "doc");
        assertEquals(
                "<doc>" +
                  "<Values>" +
                     "<Value>1</Value>"+
                     "<Value>2</Value>"+
                   "</Values>" +
                 "</doc>",
                xml);
    }
}
