## Overview

This is a config API meant to provide compatibility across multiple different other config libraries as well as be easy to use in a multiloader project setup, mainly for use in my mods.

It features a simple to use annotation-based syntax, to make creating new configuration files as easy as possible.

If you are a mod user, this information is likely not very useful to you. If you are a mod developer however, and you would like to use Lexiconfig in your own projects, scroll further to the Development section.

It currently does not have support for other configuration libraries, but as I continue to update it, I will add more compatibilities to each one to ensure that it does not conflict with others and can instead seamlessly integrate between them.

Here is a list of current as well as planned integrations across different config libraries/api:

❌ - Unintegrated
✅ - Integrated
❓ - In Progress
|    Status     |    Mod    |
| ------------- | ------------- |
| ❌ | Cloth Config |
| ❌ | oωo config |
| ❌ | Configured |

## Development
This mod contains an api that can be used to shelve new lexicons, listen to events, etc.

### Depending

build.gradle
```
dependencies {
  implementation "com.codinglitch.vibrativevoice:vibrativevoice-api:API_VERSION_HERE"
}
```
You can go to [https://versions.codinglitch.com](https://versions.codinglitch.com) to view the latest api version.

### Usage
To shelve your own lexicons, you first have to your own Library. This can be done by simply creating a new class extending the Library class, and annotating it with the LexiconLibrary annotation.
```java
@LexiconLibrary
public class MyNewLibrary extends Library {
  @Override
  public void shelveLexicons() {
      
  }
}
```
In a Forge setup, no further changes need to be made. However, if you are developing for Fabric, you will have to add this class as an entrypoint in your fabric.mod.json.

fabric.mod.json
```json
//...
"entrypoints": {
  "lexiconfig": [
    "com.path.to.MyNewLibrary"
  ]
}
//...
```

The next step is to finally create and shelve your lexicons. You can do this by simply creating another class for the lexicon, and providing it with the proper annotations, like as follows:
```java
@Lexicon(name = MY_LEXICON_TITLE) // preferably use your mod id as a name
public class MyLexicon extends LexiconData {
  @LexiconEntry(comment = "This is a comment for a simple field!")
  public String mySimpleField = "content";

  @LexiconPage(comment = "This is a fancy new comment on the fancy new category!")
  public MyNewPage myNewPage = new MyNewPage();

  public static class MyNewPage extends LexiconPageData {
    @LexiconEntry(comment = "This field is inside a lexicon page!")
    public Boolean myPageField = true;
  }
}
```

The next and last step is to shelve the lexicon, which can be done by simply adding an instance of it as a field in the library and shelving it in the given method.
```java
@LexiconLibrary
public class MyNewLibrary extends Library {
  public static MyLexicon MY_LEXICON = new MyLexicon();
  
  @Override
  public void shelveLexicons() {
    LexiconfigApi.shelveLexicon(MY_LEXICON);
  }
}
```
