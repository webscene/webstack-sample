# Webstack Sample (webstack-sample)

Sample full stack web app with both the server and client side written in Kotlin using the Webscene Client and Server libraries.


## Usage

Be sure to create (publish) the local maven repository for both Webscene Client and Server, followed by copying the generated directories to [path_to_webstack-sample]/client/libs and [path_to_webstack-sample]/server/libs. In order to run the sample execute the following Gradle tasks:
1. client:deployClientToServer
2. server:run

Remember to run the **client:deployClientToServer** Gradle task whenever changes are made to the client. Everytime the client is deployed the server will automatically restart, therefore only a refresh is needed on the web browser side to pick up the changes.