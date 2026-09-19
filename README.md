# url-shorter-api
URL Shortener API
this project is use maven project, java spring boot version 4.1.1, and java version 21

*** the log files will be generated as files in path url-core/logs/ ***

Firstly, build the project with maven build:
  - clone the project and go into url-parent 
  - then mvn clean install or mvn clean install -DskipTests -U -Dtest.skip=true
    - if you are using linux, you can use compile.sh but please make sure to set JAVA_HOME where your java located and run this command:
      - chmod 755 compile.sh

Secondly, run the spring boot:
  - go into url-core
  - then run file run.bat if you are using windows or run file run.sh if you are using linux or any others method is preferred.

Thirdly, the request payload:

curl -X POST http://localhost:8380/api/register \
-H "Content-Type: application/json" \
-d '{"email":"test@hotmail.com", "password":"test"}'
{"responseCode":"000","responseDesc":"Success"}

curl -X POST http://localhost:8380/api/login \
-H "Content-Type: application/json" \
-d '{"email":"test@hotmail.com", "password":"test"}'
{"responseCode":"000","responseDesc":"Success","tokenJwts":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjQ3Mzg0YS0zYzFiLTRiNDEtOGRkZS0zNGM3MzkxYWQ0OGMiLCJzdWIiOiJwNV9CbW9aMTVDUGl6V0sydmp4eWNBIiwiaWF0IjoxNzg5ODQ3NjE0LCJleHAiOjM1ODUwOTUyMjl9.MBw7vCXfOvCRIh440uwyCCCpXTPBrB5eW4nYFS9w-4wxrukh4MCWlmCjX4WORbubT18dvI16mqLsYDAqZX8qpg"}

curl -X POST http://localhost:8380/api/shorter \
-H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjQ3Mzg0YS0zYzFiLTRiNDEtOGRkZS0zNGM3MzkxYWQ0OGMiLCJzdWIiOiJwNV9CbW9aMTVDUGl6V0sydmp4eWNBIiwiaWF0IjoxNzg5ODQ3NjE0LCJleHAiOjM1ODUwOTUyMjl9.MBw7vCXfOvCRIh440uwyCCCpXTPBrB5eW4nYFS9w-4wxrukh4MCWlmCjX4WORbubT18dvI16mqLsYDAqZX8qpg" \
-H "Content-Type: application/json" \
-d '{"original_url":"https://duckdb.org/docs/current/"}'
{"responseCode":"000","responseDesc":"Success","short_url":"http://localhost:8380/api/YIfgeVrcPD"}

curl -X GET http://localhost:8380/api/urls -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjQ3Mzg0YS0zYzFiLTRiNDEtOGRkZS0zNGM3MzkxYWQ0OGMiLCJzdWIiOiJwNV9CbW9aMTVDUGl6V0sydmp4eWNBIiwiaWF0IjoxNzg5ODQ3NjE0LCJleHAiOjM1ODUwOTUyMjl9.MBw7vCXfOvCRIh440uwyCCCpXTPBrB5eW4nYFS9w-4wxrukh4MCWlmCjX4WORbubT18dvI16mqLsYDAqZX8qpg"
{"responseCode":"000","responseDesc":"Success","urls":[{"longUrl":"www.facebook.com","shortUrl":"http://localhost:8380/api/WdLoBiIuQQ","urlId":2},{"longUrl":"https://duckdb.org/docs/current/","shortUrl":"http://localhost:8380/api/YIfgeVrcPD","urlId":1}]}

curl -X DELETE http://localhost:8380/api/urls/1 \
-H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjQ3Mzg0YS0zYzFiLTRiNDEtOGRkZS0zNGM3MzkxYWQ0OGMiLCJzdWIiOiJwNV9CbW9aMTVDUGl6V0sydmp4eWNBIiwiaWF0IjoxNzg5ODQ3NjE0LCJleHAiOjM1ODUwOTUyMjl9.MBw7vCXfOvCRIh440uwyCCCpXTPBrB5eW4nYFS9w-4wxrukh4MCWlmCjX4WORbubT18dvI16mqLsYDAqZX8qpg"
{"responseCode":"000","responseDesc":"Success"}

*** In this project will use database as Postgresql and using RAM as space***


PS, thank you for reaching out to me to do an assignment test, i'm really grateful and hope our path will intercept in near futer

Teerapat Kongrit
