gson2xml
========

Simple API to generate and XML from a JsonObject (Gson library)

=== How to use

´´´java
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("FieldInteger", 1);
        jsonObject.addProperty("FieldDouble", 1.3);
        jsonObject.addProperty("FieldString", "Test");
        jsonObject.addProperty("FieldBoolean", true);

        Gson2Xml parser = new Gson2Xml();
        String xml = parser.parse(jsonObject, "doc");
´´´

Results in

´´´xml
  <doc>
    <FieldInteger>1</FieldInteger>
    <FieldDouble>1.3</FieldDouble>
    <FieldString>Test</FieldString>
    <FieldBoolean>true</FieldBoolean>
  </doc>
´´´
