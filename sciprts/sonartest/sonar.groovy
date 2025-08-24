pipeline {
    agent any

    tools {
        // Use the name you configured in "Global Tool Configuration"
        sonarQubeScanner 'MySonar'
    }

    stages {
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarscanner') {  // name from Manage Jenkins > Configure System
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
