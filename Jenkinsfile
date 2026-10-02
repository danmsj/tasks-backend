pipeline {
    agent any

    stages {

        stage('Backend Test') {
            steps {
                dir('backend-test') {
                    git branch: 'master',
                        url: 'https://github.com/danmsj/tasks-backend'

                    bat 'mvn clean test'
                }
            }
        }
        stage('Sonar Analysis') {
            steps {
                dir('backend-test') {
                    script {
                        def scannerHome = tool 'SONAR_SCANNER'

                        withSonarQubeEnv('SONAR_LOCAL') {
                            withEnv(["SCANNER_HOME=${scannerHome}"]) {
                                bat '''
                                    @echo off
                                    call "%SCANNER_HOME%\\bin\\sonar-scanner.bat" ^
                                    -Dsonar.projectKey=DeployBack ^
                                    -Dsonar.projectName=DeployBack ^
                                    -Dsonar.sources=src/main/java ^
                                    -Dsonar.java.binaries=target/classes ^
                                    -Dsonar.tests=src/test/java
                                '''
                            }
                        }
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                    script {
                        def qualityGate = waitForQualityGate()

                        if (qualityGate.status != 'OK') {
                            error "Quality Gate reprovado: ${qualityGate.status}"
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

        always {
            echo 'Pipeline finalizado.'
        }
    }
}