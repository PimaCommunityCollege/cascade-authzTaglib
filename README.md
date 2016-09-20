# Cascade authorization taglib

A handy library to require membership in a Cascade Server group to view a page.  If the user is a member of the specified group, nothing happens and the page continues to render.  If the user is NOT a member of the required group, they will be immediately redirected to a URL you specify.

## How to use

### Get the JAR into Cascade

Build and JAR it, or grab the binary from the bin folder.  Put the file in:   
[CASCADE_HOME]/tomcat/webapps/ROOT/WEB-INF/lib/

Restart the Cascade app to pick up the jar file.

### Use the taglib in a JSP

Import the taglib, then use the requireGroup tag.
The arguments for requireGroup are:  
group:  the Cascade Server group that you require the user to be a member of   
failURL:  the URL the user will be redirected to if they do not belong to the requireGroup

Example:  
`<%@taglib uri=”http://pcc-authz” prefix=”pcc” %>`   
…   
`<pcc:requireGroup group=”webdev” failUrl=”scram.html” />` 

## Note

Hannon Hill does not provide public maven artifacts for Cascade.  There are some ugly system-scope deps here, to use the native Cascade services, which you surely will have to change (sorry!).  A binary is provided for convenience.
