[![Build Status](https://saucelabs.com/browser-matrix/ArquivoPT.svg)](https://saucelabs.com/beta/builds/c25e444f9a6d4f819edb221b63afb720)

Recursive tests developed with selenium framework for Arquivo.pt
---------------

Functional Tests


 Generate eclipse .project and .classpath files, similar for idea.
```bash
 mvn eclipse:clean eclipse:eclipse
```

Execute the tests
```bash
 mvn clean verify -Dit.test=pt.fccn.arquivo.tests.AllTests -Dtest.url=https://preprod.arquivo.pt -Dremote.saucelabs.user=xxxx -Dremote.saucelabs.key=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx -Dtest.resolution=1280x1024
```

To debug tests add the argument:
```bash
-Dmaven.failsafe.debug
```

To run the test collection that don't use saucelabs run:
```bash
 mvn clean verify -Dit.test=pt.fccn.arquivo.tests.TestCollections
```

