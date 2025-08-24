pipeline {
    agent any

    tools {
        sonarRunner 'sonar-scanner'   // <-- use the short name, not the full class
    }

    stages {
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonar') {  // 'MySonar' = name of your SonarQube server in Jenkins config
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
