pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/tapascapiq/myproject.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building the project..."
                // put your build commands here, e.g.:
                // sh 'mvn clean install'   (for Java+Maven)
                // npm install && npm run build   (for Node.js)
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                // add test commands here
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying..."
                // add deploy steps here
            }
        }
    }
}
