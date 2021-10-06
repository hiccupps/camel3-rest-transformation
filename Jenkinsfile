pipeline {

    environment {
            registry = "abhishekkvvishnoi/my-app-image"
            registryCredential = 'docker-creds'
            dockerImage = ''
        }

    agent any

    stages{

        stage("maven-build"){

          steps{
            echo "running maven build of your code ..!!"
            sh "mvn clean install -DskipTests"
          }
        }

        stage("docker-image-build"){
            steps {
               script {
                   dockerImage = docker.build registry + ":$BUILD_NUMBER"
                        }
                   }
        }


       stage('docker-image-push') {
            steps {
                 script {
                   docker.withRegistry( '', registryCredential ) {
                       dockerImage.push()
                         }
                   }
              }
        }

       stage("docker-image-run"){

          steps{
          script {
            echo "deploying the application on docker.."
            dockerImage.run( '-p 8080:8080' )
          }
          }

      }





    }

}