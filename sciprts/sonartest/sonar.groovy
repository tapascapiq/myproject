pipeline {
  agent any
  stages {
    stage('Check Sonar Scanner') {
      steps {
        script {
          def scannerHome = tool 'sonar-scanner'
          bat "${scannerHome}\\bin\\sonar-scanner -v"
        }
      }
    }
  }
}
