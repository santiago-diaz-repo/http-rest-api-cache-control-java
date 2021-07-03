# http-rest-api-cache-control-java

---

This project shows how to work with cache-control, you are going to test the next stuff:

1. When working with `Last-Modified` header, you should keep in mind how it should work:
   1. When using a safe method, for instance GET, you should send the `If-Modified-Since` header, if resource has not changed since that date, API will return 304 or 200 otherwise 
   2. When using an unsafe method, for instance PUT, you should send the `If-Unmodified-Since` header, if resource has not changed since that date, API will update it and return 200 or 412 otherwise
2. When working with `ETag` header, you should keep in mind how it should work:
    1. When using a safe method, for instance GET, you should send the `If-None-Match` header, if etag has not changed, API will return 304 or 200 otherwise
    2. When using an unsafe method, for instance PUT, you should send the `If-Match` header, if etag has not changed, API will update resource and return 200 or 412 otherwise

If you have any doubt, do not hesitate in contacting me, my email is santiago.diaz5689@gmail.com