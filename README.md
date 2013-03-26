# Spring MVC test console

Maven plugin that generates test console for web projects that use spring mvc [@RequestMapping](http://static.springsource.org/spring/docs/2.5.x/api/org/springframework/web/bind/annotation/RequestMapping.html).
The output is an html file that can be further deployed with the application inside a war file.
The resulting console is a list of html forms for all the methods marked with the specified annotation.
Every form can be asynchronously submitted and the response will appear on the page.

