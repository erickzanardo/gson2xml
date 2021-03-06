gson2xml
========

Simple API to generate and XML from a JsonObject (Gson library)

### How to use

```java
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("FieldInteger", 1);
        jsonObject.addProperty("FieldDouble", 1.3);
        jsonObject.addProperty("FieldString", "Test");
        jsonObject.addProperty("FieldBoolean", true);

        Gson2Xml parser = new Gson2Xml();
        String xml = parser.parse(jsonObject, "doc");
```

Results in:

```xml
<doc>
        <FieldInteger>1</FieldInteger>
        <FieldDouble>1.3</FieldDouble>
        <FieldString>Test</FieldString>
        <FieldBoolean>true</FieldBoolean>
</doc>
```
When using arrays, the default tag name for it's itens are "entry", for example, this code:
```java
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive(1));
        jsonArray.add(new JsonPrimitive(2));
        jsonObject.add("Values", jsonArray);

        Gson2Xml parser = new Gson2Xml();
        String xml = parser.parse(jsonObject, "doc");
```

Results in:

```xml
<doc>
        <Values>
                <entry>1</entry>
                <entry>2</entry>
        </Values>
</doc>
```   

You can create a map to tell gson2xml how to resolve the name of the entries of the array by passing an Map as a parameter to the Gson2Xml constructor, for example:

```java
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive(1));
        jsonArray.add(new JsonPrimitive(2));
        jsonObject.add("Values", jsonArray);

        Map<String, String> arrayNameResolver = new HashMap<String, String>();
        arrayNameResolver.put("Values", "Value");
        Gson2Xml parser = new Gson2Xml(arrayNameResolver);
        String xml = parser.parse(jsonObject, "doc");
```

Resulting in: 

```xml
<doc>
        <Values>
                <Value>1</Value>
                <Value>2</Value>
        </Values>
</doc>
```
### Maven

```xml
  <repository>
    <id>erickzanardo-releases</id>
    <url>http://erickzanardo.github.com/maven/releases/</url>
  </repository>

  <dependency>
    <groupId>org.eck.json</groupId>
    <artifactId>gson2xml</artifactId>
    <version>1.0.0</version>
  </dependency>
```
