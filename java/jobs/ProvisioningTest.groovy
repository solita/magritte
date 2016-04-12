import util.AnsibleVars;

folder('Provisioning')
folder('Provisioning/Test')

job('Provisioning/Test/Provision') {
    deliveryPipelineConfiguration('Test Env', 'Provision')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l test site.yml")
    }
    publishers {
        buildPipelineTrigger('Provisioning/Staging/Provision')
    }
}
