import util.AnsibleVars;
import util.Pipeline;

folder('Deployment')
folder('Deployment/Test')

job('Deployment/Test/Deploy') {
    deliveryPipelineConfiguration('Test Env', 'Deploy')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    Pipeline.checkOut(delegate)
    steps {
        copyArtifacts('Deployment/CI/Build') {
            buildSelector() {
                upstreamBuild(true)
            }
            includePatterns('**/*')
            flatten()
        }
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l test deploy.yml")
    }
    publishers {
        buildPipelineTrigger('Deployment/Staging/Deploy')
    }
}
