pipeline {
    agent any

    tools {

        maven 'Maven'
        jdk 'java11'
    }

    environment {
        PATH = "C:\\WINDOWS\\SYSTEM32"
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "localhost:8081"
        NEXUS_REPOSITORY = "maven-releases"
        NEXUS_CREDENTIAL_ID = "nexuscred"
    }

    


    stages {

        stage('Build') {
            steps {
                echo 'building..'
                bat 'mvn clean package -DskipTests'
            }

       }

       stage('Test') {
            steps {
                echo 'testing..'
                bat 'mvn test'
            }

       }
        
         stage('Install on nexus') {
            
            steps {
                    echo 'initialise..'
                    

                    script {
                    
                       
                        bat "mvn package -DskipTests"

                        nexusArtifactUploader artifacts: [[artifactId: 'tracking', classifier: '', file: 'target/tracking.war', type: 'war']], credentialsId: 'nexus', groupId: 'sn.ept.git.seminaire.cicd', nexusUrl: 'localhost:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'maven-releases', version: '0..0.1'
                     
                }
                }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                bat 'mvn sonar:sonar -Dsonar.login=ea0ff3152d936b3f2f760068834bdd7bbc323ebc'
            }
            }
        }
        
        
        


       
        
        
        stage('Deploy DEV') {
        when {
                        branch 'develop'
                    }
           

            steps{
                deploy adapters: [tomcat9(credentialsId: 'tomcatjenkins', path: '', url: 'http://localhost:8083')], contextPath: 'tracking-dev', war: '**/*.war'

            }
    }
        stage('Test deploy dev'){
                        when {
                            branch 'develop'
                        }
                        
                         steps{
                script{
                    echo "tester si le deploiement s'est bien passe"
                    sleep(time:1,unit:"MINUTES") 
                    echo "Run test"
                   // final String url = "http://localhost:8083/tracking-dev/"
                    //final String response = bat(script: "curl -s $url", returnStdout: true).trim()
                    //echo response
                    
                }

                
            }
             }
         stage('Deploy REC') {
                    
                    when {
                        branch 'release'
                    }
                    options {
                                    timeout(time: 10, unit: 'MINUTES')
                                }
                     steps{
                deploy adapters: [tomcat9(credentialsId: 'tomcatjenkins', path: '', url: 'http://localhost:8083')], contextPath: 'tracking-rec', war: '**/*.war'

            }
            }

         stage('Test deploy rec'){
                    when {
                        branch 'release'
                    }
                   steps{
                echo "tester si le deploiement s'est bien passe"
                sleep(time:1,unit:"MINUTES") 
                echo "Run test"
                script {
                    final String url = "http://localhost:8083/tracking-rec/"

                    final String response = bat(script: "curl -s $url", returnStdout: true).trim()

                    echo response
                
                }
                
            }
                  }

                  
                stage('Quality gate') {
            steps {
                
                echo 'tester si le résultat passe le seuil....'
                waitForQualityGate abortPipeline: true
    
                
        }
        }
    
        }
         post {
            always{
                    bat 'mvn clean'
                    emailext   attachLog:true, body: 'Votre pipeline du projet a été lancé', subject: 'Build', to: 'dsokhnadiarra@ept.sn'
            }
            success{
                    emailext   attachLog:true ,body: 'Build success', subject: 'Build', to: 'dsokhnadiarra@ept.sn'
            }
            changed{
                    emailext   attachLog:true, body: 'Build changed', subject: 'Build', to: 'dsokhnadiarra@ept.sn'
            }

            unstable{
                    emailext   attachLog:true, body: 'Unstable build', subject: 'Build', to: 'dsokhnadiarra@ept.sn'
            }
            failure{
                    emailext   attachLog:true, body: 'Build failed', subject: 'Build', to: 'dsokhnadiarra@ept.sn'
            }



         }

}




