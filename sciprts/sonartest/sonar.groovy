pipeline {
    agent any

    tools {
        // Only if you want Jenkins to use installed tools
        // sonarQubeScanner 'SonarScanner'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/tapascapiq/myproject.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building the project..."
                // add build commands
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonar') { // 'MySonar' = name you gave in Jenkins config
                    sh """
                        sonar-scanner \
                          -Dsonar.projectKey=myproject \
                          -Dsonar.sources=. \
                          -Dsonar.host.url=http://localhost:9000 \
                          -Dsonar.login=$SONAR_AUTH_TOKEN
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') { // waits until analysis is done
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying..."
            }
        }
    }
}
