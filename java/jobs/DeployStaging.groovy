import util.Build;
import util.Pipeline;

job('DeployStaging') {
    deliveryPipelineConfiguration('Staging', 'Deploy')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    Pipeline.checkOut(delegate)
    steps {
        Build.copyArtifacts(delegate)
        shell('ansible-playbook -l staging deploy.yml')
    }
    publishers {
        buildPipelineTrigger('DeployProd')
    }
}
