import util.Build;
import util.Pipeline;

job('DeployDev') {
    deliveryPipelineConfiguration('Dev', 'Deploy')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    steps {
        Pipeline.checkOut(delegate)
        Build.copyArtifacts(delegate)
        shell('ansible-playbook -l dev deploy.yml')
    }
    publishers {
        buildPipelineTrigger('DeployTest')
    }
}
