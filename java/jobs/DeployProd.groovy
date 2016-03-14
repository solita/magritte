import util.Build;
import util.Pipeline;

job('DeployProd') {
    deliveryPipelineConfiguration('Prod', 'Deploy')
    wrappers {
        buildName('$PIPELINE_VERSION')
    }
    Pipeline.checkOut(delegate)
    steps {
        Build.copyArtifacts(delegate)
        shell('ansible-playbook -l prod deploy.yml')
    }
}
