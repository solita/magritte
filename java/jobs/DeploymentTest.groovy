import util.AnsibleVars;
import util.Pipeline;

job('TestDeploy') {
    deliveryPipelineConfiguration('Test Env', 'Deploy')
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
        buildPipelineTrigger('StagingDeploy')
    }
}
