import util.AnsibleVars;
import util.Pipeline;

job('StagingDeploy') {
    deliveryPipelineConfiguration('Staging Env', 'Deploy')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    Pipeline.checkOut(delegate)
    steps {
        copyArtifacts('CIBuild') {
            buildSelector() {
                upstreamBuild(true)
            }
            includePatterns('**/*')
            flatten()
        }
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l staging deploy.yml")
    }
    publishers {
        buildPipelineTrigger('ProdDeploy')
    }
}
