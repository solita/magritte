import util.AnsibleVars;
import util.Pipeline;

job('CIBuild') {
    deliveryPipelineConfiguration('CI Env', 'Build')
    wrappers {
        deliveryPipelineVersion('build #$BUILD_NUMBER', true)
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
        downstream('CIDeploy', 'SUCCESS')
    }
}

job('CIDeploy') {
    deliveryPipelineConfiguration('CI Env', 'Deploy')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    Pipeline.checkOut(delegate)
    steps {
        copyArtifacts('CIBuild') {
            buildSelector() {
                upstreamBuild(true)
            }
            includePatterns('**/*')
            flatten()
        }
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l ci deploy.yml")
    }
    publishers {
        buildPipelineTrigger('TestDeploy')
    }
}
