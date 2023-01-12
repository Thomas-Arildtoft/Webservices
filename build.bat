
pushd utils
call build.bat
popd

pushd account-management
call build.bat
popd 

pushd customer
call build.bat
popd

pushd merchant
call build.bat
popd

pushd payment-management
call build.bat
popd

pushd token-management
call build.bat
popd


