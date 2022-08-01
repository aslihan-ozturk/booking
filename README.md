# booking

booking is a web API for storing and managing clinic operations.

I used h2 embedded database to store data. Tables and records are created automatically during initialization of project. (data.sql and schema.sql files used 
for creation can be seen underresource package.) Data.sql file can be edited to manipulate initial records of db.

Assumptions:

Here is what i assume for save appointment and retrieve payment flow:  user selects the date and time for the doctor, calls appointment/calcFee endpoint to see transaction amount. I assume that once user agree to pay that amount,
user redirected to payment page and once transaction is successfull, redirects to our app. At that moment appointment/save endpoint is called to
save appointment and transaction information. 

I added isAppointmentAvailable check for both calcFee and save endpoints to make sure don't make unnecessary calculations if given date and time range is 
occupied or any other client took that appointment while transaction process going on. In the case of succcessfully paid appointment is taken /save endpoint
will throw Business Exception saying there is conflicting appointment. I assume client will call cancel transaction endpoint at that case. (I did not provide
this endpoint.)

When user cancels an appointment, request time is evaluated for cancellation fee. As given example; if there is 1 hour or less to start time of appointment
25% of the appointment fee is hold for cancellation fee and remaining amount is transferred to bank account. (I assumed we have that kind of endpoint to 
transfer money). What appointment/cancel endpoint do is sets appointment state to C represents Cancelled on appointment table and set main record of
the appointment transactionState to R represents Returned on transaction table. If there is penalty fee that client has to pay, new record is inserted 
as cancel appointment transaction with transaction_type value C. 

I did not create an endpoint to see transactions based on doctors or patients but all of the records can bee sen in h2 db console which is 
accessible on http://localhost:8080/h2-console. Username : sa and password: password credentials are defined in application.properties file. 

After downloading my source code, you can compile it with any IDE that supports SpringBoot. I used IntellIJ for example. 
port 8080 is defined as server port on applicaiton.properties file and can be changed if your port is not available. 
Once you run the app, Endpoints will be available on http://localhost:8080









