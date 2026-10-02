pipeline {
    agent any

    stages {

        stage('DeployBack) {
            steps {
                dir('deploy-backend') {
                    git branch: 'master',
                        url: 'https://github.com/danmsj/tasks-backend'

                    bat 'mvn clean test'
                }
            }
        }
        stage('Sonar Analysis Backend') {
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

        stage('Quality Gate Backend') {
            steps {
                    script {
                        def qualityGate = waitForQualityGate()

                        if (qualityGate.status != 'OK') {
                            error "Quality Gate reprovado: ${qualityGate.status}"
                        }
                }
            }
        }
        stage('DeployFrontend') {
            steps {
                dir('deploy-frontend') {
                    git branch: 'master',
                        url: 'https://github.com/danmsj/tasks-frontend'

                    bat 'mvn clean'
                }
            }
        }
        stage('Sonar Analysis Frontend') {
            steps {
                dir('frontend-test') {
                    script {
                        def scannerHome = tool 'SONAR_SCANNER'

                        withSonarQubeEnv('SONAR_LOCAL') {
                            withEnv(["SCANNER_HOME=${scannerHome}"]) {
                                bat '''
                                    @echo off
                                    call "%SCANNER_HOME%\\bin\\sonar-scanner.bat" ^
                                    -Dsonar.projectKey=DeployFront ^
                                    -Dsonar.projectName=DeployFront ^
                                    -Dsonar.sources=src/main/java ^
                                    -Dsonar.java.binaries=target/classes ^
                            
                                '''
                            }
                        }
                    }
                }
            }
        }

        stage('Quality Gate Frontend') {
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