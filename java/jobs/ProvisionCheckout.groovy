import util.Pipeline;

job('ProvisionCheckout') {
    deliveryPipelineConfiguration('Checkout', 'Checkout')
    wrappers {
        deliveryPipelineVersion('prov #$BUILD_NUMBER', true)
    }
    Pipeline.checkOut(delegate)
    publishers {
        archiveArtifacts {
            pattern('**/*')
            onlyIfSuccessful()
        }
        downstream('ProvisionCI', 'SUCCESS')
    }
}
