import util.AnsibleVars;
import util.Pipeline;

job('DeployTest') {
    deliveryPipelineConfiguration('Test', 'Deploy')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l test deploy.yml")
    }
    publishers {
        buildPipelineTrigger('DeployStaging')
    }
}
