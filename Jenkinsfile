pipeline {
    agent any
    stages {
        stage('Verificar Repositorio') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']],
                          userRemoteConfigs: [
                                [
                                    url: 'https://github.com/ecalazaes/emissor-service.git',
                                    credentialsId: 'emissor-service']]])
            }
        }

        stage('SonarQube analysis') {
            steps {
                script {
                    withSonarQubeEnv('sq1') {
                        bat """
                            mvn clean verify sonar:sonar ^
                                -Dsonar.projectKey=emissor-service  ^
                                -Dsonar.projectName="emissor-service"
                        """
                    }
                }
            }
        }

        stage('Construir Imagem Docker') {
            steps {
                script {
                    def appName = 'emissor-service'
                    def imageTag = "${appName}:${env.BUILD_ID}"
                    bat "docker build ${imageTag} ."
                }
            }
        }

        stage('Fazer Deploy') {
            steps {
                script {
                    def appName = 'emissor-service'
                    def imageTag = "${appName}:${env.BUILD_ID}"
                    bat "docker-compose up -d --build"
                }
            }
        }
    }

    post {
        success {
            echo 'Deploy realizado com sucesso!'
        }
        failure {
            echo 'Houve um erro durante o deploy.'
        }
    }
}