# Configuration
A Java configuration API with an implementation for parsing data from XML-based configuration files to a (Map) memory structure.

# General
This configuration API does not use or require any 3rd party libraries. Currently there is only an implementation for loading (parsing) and saving (transforming) XML-based configuration files using the Java DOM API. However, it is possible to create implementations for other file types. The most important class and the entry point to this configuration API is the [```Configuration``` class](src/main/java/com/vanillage/utils/configuration/Configuration.java). Instances of this class provide methods for loading data from a file into an object and saving the data from an object to a file. This object is a field called ```content``` which can be set or retrieved from the [```Configuration``` class](src/main/java/com/vanillage/utils/configuration/Configuration.java). Typically the ```content``` object is a structure of ```java.util.Map```s, where the keys represent XML tags and the values represent the content of XML elements. XML attributes are being used to store ```type``` information. The [```Configuration``` class](src/main/java/com/vanillage/utils/configuration/Configuration.java) provides methods to retrieve values from the ```Map``` structure and for modifying it. Besides the ```content```, the [```Configuration``` class](src/main/java/com/vanillage/utils/configuration/Configuration.java) also provides support for a default and an example configuration and has fields for a [```ConfigurationParser```](src/main/java/com/vanillage/utils/configuration/ConfigurationParser.java) and a [```ConfigurationTransformer```](src/main/java/com/vanillage/utils/configuration/ConfigurationTransformer.java). Subclasses of these classes actually contain the implementation for saving and loading data from a specific file type. The [```ConfigurationParser```](src/main/java/com/vanillage/utils/configuration/ConfigurationParser.java) and the [```ConfigurationTransformer```](src/main/java/com/vanillage/utils/configuration/ConfigurationTransformer.java) basically create and convert the ```Map```s. These ```Map```s can then be converted to other kinds of objects by registering [```ConfigurationDeserializer```s](src/main/java/com/vanillage/utils/configuration/deserializers/) and [```ConfigurationSerializer```s](src/main/java/com/vanillage/utils/configuration/serializers/).

# Getting started
At first one has to create the [```Configuration```](src/main/java/com/vanillage/utils/configuration/Configuration.java) object. This can be done by either using a constructor or using the [```XmlConfigurationFactory```](src/main/java/com/vanillage/utils/configuration/xml/XmlConfigurationFactory.java). By default, this will create [```Configuration```](src/main/java/com/vanillage/utils/configuration/Configuration.java) object for XML files. Then one can directly set some values and save the configuration to a file.
```java
Configuration configuration = XmlConfigurationFactory.newConfiguration();
configuration.set(3, "this", "is", "a", "path");
configuration.set("text", "this", "is", "another", "path");
configuration.set("3.0", "path", "to", "a", "string");
configuration.set(3.0, "path", "to", "a", "double");
configuration.set(3.0f, "path", "to", "a", "float");
configuration.saveSilent(new File("configuration.xml"));
```
Below is the resulting configuration.xml file. Some entries have a ```type``` attribute. These are defined by the [```ConfigurationSerializer```s](src/main/java/com/vanillage/utils/configuration/serializers/) and are necessary in some cases to identify the type correctly when the configuration is loaded again.
```xml
<configuration>
  <this>
    <is>
      <a>
        <path>3</path>
      </a>
      <another>
        <path>text</path>
      </another>
    </is>
  </this>
  <path>
    <to>
      <a>
        <string type="String">3.0</string>
        <double>3.0</double>
        <float type="Float">3.0</float>
      </a>
    </to>
  </path>
</configuration>
```
As already mentioned one can also get the ```content``` or some values from the Map structure. The above created configuration.xml file is loaded for this example. A query can be specified by a lambda expression for a ```Predicate```.
```java
Configuration configuration = XmlConfigurationFactory.newConfiguration();
configuration.loadSilent(new File("configuration.xml"));
System.out.println(configuration.getContent());
System.out.println(configuration.get(x -> x instanceof Integer, "this", "is", "a", "path"));
System.out.println(configuration.get(x -> x instanceof Number, "path", "to", "a", "double"));
System.out.println(configuration.get(x -> x instanceof Number, "path", "to", "a", "float"));
System.out.println(configuration.get(x -> x instanceof Number, "not", "existing", "path"));
```
Below is the printed result. If the configuration and its default configuration don't contain a requested value, a special value is returned.
```
{this={is={a={path=3}, another={path=text}}}, path={to={a={string=3.0, double=3.0, float=3.0}}}}
3
3.0
3.0
NO_VALUE_INSTANCE
```

# Custom serializers and deserializers
TODO.

# Special string and object keys/tags
Unfortunately XML doesn't allow using any ```String``` as key/tag name. For example the ```String``` ```"1st"``` is not allowed because the first character is a digit. Another problem is representing an empty ```String```. However, this API does support all ```String```s and even better, it also allows using objects as keys/tags without violating the XML Specification.
TODO: Example.
