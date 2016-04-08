import util.AnsibleVars;
import util.Pipeline;

job('DevDeploy') {
    deliveryPipelineConfiguration('Dev Env', 'Deploy')
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
        shell("ansible-playbook -i '${AnsibleVars.INVENTORY_FILE}' -l dev deploy.yml")
    }
    publishers {
        buildPipelineTrigger('TestDeploy')
    }
}
