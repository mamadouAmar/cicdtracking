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
                bat 'mvn clean package'
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
                    
                       
                        bat "mvn install"
                        // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                        pom = readMavenPom file: "pom.xml";
                        // Find built artifact under target folder
                        filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                        // Print some info from the artifact found
                        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                        // Extract the path from the File found
                        // Extract the path from the File found
                        artifactPath = filesByGlob[0].path;
                        // Assign to a boolean response verifying If the artifact name exists
                        artifactExists = fileExists artifactPath;

                        if(artifactExists) {
                            echo "* File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
    
                            nexusArtifactUploader(
                                nexusVersion: NEXUS_VERSION,
                                protocol: NEXUS_PROTOCOL,
                                nexusUrl: NEXUS_URL,
                                groupId: pom.groupId,
                                version: pom.version,
                                repository: NEXUS_REPOSITORY,
                                credentialsId: NEXUS_CREDENTIAL_ID,
                                artifacts: [
                                    // Artifact generated such as .jar, .ear and .war files.
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: artifactPath,
                                    type: pom.packaging],
    
                                    // Lets upload the pom.xml file for additional information for Transitive dependencies
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: "pom.xml",
                                    type: "pom"]
                                ]
                            );

                            } else {
                                error "* File: ${artifactPath}, could not be found";
                            }
                    
                }
                }
        }

        
        stage('Paralel'){
            parallel{
       
        
        stage('Quality gate') {
            steps {
                echo 'analyse sonar..'
                bat 'mvn sonar:sonar -Dsonar.login=ea0ff3152d936b3f2f760068834bdd7bbc323ebc'
            }
        }
        stage('Check Quality gate') {
            steps {
                echo 'tester si le résultat passe le seuil....'
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Deploy DEV') {
            options {
                timeout(time: 1, unit: 'HOURS')
            }
            when {
                branch 'develop'
            }
            steps {
                echo 'si le build s\'effectue sur la banche main'
                bat 'mvn deploy'
            }
    }
         stage('Deploy REC') {
                    
                    when {
                        branch 'main'
                    }
                    steps {
                        echo 'si le build s\'effectue sur la banche release'
                        bat 'mvn deploy'
                    }
            }
         stage('Test deploy dev'){
                    when {
                        branch 'develop'
                    }
                    steps{
                        echo 'tester si le déploiment dans dev s\'est bien passé'
                    }
         }
         stage('Test deploy rec'){
                    when {
                        branch 'release'
                    }
                   steps{
                        echo 'tester si le déploiment dans release s\'est bien passé'
                    }
                  }
    }
        }}
         post {
            always{
                    bat 'mvn clean'
                    echo'envoyer notification'
            }
            success{
                    echo 'envoyer notification'
            }
            changed{
                    echo'envoyer notification'
            }

            unstable{
                    echo'envoyer notification'
            }
            failure{
                    echo'envoyer notification'
            }



         }

}




