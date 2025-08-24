pipeline {
    agent any

    tools {
        // Use the tool name from "Global Tool Configuration → SonarQube Scanner installations"
        sonarQubeScanner 'sonar-scanner'
    }

    stages {
        stage('SonarQube Analysis') {
            steps {
                // Use the SonarQube server name from "Manage Jenkins → Configure System → SonarQube servers"
                withSonarQubeEnv('MySonar') {
                    sh '''
                        sonar-scanner \
                        -Dsonar.projectKey=myproject \
                        -Dsonar.sources=. \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=<your-token>
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
