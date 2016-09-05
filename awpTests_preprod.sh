#!/bin/bash
export DISPLAY=:99 # Display port number of Xvfb
cd /root/functional-tests/
plataResult=`/usr/bin/ant test -Dtestcase=pt.fccn.arquivo.tests.AllTests -Dtest.url=http://preprod.arquivo.pt  | grep "Failures: 0, Errors: 0"`

if [ -z "$plataResult" ]; then
	echo "TESTS FAIL"
else 
	echo "TESTS OK"
fi

echo "Result: $plataResult"
