pushd utils
call mvn clean
popd 

pushd account-management
call mvn clean
popd 

pushd token-management
call mvn clean
popd

pushd payment-management
call mvn clean
popd 

pushd customer
call mvn clean
popd 

pushd merchant
call mvn clean
popd


