pipeline {
    agent any

    environment {
        SONAR_SERVER = 'SonarQube' // Must match Jenkins SonarQube config name
        SONAR_PROJECT_KEY = 'com.example:java-webapp-cicd'
        GITHUB_CREDENTIALS = 'github-token' // Jenkins credential ID
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
                sh 'mvn test'
            }
        }

        stage('SonarQube Scan') {
            steps {
                withSonarQubeEnv("${SONAR_SERVER}") {
                    sh "mvn sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY}"
                }
            }
        }

        stage('SonarQube Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            script {
                def status = currentBuild.currentResult
                githubNotify context: 'ci/jenkins', status: status.toLowerCase()
            }
        }
    }
}
