# How to promote a decision service project for embedded execution with ODM

![Concept of operations](ODM-embedded-jse-exec.jpg "Concept of operations")

## Step 1 - Generate a ruleapp archive from your decision service project
### Step 1.1 - From ODM Rule Designer
In your decision service project generate the ruleapp archive.

Select your decision service project in the explorer. Right click and select the "Rule execution Server > Deploy..." item.

![Deploy a RuleApp](ODM-RuleDesigner-1.0.png "Concept of operations")

Now select the decision service operation for which you want to generate the executable RuleApp archive, and where you choose to write it.

![Deploy a RuleApp](ODM-RuleDesigner-1.1.png "Concept of operations")

![Deploy a RuleApp](ODM-RuleDesigner-1.2.png "Concept of operations")

Congratulations. Your RuleApp archive has been written. In this example under mydeployment.jar name that can be changed.

### Step 1.2 - From ODM Decision Center
Alternatively you can generate the ruleapp archive for your decision service project in Decision Center.

At the end of this step you have obtained a ruleapp archive downloaded on your file system

## Step 2 - Create a Java application to execute the decision service

### Step 2.1 - Package the ODM execution jars
Add theses librairies to load and execute a decision service compiled as a ruleapp archive:
* `${odm.install}/executionserver/lib/jrules-res-${odm.version}-execution.jar`
* `${odm.install}/executionserver/lib/jrules-engine-${odm.version}.jar`
* `${odm.install}/executionserver/lib/j2ee_connector-1_5-fr.jar`

### Step 2.2 - Package the executable object model jars
Add the eXecutable Object Model library to the classpath of the Java application. Add associated third party jars if needed.

`${basedir}/yourxom.jar`

An example of Maven pom file is available at [../simple-loan-validation-res-runner/pom.xml](pom.xml)

### Step 2.3 - Write a decision service runner
The code snipplet has a generic base. It just varies depending on the signature of the decision service operation, the level of trace that your request, and optionaly the configuration of the embedded Rule Execution Server RuleSession factory.

First add ODM includes.

```console
import ilog.rules.res.model.IlrPath;
import ilog.rules.res.session.IlrJ2SESessionFactory;
import ilog.rules.res.session.IlrSessionRequest;
import ilog.rules.res.session.IlrSessionResponse;
import ilog.rules.res.session.IlrStatelessSession;
import ilog.rules.res.session.config.IlrPersistenceType;
import ilog.rules.res.session.config.IlrSessionFactoryConfig;
import ilog.rules.res.session.config.IlrXUConfig;
```

Second create a method to initiate an embedded Rule Execution Server with an in memory persistence. In this mode the rule execution will search ruleapps in ithe classpath of the application.

```console
private static IlrJ2SESessionFactory GetRuleSessionFactory() {
		IlrSessionFactoryConfig factoryConfig = IlrJ2SESessionFactory.createDefaultConfig();
		IlrXUConfig xuConfig = factoryConfig.getXUConfig();
		xuConfig.setLogAutoFlushEnabled(true);
		xuConfig.getPersistenceConfig().setPersistenceType(IlrPersistenceType.MEMORY);
		xuConfig.getManagedXOMPersistenceConfig().setPersistenceType(IlrPersistenceType.MEMORY);
		return new IlrJ2SESessionFactory(factoryConfig);
	}
```

Third a method to automate a rule based decision
- the specified rulesetPath matches with the ruleapp/ruleset path of the ruleapp archive
- the trace configuration is tunable depending on your needs
- put all input parameters in the map for the execution
- retrieive the output parameters and the trace from the response

```console
public IlrSessionResponse execute(Borrower borrower, LoanRequest loan) {
		try {

			IlrJ2SESessionFactory sessionFactory =  GetRuleSessionFactory();

			// Creating the decision request
			IlrSessionRequest sessionRequest = sessionFactory.createRequest();
			String rulesetPath = "/loanvalidation/loan_validation_with_score_and_grade";
			sessionRequest.setRulesetPath(IlrPath.parsePath(rulesetPath));

			sessionRequest.setTraceEnabled(true);
			//sessionRequest.getTraceFilter().setInfoAllFilters(true);
			sessionRequest.getTraceFilter().setInfoRules(true);
			sessionRequest.getTraceFilter().setInfoRulesNotFired(true);
			sessionRequest.getTraceFilter().setInfoTasks(true);
			sessionRequest.getTraceFilter().setInfoTotalTasksNotExecuted(true);
			sessionRequest.getTraceFilter().setInfoExecutionEvents(true);

			Map<String, Object> inputParameters = sessionRequest
					.getInputParameters();
			inputParameters.put("loan", loan);
			inputParameters.put("borrower", borrower);

			// Creating the rule session
			IlrStatelessSession session = sessionFactory
					.createStatelessSession();

			IlrSessionResponse response = session.execute(sessionRequest);
			return response;

		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		}
		return null;
	}
```
An complete source code is available in [LoanValidationRESRunner.java](../simple-loan-validation-res-runner/src/main/java/com/ibm/decisions/loanvalidation/LoanValidationRESRunner.java)

### Step 2.4 - Package the ruleapp archive in the Java application jar
Add the ruleapp archive jar to the Java application classpath, as a regular library.

### Step 2.5 - Compile
javac commonly through a maven command
An example of Maven pom file is available at [../simple-loan-validation-res-runner/pom.xml](pom.xml)

### Step 2.6 - Run
Launch our Java application. It executes the business rules as modeled in Rule Designer and Decision Center compiled and fully packaged within your application. No need of the RES DB, HTDS or other ODM parts. You are now ready to execute at scale and maximum speed your business rules in Java. Et voila.

A full code example is available at [../simple-loan-validation-res-runner](../simple-loan-validation-res-runner)
