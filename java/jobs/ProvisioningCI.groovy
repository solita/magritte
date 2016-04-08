import util.Pipeline;
import util.AnsibleVars;

job('ProvisionCheckout') {
    deliveryPipelineConfiguration('CI Env', 'Checkout')
    wrappers {
        deliveryPipelineVersion('prov #$BUILD_NUMBER', true)
    }
    Pipeline.checkOut(delegate)
    publishers {
        archiveArtifacts {
            pattern('**/*')
            onlyIfSuccessful()
        }
        downstream('ProvisionCI', 'SUCCESS')
    }
}

job('ProvisionCI') {
    deliveryPipelineConfiguration('CI Env', 'Provision')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    steps {
        copyArtifacts('ProvisionCheckout') {
            buildSelector() {
                upstreamBuild(true)
            }
            includePatterns('**/*')
        }
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l ci site.yml")
    }
    publishers {
        downstream('ProvisionDev', 'SUCCESS')
    }
}
