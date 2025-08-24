pipeline {
    agent any

    tools {
        SonarQubeScanner 'sonar-scanner'   // <-- must match the name you configured in Jenkins
    }

    stages {
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonar') {   // <-- must match your SonarQube server config name
                    bat "sonar-scanner -Dsonar.projectKey=myproject -Dsonar.sources=."
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
