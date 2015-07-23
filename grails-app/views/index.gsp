<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>
    <style type="text/css" media="screen">
    #status {
        background-color: #eee;
        border: .2em solid #fff;
        margin: 2em 2em 1em;
        padding: 1em;
        width: 12em;
        float: left;
        -moz-box-shadow: 0px 0px 1.25em #ccc;
        -webkit-box-shadow: 0px 0px 1.25em #ccc;
        box-shadow: 0px 0px 1.25em #ccc;
        -moz-border-radius: 0.6em;
        -webkit-border-radius: 0.6em;
        border-radius: 0.6em;
    }

    .ie6 #status {
        display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
    }

    #status ul {
        font-size: 0.9em;
        list-style-type: none;
        margin-bottom: 0.6em;
        padding: 0;
    }

    #status li {
        line-height: 1.3;
    }

    #status h1 {
        text-transform: uppercase;
        font-size: 1.1em;
        margin: 0 0 0.3em;
    }

    #page-body {
        margin: 2em 1em 1.25em 18em;
    }

    h2 {
        margin-top: 1em;
        margin-bottom: 0.3em;
        font-size: 1em;
    }

    p {
        line-height: 1.5;
        margin: 0.25em 0;
    }

    #controller-list ul {
        list-style-position: inside;
    }

    #controller-list li {
        line-height: 1.3;
        list-style-position: inside;
        margin: 0.25em 0;
    }

    @media screen and (max-width: 480px) {
        #status {
            display: none;
        }

        #page-body {
            margin: 0 1em 1em;
        }

        #page-body h1 {
            margin-top: 0;
        }
    }
    </style>
</head>

<body>
<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div id="status" role="complementary">
    <h1>Application Status</h1>
    <ul>
        <li>App version: <g:meta name="app.version"/></li>
        <li>Grails version: <g:meta name="app.grails.version"/></li>
        <li>Groovy version: ${GroovySystem.getVersion()}</li>
        <li>JVM version: ${System.getProperty('java.version')}</li>
        <li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
        <li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
        <li>Domains: ${grailsApplication.domainClasses.size()}</li>
        <li>Services: ${grailsApplication.serviceClasses.size()}</li>
        <li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
    </ul>

    <h1>Installed Plugins</h1>
    <ul>
        <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
            <li>${plugin.name} - ${plugin.version}</li>
        </g:each>
    </ul>
</div>

<div id="page-body" role="main">
    <h1>Welcome to CloudCard Image Server</h1>
    <g:set var="url" value="${request.serverName}:${request.serverPort}${request.forwardURI}"/>
    <div id="controller-list" role="navigation">
        <h2>To Save Images To BbTS DB:</h2>
        <ol>
            <li><g:link controller="user">Configure a service account.</g:link></li>
            <li>
                POST images to ${url}customerPhotos
                <ol>
                    <li>Include Basic Auth Header</li>
                    <li>Request Body: { "id" : "{CustNum}", "photo" : [{array of binary picture data}] }</li>
                    <li>Example Body: { "id" : "1234567890", "photo" : [1,3,6,-2,6,7,...] }</li>
                </ol>
            </li>
            <li>Image Server will respond
                <ol>
                    <li>HTTP Status 201 CREATED means the request was successful.</li>
                    <li>HTTP Status 404 NOT_FOUND means no customer could be found for that CustNum.</li>
                </ol>
            </li>
        </ol>

        <h2>To Display BbTS Images in Web Pages:</h2>
        <ol>
            <li><g:link controller="user">Configure a service account.</g:link></li>
            <li>
                From you application server send a GET request to /customerPhotos/{CustNum}<br/>
                (i.e. GET ${url}customerPhotos/1234567890 )
                <ol>
                    <li>Include Basic Auth Header</li>
                </ol>
            </li>
            <li>Image Server will respond
                <ol>
                    <li>HTTP Status 200 OK means the request was successful.</li>
                    <li>HTTP Status 404 NOT_FOUND means no customer could be found for that CustNum.</li>
                    <li>Response Body: {"custId":11,"key":"h9jhwlf9YZIowzJYY6rlXm0fkP3FoMtl","photo": [...]}</li>
                </ol>
            </li>
            <li>The "key" attribute from the response can be used to load images from web pages.<br/>
                Example: &lt;img src="${url}images/h9jhwlf9YZIowzJYY6rlXm0fkP3FoMtl" /&gt;
            </li>

        </ol>

    </div>
</div>
</body>
</html>
