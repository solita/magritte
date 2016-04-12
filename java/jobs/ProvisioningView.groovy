deliveryPipelineView('Provisioning Pipeline') {
    pipelineInstances(5)
    enableManualTriggers(true)
    showAggregatedPipeline(true)
    allowRebuild(true)
    pipelines {
        component('Provisioning', 'Provisioning/CI/Checkout')
    }
}
