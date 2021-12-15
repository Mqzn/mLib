# mLib
A simple library for Minecraft Spigot plugins.

This library includes the following utilities:
  1) Menus (for inventories and GUIs)
  2) Commands (supports sub-command trees)
  3) Files and config files
  4) Other crucial utilities (e.g: ItemBuilder, MessageBuilder)


## Using mLib
There are currently two ways of using mLib in your project:
- Artifact Dependency
- Maven Dependency

### Artifact Dependency
You can download the jar directly from [here](../../releases/tag/1.2.3), and use it as an artifact.

### Maven Dependency
You can find our public repository hosted on JitPack. All you need to do is add the JitPack repository, and then the mLib dependency. 
#### Example:
```xml
      <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
      </repositories>

      <dependencies>
        <dependency>
            <groupId>com.github.mqzn</groupId>
            <artifactId>mLib</artifactId>
            <version>1.2.3</version>
            <scope>provided</scope> 
        </dependency>
      </dependencies>
```
[![](https://jitpack.io/v/Mqzn/mLib.svg)](https://jitpack.io/#Mqzn/mLib)

## Using mLib on your server
mLib is a stand-alone plugin, this allows it to register its own listeners, and run along-side your other plugins which utilise it.

You can download the jar directly from [here](../../releases/tag/1.2.3), and place it in your plugins folder like any other, restart your server, and away you go!

## Utilities Guides
- [Menus](../../wiki/Menus)
- [Commands](../../wiki/Commands)
- [Files & Configs](../../wiki/Files_And_Configs)
- [Other](../../wiki/Other-Utilities)

