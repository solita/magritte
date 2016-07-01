import util.Pipeline;
import util.AnsibleVars;

folder('Provisioning')
folder('Provisioning/Build')

job('Provisioning/Build/Checkout') {
    deliveryPipelineConfiguration('Build Env', 'Checkout')
    wrappers {
        deliveryPipelineVersion('checkout #$BUILD_NUMBER', true)
        timestamps()
    }
    Pipeline.checkOut(delegate)
    publishers {
        archiveArtifacts {
            pattern('**/*')
            onlyIfSuccessful()
        }
        downstream('Provisioning/Build/Provision', 'SUCCESS')
    }
}

job('Provisioning/Build/Provision') {
    deliveryPipelineConfiguration('Build Env', 'Provision')
    quietPeriod(0)
    wrappers {
        buildName('$PIPELINE_VERSION')
        timestamps()
    }
    steps {
        copyArtifacts('Provisioning/Build/Checkout') {
            buildSelector() {
                upstreamBuild(true)
            }
            includePatterns('**/*')
        }
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_ROOT}/build/inventory' site.yml")
    }
    publishers {
        archiveArtifacts {
            pattern('imagination/jenkins_id_rsa.pub')
            onlyIfSuccessful()
        }
        buildPipelineTrigger('Provisioning/QA/Provision')
    }
}
