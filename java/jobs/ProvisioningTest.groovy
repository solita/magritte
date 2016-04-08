import util.AnsibleVars;

job('TestProvision') {
    deliveryPipelineConfiguration('Test Env', 'Provision')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l test site.yml")
    }
    publishers {
        buildPipelineTrigger('StagingProvision')
    }
}
