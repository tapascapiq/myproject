pipeline {
    agent any
    tools {
        sonarQubeScanner 'sonar-scanner'
    }
    stages {
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonar') {  // <-- SonarQube server name you set in Jenkins
                    bat """
                        ${tool 'sonar-scanner'}\\bin\\sonar-scanner.bat ^
                        -Dsonar.projectKey=myproject ^
                        -Dsonar.sources=. ^
                        -Dsonar.host.url=http://localhost:9000 ^
                        -Dsonar.login=YOUR_TOKEN
                    """
                }
            }
        }
    }
}
