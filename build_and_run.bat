call build.bat
call docker image prune -f

pushd end-to-end-tests
call deploy.bat 
timeout /t 5
call test.bat
popd

call docker image prune -f