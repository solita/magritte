job('Build') {
    deliveryPipelineConfiguration('Build', 'Build')
    wrappers {
        deliveryPipelineVersion('build $BUILD_NUMBER', true)
    }
    scm {
        git {
            remote {
                url('https://github.com/noidi/hello-java.git')
            }
            branch('master')
            clean()
        }
    }
    triggers {
        scm('*/15 * * * *')
    }
    steps {
        shell('echo $BUILD_NUMBER > src/main/resources/build.txt')
        shell('mvn package')
        shell('mvn verify')
    }
    publishers {
        archiveJunit('target/failsafe-reports/*.xml')
        archiveArtifacts {
            pattern('target/hello.jar')
            pattern('start')
            pattern('stop')
            onlyIfSuccessful()
        }
        downstream('DeployDev', 'SUCCESS')
    }
}
