import util.AnsibleVars;

job('ProvisionCI') {
    deliveryPipelineConfiguration('CI', 'Provision')
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
