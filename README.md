# SpringK8S

# Spring K8s Demo  
Aplicatie demo realizată pentru a demonstra containerizarea și deploy-ul unei aplicații Spring Boot folosind Docker și Kubernetes (Minikube).

---

## Tehnologii folosite
- Java 21  
- Spring Boot 4.x  
- Docker  
- Kubernetes  
- Minikube  
- kubectl  
- Postman (pentru testare API)

---

## Descrierea proiectului

Acest proiect conține o aplicație Spring Boot simplă care expune două endpoint-uri REST:

- `/api/info` – informații despre aplicație  
- `/api/random` – generează un număr random, UUID și timestamp  

Scopul proiectului este de a demonstra întregul flux DevOps:

1. Dezvoltarea aplicației Spring Boot  
2. Build local și generare JAR  
3. Containerizarea aplicației cu Docker  
4. Rularea aplicației în container  
5. Crearea unui Deployment și Service în Kubernetes  
6. Deploy în Minikube  
7. Testarea serviciului cu Postman

---

## 📁 Structura proiectului

spring-k8s/
├── src/main/java/...
├── src/main/resources/application.properties
├── Dockerfile
├── k8s.yaml
├── pom.xml
└── target/*.jar


---

## 🚀 Endpoint-uri expuse

### GET `/api/info`
Returnează informații generale despre aplicație.

### GET `/api/random`
Returnează un JSON cu:
- random number  
- UUID  
- mesaj demonstrativ  

Exemplu:

```json
{
  "app": "spring-k8s-demo",
  "version": "1.0.0",
  "timestamp": "2025-12-17T11:43:55Z"
}

```
## Build si rulare locala

mvn clean package
mvn spring-boot:run


## Containerizare cu Docker

```Dockerfile
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","/app/app.jar"]
```

## Deploy in Kubernetes(minikube)

Pornire minikube
  -minikube start --driver=docker
Folosirea dockerului Minikube pentru rebuild
  -eval $(minikube docker-env)
  -docker build -t spring-k8s-demo


## k8s.yaml file
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-k8s-demo
spec:
  replicas: 1
  selector:
    matchLabels:
      app: spring-k8s-demo
  template:
    metadata:
      labels:
        app: spring-k8s-demo
    spec:
      containers:
        - name: spring-k8s-demo
          image: spring-k8s-demo:1.0
          imagePullPolicy: Never
          ports:
            - containerPort: 9001

---
apiVersion: v1
kind: Service
metadata:
  name: spring-k8s-demo
spec:
  type: NodePort
  selector:
    app: spring-k8s-demo
  ports:
    - port: 9001
      targetPort: 9001
      nodePort: 30001
```


### Deploy in cluster
-kubectl apply -f k8s.yaml
-kubectl pods
-kubectl svc
-minikube ip

### Testarea serviciului

`Din WSL :` curl http://$(minikube ip):30001/api/info
`Din Postman : windows nu a putut accesa direct ip-ul minikube din wsl. Facem port-forwarding pentru a testa cu postman.`

  -kubectl port-forward service/spring-k8s-demo 5432:5432
  
  -apoi in Postman: GET http://localhost:5432/api/info
