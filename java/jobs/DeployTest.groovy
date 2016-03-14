import util.Build;
import util.Pipeline;

job('DeployTest') {
    deliveryPipelineConfiguration('Test', 'Deploy')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    steps {
        Pipeline.checkOut(delegate)
        Build.copyArtifacts(delegate)
        shell('ansible-playbook -l test deploy.yml')
    }
    publishers {
        buildPipelineTrigger('DeployStaging')
    }
}
