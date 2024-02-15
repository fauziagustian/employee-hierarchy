# employee-hierarchy

# This application should let user to
- [x] Search for an employee,
- [x] Display all his/her managers name up to the root of the tree, and
- [x] Display the total count of his/her direct & indirect reports.

# what is in the project  ?
1. design pattern (Controller - Service - Repository(not use))
2. functional test 
3. unit test and coverage docs using jacoco
4. swagger
5. spring-javaformat 
6. sonarlint
7. docker
8. ci/cd

# specification apps : 
- java version 17
- maven version 3.8.8
- this apps is uploaded using visual studio code

# update program 
1. after you finish the program dont forget for run "mvn spring-javaformat:apply"
2. in this app using validate must be run that command before "mvn clean install" or etc mvn
3. this is usefull for making code format is structured like golang 

# running apps
1. cloning this apps on you folder project : git clone https://github.com/fauziagustian/employee-hierarchy.git
2. run command "cd ./employee-hierarchy"
3. write on your command using bash or powershell : "mvn clean install"
4. after that run command "mvn spring-boot:run"
5. and the application has started.

# pull my docker image from docker hub
1. run this command "docker pull fauzidockerhub/employe-hierarchy-images:latest"

# open swagger
1. go on browser and open this url : http://localhost:8080/employee-hierarchy/swagger-ui/index.html
2. execute if you want test service apps

# sample of request and response : 
1. evelina
  - request : http://localhost:8080/employee-hierarchy/employees/evelina
  - response :
```json
{
  "totalReports": 0,
  "employee": "evelina",
  "managersHierarchy": [
    "eveleen",
    "tabitha",
    "lori"
  ]
}
```
3. martin
   - request : http://localhost:8080/employee-hierarchy/employees/evelina
   - response :
```json
{
  "error": "Employee not found",
  "message": "the employee : martin does not exist"
}
```
5. keane and kylee
   - request : http://localhost:8080/employee-hierarchy/employees/keane
   - response :
```json
{
  "error": "Employee Hierarchy Error",
  "message": "Unable to process employee hierarchy. keane does not have any hierarchy."
}
```
- request : http://localhost:8080/employee-hierarchy/employees/kylee
   - response :
```json
{
  "error": "Employee Hierarchy Error",
  "message": "Unable to process employee hierarchy. kylee does not have any hierarchy."
}
```
6. linton
   - request : http://localhost:8080/employee-hierarchy/employees/linton
   - response :
```json
{
  "error": "Multiple Managers Found",
  "message": "Unable to process employee tree. linton has multiple managers: fletcher, lori"
}
```

# run unit test
1. run command line "mvn clean test"
2. if you see the document of coverage you can run "mvn clean test jacoco:report"
3. after you run jacoco you can see the documentation of unit test and coverage in file index.html with the folder : ./employee-hierarchy/target/site/jacoco/index.html

# screenshot coverage unit test using jacoco
![image](https://github.com/fauziagustian/employee-hierarchy/assets/64592796/813de6dc-b703-423a-a141-40157ddeb734)

# sonarlint
i have using sonarlint in my code editor, the code is 100% clean and don't have sonarlint comment or notification from sonarlint.

# screenshot sample of ci/cd in my github
![pipelines ](https://github.com/fauziagustian/employee-hierarchy/assets/64592796/89ab4c5e-5963-4c80-8343-7e5a1fdca988)




