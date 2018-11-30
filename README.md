[![Build Status](https://saucelabs.com/browser-matrix/ArquivoPT.svg)](https://saucelabs.com/beta/builds/c25e444f9a6d4f819edb221b63afb720)
Recursive tests developed with selenium framework for Arquivo.pt
---------------

Functional Tests

To use the remote tests on sauce labs use:
1) Download Sauce Connect Proxy
Choose the right one on:
https://wiki.saucelabs.com/display/DOCS/Sauce+Connect+Proxy#app-switcher

2) Extract, install its service or execute it directly
./sc --user xxxxx --api-key xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx

3) Start the tests
ant test -Dtestcase=pt.fccn.arquivo.tests.AllTests -Dtest.url=https://xxxxxx.pt -Dremote.saucelabs.user=xxxx -Dremote.saucelabs.key=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx -Dtest.resolution=1280x1024