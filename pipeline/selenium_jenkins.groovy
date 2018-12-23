import hudson.model.*;

println env.JOB_NAME
println env.BUILD_NUMBER


pipeline{

    agent any
    parameters {
        string(name: 'BROWSER_TYPE', defaultValue: 'chrome', description: 'Type a browser type, should be chrome/firefox')
        string(name: 'TEST_SERVER_URL', defaultValue: '', description: 'Type the test server url')
    }
    
	stages{
	    stage("Initialization"){
	        steps{
	            script{
	                selenium_test = load ${env.WORKSPACE} + "\\pipeline\\selenium.groovy"
	            }
	        }
	    }

	    stage("Git Checkout"){
	        steps{
	            script{
	                checkout([$class: 'GitSCM', branches: [[name: '*/master']],
						userRemoteConfigs: [[credentialsId: '6f4fa66c-eb02-46dc-a4b3-3a232be5ef6e', 
							url: 'https://github.com/QAAutomationLearn/JavaAutomationFramework.git']]])
	            }
	        }
	    }
	    
        stage("Set key value"){
	        steps{
	            script{
	                config_file = ${env.WORKSPACE} + "\\Config\\config.properties"
	                selenium_test.setKeyValue("browser", "abc123", config_file)
	            }

	        }

	    }
	    stage("Run Selenium Test"){
	        steps{
	            script{
	                println "Here will start to run selenium test."
	            }

	        }

	    }
	}









}
