pipeline {
    agent any

    stages {

        stage('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }

        stage('Uni Testes') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Sonar Analysis') {
    steps {
        script {
            def scannerHome = tool 'SONAR_SCANNER'

            withSonarQubeEnv(
                installationName: 'SONAR_LOCAL',
                credentialsId: 'sonarqube-token'
            ) {
                withEnv(["SCANNER_HOME=${scannerHome}"]) {
                    bat '''
                        @echo off
                        call "%SCANNER_HOME%\\bin\\sonar-scanner.bat" ^
                        -Dsonar.projectKey=DeployBack ^
                        -Dsonar.projectName=DeployBack ^
                        -Dsonar.sources=src/main/java ^
                        -Dsonar.java.binaries=target/classes
                        '''
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executado com sucesso!'
        }

        failure {
            echo 'Pipeline apresentou falha. Verifique o console do Jenkins.'
        }
    }
}