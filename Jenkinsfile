#!/usr/bin/env groovy
node {
    if (env.jobType == "pipeline") {
        echo 'Pipeline steps called'
    } else if (env.jobType == "production") {
        echo 'Production steps called'
    } else {
        echo 'Pull Request steps called'
        stage("PR Build") {
            sh "echo call maven clean install here"
            sh "exit 0"
//            sh "echo Maven Version && /usr/share/maven/bin/mvn --version"
            //sh "echo Java Version && java -version"
        }
    }
}
