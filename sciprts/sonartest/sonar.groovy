pipeline {
    agent any

    tools {
        // syntax: <toolType> '<toolName>'
        // the toolType for SonarQube is "hudson.plugins.sonar.SonarRunnerInstallation"
        // but in declarative pipeline you simply write: "SonarQubeScanner"
        SonarQubeScanner 'sonar-scanner'
    }

    stages {
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonar') {
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
