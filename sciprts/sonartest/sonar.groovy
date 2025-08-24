pipeline {
    agent any

    tools {
        // This name must match what you configured in Jenkins Tools → SonarQube Scanner
        sonarScanner 'sonar-scanner'
    }

    environment {
        // This must match the name of your SonarQube server config in Jenkins
        SONARQUBE = 'MySonar'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/tapascapiq/myproject.git'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${env.SONARQUBE}") {
                    // Run SonarScanner
                    bat "sonar-scanner -Dsonar.projectKey=myproject -Dsonar.sources=."
                }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
