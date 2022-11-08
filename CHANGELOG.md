# Changes Log
All notable changes to this project (DataCROP Model Repository) will be documented in this file.

---
### [Release v0.3.0] - 2022-11-08

#### :chart_with_upwards_trend: Business Goal 
* Implementation of endpoints pertaining to Iot Systems (Asset Management). Version for MongoDB. ~ [Milestone](https://github.com/datacrop/maize-model-repository/milestone/1)

#### :rocket: Added 

* `model-repository-server`
  - [#10](https://github.com/datacrop/maize-model-repository/issues/10), [#11](https://github.com/datacrop/maize-model-repository/issues/11), [#13](https://github.com/datacrop/maize-model-repository/issues/13), [#14](https://github.com/datacrop/maize-model-repository/issues/14), [#15](https://github.com/datacrop/maize-model-repository/issues/15), [#16](https://github.com/datacrop/maize-model-repository/issues/16), [#24](https://github.com/datacrop/maize-model-repository/issues/24)
  Implementation of classes pertaining to IoT Systems and Locations on modules "commons", "mongodb", "persistence", "services" and "api". ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))

  - [#17](https://github.com/datacrop/maize-model-repository/issues/17), [#18](https://github.com/datacrop/maize-model-repository/issues/18)
  Implemented automated tests pertaining to IoT Systems and Locations on modules "mongodb" and "api". ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))
  
  - [#27](https://github.com/datacrop/maize-model-repository/issues/27)  
  Added Dockerfile and .dockerignorefile to define containerization. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))

* `deployment-scripts`
  - [#28](https://github.com/datacrop/maize-model-repository/issues/28)
  Created two docker-compose files: one to deploy only the databases for development and one to deploy locally a MongoDB - API pair. (The API is being pulled from DockerHub.) ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))

* `documentation`
  - [#19](https://github.com/datacrop/maize-model-repository/issues/19)
  Created Postman collection and environment. Exported and placed in documentation file. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))

  - [#20](https://github.com/datacrop/maize-model-repository/issues/20)
  Created API documentation pertaining to IoT Systems and Locations in .docx and .pdf format. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))

  - [#21](https://github.com/datacrop/maize-model-repository/issues/21)
  Configured automatic creation of Swagger UI. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))

  - [#23](https://github.com/datacrop/maize-model-repository/issues/23)
  Generated documentation using the Javadoc tool. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))

* `samples`
  - [#29](https://github.com/datacrop/maize-model-repository/issues/29)
  Created first request and response sample Systems in .json format. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))

#### :pencil2: Updated 

* `quick-dev-tools`
  - [#28](https://github.com/datacrop/maize-model-repository/issues/28)
  Renamed to "deployment-scripts". ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))

#### :wrench: Fixed 
---
### [Release v0.2.0] - 2022-10-30

#### :chart_with_upwards_trend: Business Goal 
* Instantiation of the Spring Boot Project with multiple properties files for different databases. ~ [Milestone](https://github.com/datacrop/maize-model-repository/milestone/2)

#### :rocket: Added 

* `model-repository-server`
  - [#8](https://github.com/datacrop/maize-model-repository/issues/8)
  Instantiated the Spring Boot Project as a Maven Project with six modules: API, services, persistence, mysql, mongo, commons. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))
  - [#9](https://github.com/datacrop/maize-model-repository/issues/9)
  Created the following application.properties files: one generic, one for Mongo configurations, one for H2 JPA database and one for MySQL JPA database.
* `quick-dev-tools\docker-compose.yml`
  - [#9](https://github.com/datacrop/maize-model-repository/issues/9)
  Created docker-compose.yml file for local installation of a MongoDB and a MySQL container for development purposes. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))
  

#### :pencil2: Updated 

#### :wrench: Fixed 
---
### [Release v0.1.0] - 2022-10-27

#### :chart_with_upwards_trend: Business Goal 
* Creation of the outer folder structure of the repository.

#### :rocket: Added 

* `documentation`
  - [#1](https://github.com/datacrop/maize-model-repository/issues/1)
  Created folder hosting documentation of the component's API, as well as a Swagger export in JSON format. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))
* `pictures`
  - [#2](https://github.com/datacrop/maize-model-repository/issues/2)
  Created folder hosting pictures for the documentation. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))
* `quick-dev-tools`
  - [#3](https://github.com/datacrop/maize-model-repository/issues/3)
  Created folder hosting quick setup helpers for development-only purposes on local machine. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))
* `samples`
  - [#4](https://github.com/datacrop/maize-model-repository/issues/4)
  Created folder hosting sample data models of the basic logical entities in JSON format, as well as an exported collection of sample Postman HTTP requests for the API. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))
* `CHANGELOG.md`  
  - [#5](https://github.com/datacrop/maize-model-repository/issues/5)
  Created the present document to keep track of the notable versions of the Model Repository component. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))
* `LICENCE.md`  
  - [#6](https://github.com/datacrop/maize-model-repository/issues/6)
  Created a document with details on the licensing scheme of the Model Repository component. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))  
* `README.md`
  - [#7](https://github.com/datacrop/maize-model-repository/issues/7)
  Created a documentation file describing the component's purpose, contents and status. ([@AngelaMariaDespotopoulou](https://github.com/AngelaMariaDespotopoulou))
  

#### :pencil2: Updated 

#### :wrench: Fixed 


