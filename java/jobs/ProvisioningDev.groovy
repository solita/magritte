import util.AnsibleVars;

job('ProvisionDev') {
    deliveryPipelineConfiguration('Dev Env', 'Provision')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l dev site.yml")
    }
    publishers {
        buildPipelineTrigger('ProvisionTest')
    }
}
