job('build') {
    scm {
        git {
            remote {
                url('https://github.com/noidi/hello-java.git')
            }
            clean()
        }
    }
    triggers {
        scm('*/15 * * * *')
    }
    steps {
        shell('mvn package')
        shell('mvn verify')
    }
    publishers {
        archiveJunit('target/failsafe-reports/*.xml')
        archiveArtifacts {
            pattern('target/hello.jar')
            onlyIfSuccessful()
        }
        downstream('deploy', 'SUCCESS')
    }
}

job('deploy') {
    steps {
        copyArtifacts('build') {
            buildSelector() {
                upstreamBuild(fallback=true)
            }
            includePatterns('target/hello.jar')
            flatten()
        }
    }
}
