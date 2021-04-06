# vcpkg-gui
GUI app for [vcpkg](https://github.com/microsoft/vcpkg) package manager. GUI provides simple operations: view all installed vcpkg packages, install/remove a package.

***Work in progress. May not work properly just yet.***

## Download and build

```
$ git clone https://github.com/mishkamashka/vcpkg-gui.git
$ cd vcpkg-gui
$ mvn install
```

## Set up and run
vcpkg-gui requires a pre-installed vcpkg tool. The app gets vcpkg location from a VCPKG_PATH environment variable or askes to enter it. Consider setting the variable not to enter the path every time the app starts.

```
$ java -jar target/vcpkg-gui.jar
```
