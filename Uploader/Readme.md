Todo
- search files by size, by name
- properly handle errors
- check na velikost souboru v klientovi
- UI tests - test that file over 500KB cannot be uploaded

https://bezkoder.com/angular-spring-boot-file-upload/


https://www.toptal.com/java/spring-boot-rest-api-error-handling
https://rieckpil.de/test-spring-applications-using-aws-with-testcontainers-and-localstack/
https://nirajsonawane.github.io/2019/12/25/Testcontainers-With-Spring-Boot-For-Integration-Testing/
https://stackoverflow.com/questions/53092222/spring-cloud-testing-s3-client-with-testcontainters
https://www.youtube.com/watch?v=pNg72FknNco
https://www.baeldung.com/spring-boot-testcontainers-integration-test
https://dzone.com/articles/testcontainers-and-spring-boot
https://stackabuse.com/spring-cloud-aws-s3/
https://medium.com/@justipops/containerized-spring-integration-tests-using-test-containers-and-localstack-78b0e5018e99
https://www.novatec-gmbh.de/en/blog/testcontainers-bring-your-integration-tests-to-a-new-level/
https://dev.to/rieckpil/write-spring-boot-integration-tests-with-testcontainers-junit-4-5-40am

https://cloud.spring.io/spring-cloud-aws/spring-cloud-aws.html#_using_amazon_web_services


https://bezkoder.com/integrate-angular-spring-boot/


## Context
Uploader is a web application for management of uploaded images (PNG and JPEG)) running in AWS.
The user can:
- upload files
- list all files
- delete files
- search file by name, description and size
  
![Context Diagram](Uploader.png)

Maximum size of the uploaded files is 500 KB.
User authentication and authorization is not required.