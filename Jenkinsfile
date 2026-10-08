pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'abdullah22hel/mallplanner'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn -B clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn -B test -Dtest=!*DAOTest -DfailIfNoSpecifiedTests=false'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Coverage') {
            steps {
                recordCoverage(
                    tools: [[parser: 'JACOCO', pattern: 'target/site/jacoco/jacoco.xml']]
                )
            }
        }

        stage('Package') {
            steps {
                bat 'mvn -B package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                bat "docker build -t %DOCKER_IMAGE%:%BUILD_NUMBER% -t %DOCKER_IMAGE%:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat 'echo %DOCKER_PASS%| docker login -u %DOCKER_USER% --password-stdin'
                    bat "docker push %DOCKER_IMAGE%:%BUILD_NUMBER%"
                    bat "docker push %DOCKER_IMAGE%:latest"
                }
            }
        }
    }

    post {
        success {
            echo 'Build succeeded.'
        }
        failure {
            echo 'Build failed.'
        }
        always {
            bat 'docker logout'
        }
    }
}