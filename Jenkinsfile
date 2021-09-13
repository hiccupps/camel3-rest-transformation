pipeline {

    agent any

    stages{

        stage("build"){

          steps{
            echo "building an aplication.."
            sh "mvn clean install -DskipTests"
          }

        }

           stage("test"){

          steps{
           echo "testing an aplication.."
          }

        }



       stage("deploy"){

          steps{
           echo "deploying an aplication.."
          }

        }





    }

}