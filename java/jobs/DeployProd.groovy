import util.Pipeline;

job('DeployProd') {
    deliveryPipelineConfiguration('Prod', 'Deploy')
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
        shell('ansible-playbook -l prod deploy.yml')
    }
}
