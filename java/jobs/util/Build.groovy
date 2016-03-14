package util;

class Build {
    static copyArtifacts(context) {
        context.with {
            copyArtifacts('Build') {
                buildSelector() {
                    upstreamBuild(true)
                }
                includePatterns('target/hello.jar', 'start', 'stop')
                flatten()
            }
        }
    }
}
