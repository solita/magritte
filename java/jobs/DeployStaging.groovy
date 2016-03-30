import util.AnsibleVars;
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l staging deploy.yml")
    }
    publishers {
        buildPipelineTrigger('DeployProd')
    }
}
