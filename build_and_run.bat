call build.bat
call docker image prune -f

call deploy.bat 
timeout /t 5

pushd end-to-end-tests
call test.bat
popd

call docker image prune -f