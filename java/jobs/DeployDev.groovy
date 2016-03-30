import util.Pipeline;

job('DeployDev') {
    deliveryPipelineConfiguration('Dev', 'Deploy')
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
        shell('ansible-playbook -l dev deploy.yml')
    }
    publishers {
        buildPipelineTrigger('DeployTest')
    }
}