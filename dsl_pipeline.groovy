folder('PIPELINE_JOB/DEVOPSPROJECT') {
        description('hpsim project foloder created')
}
freeStyleJob('PIPELINE_JOB/DEVOPSPROJECT/compile') {
    logRotator(-1, 10)
    scm {
        github('ShaikShaik/myweb', 'master')
    }
    steps {
        maven('clean compile')
    }
    publishers {
        downstream('PIPELINE_JOB/DEVOPSPROJECT/test', 'SUCCESS')
    }
}
mavenJob('PIPELINE_JOB/DEVOPSPROJECT/test') {
    logRotator(-1, 10)
     scm {
        github('ShaikShaik/myweb', 'master')
    }
    goals('clean test')
   
   publishers {
        downstream('PIPELINE_JOB/DEVOPSPROJECT/sonar', 'SUCCESS')
    }
}
mavenJob('PIPELINE_JOB/DEVOPSPROJECT/sonar') {
    logRotator(-1, 10)
     scm {
        github('ShaikShaik/myweb', 'master')
    }
    goals('clean sonar:sonar')
  publishers {
        downstream('PIPELINE_JOB/DEVOPSPROJECT/nexus', 'SUCCESS')
    }
}
mavenJob('PIPELINE_JOB/DEVOPSPROJECT/nexus') {
    logRotator(-1, 10)
     scm {
        github('ShaikShaik/myweb', 'master')
    }
    goals('clean deploy')
}
buildPipelineView('PIPELINE_JOB/DEVOPSPROJECT/build-pipeline') {
    filterBuildQueue()
    filterExecutors()
    
    displayedBuilds(5)
    selectedJob('PIPELINE_JOB/DEVOPSPROJECT/compile')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}
