# WebHiob

Welcome in WebHiob - interactive learining environment for security of modern web applications.

WebHiob was created a as part of the master thesis by Robert Rozmus (student of Warsaw University of Technology, Institute  of Computer Science).
If you have any questions, please do not hesitate to contact with the <a href="mailto:robertrozmusjob@gmail.com">author</a>.

Copyright (c) 2015 Robert Rozmus

<h4>How to run</h4>

To run WebHiob it is required to provide:
<ol>
  <li>  <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html">Java SE Development Kit 7  </a> </li>
<li> <a href="https://gradle.org">Gradle</a> </li>
<li> an IDE supporting Gradle (recommended  <a href="https://www.jetbrains.com/idea">InteliiJ IDEA</a> )</li>
<li> <a href="http://tomcat.apache.org">Tomcat</a> (version 7 or 8) </li>
<li> <a href="http://www.gwtproject.org/download.html">GWT SDK</a> </li>
</ol>

To be able to take advantage of all lessons it is recommended to install <a href="https://www.owasp.org/index.php/OWASP_Zed_Attack_Proxy_Project">Zed Attack Proxy</a> (or
similar tool allowing to intercept HTTP requests and responses).

Be aware that WebHiob uses <a href="https://projectlombok.org/index.html">Project Lombok</a>, therefore before compilation you must enable annotation processing (In IntelliJ IDEA it could be done by pressing the File->Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors tab and checking the "Enable annotation processing" checkbox.

To run WebHiob you should use the gradle "build" task.





<h4>Warnings</h4>
While running WebHiob your machine will be vulnerable to attack, therefore you should disconnect from the Internet while using it.

The WebHiob environment is for educational purposes only. You should not try to attempt these techniques on the Internet without authorization, because it is against to the law.
