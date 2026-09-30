pipeline{
    agent any
    stages{
        stage('Build Backend'){
            steps{
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Uni Testes'){
			steps{
				bat 'mvn test'
			}
		stage('Sonar Analysis'){
			environment{
				scannerHome = tool 'SONAR_SCANNER'
			}
			steps{
				withSonarQubeEnv('SONAR_LOCAL'){
				bat '${scannerHome}/bin/sonar-scanner -e   -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_cf527c0b7d83971c5dd608046206e31275ef8944 -Dsonar.java.binaries=target'
			}
			}
		}
        }
   
    }
}
