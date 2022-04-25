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
                      /*  // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
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
                    */
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
        
        
        stage('Paralel'){
            parallel{

                stage('Quality gate') {
            steps {
                
                echo 'tester si le résultat passe le seuil....'
                waitForQualityGate abortPipeline: true
    
                
        }
        }

       
        
        
        stage('Deploy DEV') {
        when {
                        branch 'develop'
                    }
           

            steps {
                echo 'si le build s\'effectue sur la banche main'
                bat 'mvn deploy'
            }
    }
        stage('Test deploy dev'){
                        when {
                            branch 'develop'
                        }
                        steps{
                            echo 'Déploiment sur dev success'
                        }
             }
         stage('Deploy REC') {
                    
                    when {
                        branch 'main'
                    }
                    options {
                                    timeout(time: 10, unit: 'MINUTES')
                                }
                    steps {
                        echo 'si le build s\'effectue sur la banche release'
                        bat 'mvn deploy'
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
                    emailext   attachLog:true, body: 'Votre pipiline du projet a été lancé', subject: 'Build', to: 'ndiayeoumarsahaba@ept.sn'
            }
            success{
                    emailext   attachLog:true ,body: 'Build success', subject: 'Build', to: 'ndiayeoumarsahaba@ept.sn'
            }
            changed{
                    emailext   attachLog:true, body: 'Build changed', subject: 'Build', to: 'ndiayeoumarsahaba@ept.sn'
            }

            unstable{
                    emailext   attachLog:true, body: 'Unstable build', subject: 'Build', to: 'ndiayeoumarsahaba@ept.sn'
            }
            failure{
                    emailext   attachLog:true, body: 'Build failed', subject: 'Build', to: 'ndiayeoumarsahaba@ept.sn'
            }



         }

}




