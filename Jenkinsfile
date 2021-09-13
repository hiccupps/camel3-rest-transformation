pipeline {

    agent any

    stages{

        stage("maven-build"){

          steps{
            echo "running maven build of your code ..!!"
            sh "mvn clean install -DskipTests"
          }
        }

        stage("docker-image-build"){

          steps{
           echo "building docker images for your new code...!!!!"
           sh "docker build -t abhishekkvvishnoi/my-app-image:latest ."
          }
        }


       stage("docker-image-push"){

          steps{
           echo "deploying an aplication.."
           sh "docker push abhishekkvvishnoi/my-app-image:latest"
          }

        }

       stage("kubernetes-deplyment"){

           steps{
             echo "deploying an aplication.."
             sh "kubectl apply -f my.yaml"
            }

       }





    }

}