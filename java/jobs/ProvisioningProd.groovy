import util.AnsibleVars;

folder('Provisioning')
folder('Provisioning/Prod')

job('Provisioning/Prod/Provision') {
    deliveryPipelineConfiguration('Prod Env', 'Provision')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    steps {
        copyArtifacts('Provisioning/CI/Checkout') {
            buildSelector() {
                upstreamBuild(true)
            }
            includePatterns('**/*')
        }
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l prod site.yml")
    }
}
