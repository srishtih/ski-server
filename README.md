# Implemting the ski resort backend system using Kubernetes
The implementation records ski lift rides from different lifts, resorts into a redis database. All requests from clients are validated at server and then written into a RabbitMQ queue and lift rides as JSON objects.
The consumer will pull this data and write in in suitable key-value format into Redis.

We start by creating an EKS cluster, add a 5 replica node group nd set Kubeconfig. We start off by deploying RabbitMQ as a K8s service, followed by deploying a Java web application as a service. Next, we set up Redis database as a service too in leader-follower format. Finally we deploy the consumer that passes the dara from server to Redis.

All the manifest files(within k8s-config folder) and java source code are included in this repository

## Detailed set-up steps:
### Setting up RabbitMQ as a service
    1. Install RabbitMQ operator using cluster-operator.yaml
    2. If your cluster does not have a Physical Volume Provisioner, then apply local-path-storage.yaml
    3. Create a RabbitMQ cluster using hello-world-rmq-cluster.yaml
    4. Usernme and password maybe extracted, if necessay. For our usgae, we read it from the secret in the web server's deployment manifest
    5. Port forward to 15672

    For detailed instructions, refer https://www.rabbitmq.com/kubernetes/operator/quickstart-operator.html
    
### Deploying Java web application as a K8s service
    1. Create deployment using ski-server-deployment.yaml
    2. Create service using ski-server-service.yaml
    3. Port forward to a local port 
    
    For detailed instructions, refer https://kubernetes.io/docs/tasks/access-application-cluster/port-forward-access-application-cluster/ 
    
### Setting up Redis as a service
    1. Create Redis followers using redis-follower-deployment.yaml and redis-follower-service.yaml
    2. Create Redis followers using redis-follower-deployment.yaml and redis-follower-service.yaml

    For detailed instuctions, refer https://kubernetes.io/docs/tutorials/stateless-application/guestbook/
    
### Running consumer as k8s deployment
    1. Create deployment using consumer-deployment.yaml
