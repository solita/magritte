import util.AnsibleVars;
import util.Pipeline;

folder('Deployment')
folder('Deployment/QA')

job('Deployment/QA/Deploy') {
    deliveryPipelineConfiguration('QA Env', 'Deploy')
    quietPeriod(0)
    wrappers {
        buildName('$PIPELINE_VERSION')
        timestamps()
        preBuildCleanup()
        colorizeOutput()
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
        shell("env ANSIBLE_FORCE_COLOR=true ansible-playbook -i '${AnsibleVars.INVENTORY_ROOT}/qa/inventory' deploy.yml")
    }
    publishers {
        buildPipelineTrigger('Deployment/Prod/Deploy')
    }
}
