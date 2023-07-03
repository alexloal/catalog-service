# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Commands
Build the project image in Docker
```shell 
docker build -t polarbookshop/catalog-service:0.0.1 .
```

Run the image in Docker
```shell
docker run --rm --name catalog-service -p 8080:8080 polarbookshop/catalog-service:0.0.1
```

Start up minikube 
```shell
minikube start
```

Stop minikube
```shell
minikube stop
```

Load the image in minikube
```shell
minikube image load polarbookshop/catalog-service:0.0.1
```

Create the deployment of the image specified
```shell
kubectl create deployment catalog-service --image=polarbookshop/catalog-service:0.0.1
```

Display minikube dashboard
```shell
minikube dashboard
```

Display deployment information
```shell
kubectl get deployment
```

Display pod information
```shell
kubectl get pod
```

Display the logs of the service
```shell
kubectl logs deployment/catalog-service
```

Expose the app to be accessible
```shell
kubectl expose deployment catalog-service --name catalog-service --port=8080
```

Display service information
```shell
kubectl get service catalog-service
```

Map the service port with the localhost port
```shell
kubectl port-forward service/catalog-service 8000:8080
```

To close the mapping
```shell
Ctrl+c
```

Delete the service
```shell
kubectl delete service catalog-service
```

Delete the deployment
```shell
kubectl delete deployment catalog-service
```