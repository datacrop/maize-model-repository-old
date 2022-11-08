# DataCROP Model Repository ~ Maize :corn: (V3.0) 
Implementation of databases based on the DataCROP Maize :corn: (V3.0) digital models. 
<p align="center">
<a href="https://github.com/datacrop/maize-model-repository/releases"><img src="https://img.shields.io/badge/latest%20release-v0.3.0-blueviolet" alt="Release"></a>
<a href="https://github.com/datacrop/maize-model-repository/blob/main/LICENSE"><img src="https://img.shields.io/github/license/datacrop/maize-model-repository" alt="License"></a>
</p>

<p align="center">
  <img src="../pictures/release.jpg" width="500px" />
</p>

## Directory contents
The present directory contains scripts for easy deployment of components for both development and production environments. Here follows a summary of the current directory's contents:
* **[docker-compose-api-and-mongo.yml](docker-compose-api-and-mongo.yml)**: this file contains configurations that deploy the Model Repository to be used along with a MongoDB instance.
* **[docker-compose-databases-only.yml](docker-compose-databases-only.yml)**: this file contains configurations to locally deploy a MongoDB and a MySQL instance for development purposes.

## Deployment using Docker-Compose (example)
1. Command to **deploy** the stack on localhost:
> docker-compose -f docker-compose-api-and-mongo.yml -p modelrepo up -d 

2. To test deployment, the Swagger interface must appear here:
> <a href="" onclick="return false">http://localhost:8087/swagger-ui/index.html#/</a>

3. Command to **undeploy** the stack:
> docker-compose -f docker-compose-api-and-mongo.yml -p modelrepo down

4. Command to clear the unused images/volumes for **housekeeping** (be <u>sure you really want to</u> before doing this):

> docker system prune -a