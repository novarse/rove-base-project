 - Build Setup
 **jbossHome** must be specified in **gradle.properties** of API project to deploy project in local server.
 
 - Build API (Must execute following task within API project)
	- execute **gradle build** to create war
	- execute **gradle deploy** to create war and deploy to server. 
 - Build API with UI 
    - Navigate to root project   
	- execute **gradle build** . This will build all the projects 
	- Navigate to API project
	- execute **gradle deploy** 
 - Elasticsearch configuration
	 - Extract src/test/resources hunspell.zip to <elasticsearch-home>/config directory. This dictionary is used for hunspell stemmer.