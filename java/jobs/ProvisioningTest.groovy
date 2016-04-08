import util.AnsibleVars;

job('ProvisionTest') {
    deliveryPipelineConfiguration('Test Env', 'Provision')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l test site.yml")
    }
    publishers {
        buildPipelineTrigger('ProvisionStaging')
    }
}
