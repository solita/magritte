import util.AnsibleVars;

job('ProvisionStaging') {
    deliveryPipelineConfiguration('Staging Env', 'Provision')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l staging site.yml")
    }
    publishers {
        buildPipelineTrigger('ProvisionProd')
    }
}
