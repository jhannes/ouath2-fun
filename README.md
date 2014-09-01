This demo uses Jetty and Maven Shade plugin to build
self-contained applications that run as executable
jars.

The project demonstrates OAuth 2 security handshakes through
a number of modules.

Usage:
-----
1. At the project root, build IDE files and all modules
   * `mvn eclipse:eclipse -DdownloadSources` (or IDEA)
   * `mvn install`
2. Import the projects into your IDE
3. In your IDE, run as a main class
   oauth2-fun-ident/com.johannesbrodwall.oauth2fun.ident.IdentApplicationTestServer
4. From the command line, run `java -jar .../oauth2-fun-ident/target/oauth2-fun-ident.jar`
   * `oauth2-fun-server` and `oauth2-fun-client` have similar main classes
5. Go to http://localhost:11080 in your web browser

If you start the server class in the debug, many code changes will be
picked up without restart. If you want to restart the server, simply
start it again, and the new server will kill the old one.


Oauth 2 Ident module
====================

This module identifies the user by means of a third party
OAuth 2 service provider. It will support Facebook, Google
and potentially Twitter, Linkedin and Dropbox.

