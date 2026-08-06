pipeline {

    parameters {
        string(name: 'CHROME_SCALE', defaultValue: '4', description: 'Chrome node count')
        string(name: 'FIREFOX_SCALE', defaultValue: '3', description: 'Firefox node count')
        string(name: 'EDGE_SCALE', defaultValue: '2', description: 'Edge node count')
    }

    agent any

    stages {

        stage('Start & Scale Grid') {
            steps {
                bat """
                docker compose down --remove-orphans

                docker compose up -d selenium-hub chrome firefox edge

                docker compose scale chrome=%CHROME_SCALE%
                docker compose scale firefox=%FIREFOX_SCALE%
                docker compose scale edge=%EDGE_SCALE%
                """
            }
        }

        stage('Parallel Browsers') {
            parallel {

                stage('Chrome') {
                    steps {
                        bat """
                        set BROWSER=chrome
                        set REPORT_NAME=ChromeReport
                        mvn test -Dbrowser=chrome -DreportName=ChromeReport
                        """
                    }
                }

                stage('Firefox') {
                    steps {
                        bat """
                        set BROWSER=firefox
                        set REPORT_NAME=FirefoxReport
                        mvn test -Dbrowser=firefox -DreportName=FirefoxReport
                        """
                    }
                }

                stage('Edge') {
                    steps {
                        bat """
                        set BROWSER=edge
                        set REPORT_NAME=EdgeReport
                        mvn test -Dbrowser=edge -DreportName=EdgeReport
                        """
                    }
                }

            }
        }
    }

    post {
        always {
            bat "docker compose down --remove-orphans || true"
            archiveArtifacts artifacts: 'test-output/**/*'
            archiveArtifacts artifacts: 'screenshots/**/*'
            archiveArtifacts artifacts: 'combined-report/**/*'
        }
    }
}
