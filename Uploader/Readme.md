Todo
- search files by size, by name
- properly handle errors
- check na velikost souboru v klientovi
- UI tests - test that file over 500KB cannot be uploaded

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