job('Build') {
    scm {
        git('https://github.com/noidi/hello-java.git')
    }
    triggers {
        scm('*/15 * * * *')
    }
    steps {
        shell('./test.sh')
    }
}
