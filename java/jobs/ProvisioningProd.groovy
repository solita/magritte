import util.AnsibleVars;

job('ProvisionProd') {
    deliveryPipelineConfiguration('Prod Env', 'Provision')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l prod site.yml")
    }
}
