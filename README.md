This demo uses Jetty and Maven Shade plugin to build
self-contained applications that run as executable
jars.

The project demonstrates OAuth 2 security handshakes through
a number of modules.

Usage:
-----
1. At the project root, build IDE files and all modules
   * `mvn eclipse:eclipse -DdownloadSources` (or 'mvn idea:idea')
   * `mvn install`
2. Import the projects into your IDE. The project contains three modules,
   each with a server process.
3. You can either run a server in your IDE or from the built jar-file
   * From IDE, run as a main class e.g.
     oauth2-fun-ident/com.johannesbrodwall.oauth2fun.ident.IdentApplicationServer
     (in IntelliJ, make sure current working dir is oaut2-fun-ident)
   * From the command line, run
     `java -jar .../oauth2-fun-ident/target/oauth2-fun-ident.jar`
4. Go to http://localhost:11080 in your web browser

If you start the server class in the debug, many code changes will be
picked up without restart. If you want to restart the server, simply
start it again, and the new server will kill the old one.


Oauth 2 Ident module
====================

This module identifies the user by means of a third party
OAuth 2 service provider. It will support Facebook, Google
and potentially Twitter, Linkedin and Dropbox.

