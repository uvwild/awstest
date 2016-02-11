# awstest

A spring boot rest service running on AWS Elastic Beanstalk PaaS product (EB).

##MileStone 1

* EB depoyment using Intellij Plugin 
* dataservice can be deployed
 
##MileStone 2

* local tomcat depoyment using cargo maven plugin 
* both services can be deployed using 
```clean package org.codehaus.cargo:cargo-maven2-plugin:daemon-start```

##MileStone 3

* EB depoyment using elasticbeanstalk maven plugin 
* both services can be deployed using 
```mvn clean package -Peb-deploy```

 

