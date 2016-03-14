import util.Build;
import util.Pipeline;

job('DeployProd') {
    deliveryPipelineConfiguration('Prod', 'Deploy')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    steps {
        Pipeline.checkOut(delegate)
        Build.copyArtifacts(delegate)
        shell('ansible-playbook -l prod deploy.yml')
    }
}
