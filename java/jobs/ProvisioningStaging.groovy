import util.AnsibleVars;

job('StagingProvision') {
    deliveryPipelineConfiguration('Staging Env', 'Provision')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l staging site.yml")
    }
    publishers {
        buildPipelineTrigger('ProdProvision')
    }
}
