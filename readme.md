minikube start --driver=hyperkit             
minikube service --url rabbitmq - run 
kubectl apply -f bootstrap/zipkin - run 
kubectl get pods -w
kubectl logs zipkin-0
kubectl get all
minikube service --url rabbitmq   