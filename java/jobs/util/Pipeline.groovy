package util;

class Pipeline {
    static checkOut(context) {
        context.with {
            // Don't fail the build even if rsync fails (it's probably because some
            // files are missing the o+r permission).
            shell('rsync -av /project/* . || true')
        }
    }
}
