import util.Pipeline;

job('DeployStaging') {
    deliveryPipelineConfiguration('Staging', 'Deploy')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    Pipeline.checkOut(delegate)
    steps {
        copyArtifacts('Build') {
            buildSelector() {
                upstreamBuild(true)
            }
            includePatterns('**/*')
            flatten()
        }
        shell('ansible-playbook -l staging deploy.yml')
    }
    publishers {
        buildPipelineTrigger('DeployProd')
    }
}
