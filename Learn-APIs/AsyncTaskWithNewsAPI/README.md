#Notes

### AsyncTask tips
- Do not use execute().get() to retrieve the results. This blocks the MainUI until the AsyncTask executes, which defeats the purpose of using it.
###Internet Call must-haves:

- Android Permissions for internet use
- Query Utilities to deconstruct the search query, create a URL connection, and to read the returned JSON response