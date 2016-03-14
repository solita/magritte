deliveryPipelineView('Pipeline') {
    enableManualTriggers(true)
    allowRebuild(true)
    showAggregatedPipeline(true)
    pipelines() {
        component('Hello', 'Build')
    }
}
