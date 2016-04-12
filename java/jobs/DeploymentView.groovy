deliveryPipelineView('Deployment Pipeline') {
    enableManualTriggers(true)
    allowRebuild(true)
    showAggregatedPipeline(true)
    pipelines {
        component('Deployment', 'Deployment/CI/Build')
    }
}
