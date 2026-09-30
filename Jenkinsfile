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
        
        stage('Deploy Backend'){
			steps{
				deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
			}
		}
		stage('API Test'){
			steps{
				git branch: 'main', url: 'https://github.com/danmsj/tasksApiTest'
				bat 'mvn clean package -DskipTests=true'
			}
		}
		stage('Deploy Frontend'){
			steps{
				git branch: 'master', url: 'https://github.com/danmsj/tasks-frontend'
				bat 'mvn clean package'
				deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-frontend', war: 'target/tasks-frontend.war'
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

