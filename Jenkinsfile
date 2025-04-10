pipeline {
    agent any

    environment {
        SONAR_SERVER = 'sonarserver' // Must match Jenkins SonarQube config name
        SONAR_PROJECT_KEY = 'project-2'
        GITHUB_CREDENTIALS = 'githubtoken' // Jenkins credential ID
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
                timeout(time: 25, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        success {
            echo '🎉 Build succeeded!'
            githubNotify context: 'CI/CD Pipeline', status: 'SUCCESS', description: 'Build passed', targetUrl: "${env.BUILD_URL}"
        }

        failure {
            echo '❌ Build failed!'
            githubNotify context: 'CI/CD Pipeline', status: 'FAILURE', description: 'Build failed', targetUrl: "${env.BUILD_URL}"
        }

        aborted {
            echo '⚠️ Build aborted!'
            githubNotify context: 'CI/CD Pipeline', status: 'ERROR', description: 'Build aborted', targetUrl: "${env.BUILD_URL}"
        }
    }
}