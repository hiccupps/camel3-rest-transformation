
Curl for conversion :

curl --location --request POST 'http://localhost:8084/api/getData' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "abhishek",
"age": "32"
}'



Curl for List Conversion : 

curl --location --request POST 'http://localhost:8084/api/getListData' \
--header 'Content-Type: application/json' \
--data-raw '{
"employees" : [
{
"name": "abhishek",
"age": "32"
},
{
"name": "arushi",
"age": "31"
}
]
}'


