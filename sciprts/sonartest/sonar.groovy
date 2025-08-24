node {
    stage('SonarQube Analysis') {
        // This must match the "Name" you gave in "Global Tool Configuration"
        def scannerHome = tool name: 'sonar-scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'

        withSonarQubeEnv('MySonar') {   // <-- matches the SonarQube server name you set in Jenkins
            bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=myproject -Dsonar.sources=."
        }
    }

    stage('Quality Gate') {
        timeout(time: 1, unit: 'MINUTES') {
            waitForQualityGate abortPipeline: true
        }
    }
}
