import util.Pipeline;
import util.AnsibleVars;

job('CICheckoutPipeline') {
    deliveryPipelineConfiguration('CI Env', 'Checkout')
    wrappers {
        deliveryPipelineVersion('checkout #$BUILD_NUMBER', true)
    }
    Pipeline.checkOut(delegate)
    publishers {
        archiveArtifacts {
            pattern('**/*')
            onlyIfSuccessful()
        }
        downstream('CIProvision', 'SUCCESS')
    }
}

job('CIProvision') {
    deliveryPipelineConfiguration('CI Env', 'Provision')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    steps {
        copyArtifacts('CICheckoutPipeline') {
            buildSelector() {
                upstreamBuild(true)
            }
            includePatterns('**/*')
        }
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l ci site.yml")
    }
    publishers {
        buildPipelineTrigger('TestProvision')
    }
}
