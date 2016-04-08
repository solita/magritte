import util.AnsibleVars;

job('ProdProvision') {
    deliveryPipelineConfiguration('Prod Env', 'Provision')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l prod site.yml")
    }
}
