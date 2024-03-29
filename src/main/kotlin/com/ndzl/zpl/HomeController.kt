package com.ndzl.zpl

import com.ndzl.zpl.ZPLSupportFunctions.lookupThenBuildTutorial
import java.io.IOException
import java.util.*
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//funziona su fenix, 4 dic 2020
//jar kotlin in httpdocs/WEB-INF/lib
//txt file con descrizione zpl in 2020: /var/ www/vhosts/cxnt48.com/docs/   opp   2021:/chroot/home/cxntcom/cxnt48.com/

/*DEPLOY DI QUESTO PROGETTO 11.06.2021
* QUESTO NON è UN PROGETTO J2EE / TOMCAT
* GENERA UNICAMENTE I .CLASS DA COPIARE DENTRO UN PROGETTO VUOTO /zpl
* ad es. da Eclipse con WAR si può creare alberatura per /zpl e poi sostituire .class in /cxnt48.com/apache-tomcat-9.0.46/webapps/ROOT/WEB-INF/classes/com/ndzl/zpl
* i jar KOTLIN vanno copiati in /cxnt48.com/apache-tomcat-9.0.46/webapps/ROOT/WEB-INF/lib
* i jar kotlin sono
* annotations-13.0.jar; javax.json.jar; kotlin-stdlib-1.4.20.jar; kotlin-stdlib-common-1.4.20.jar; kotlin-stdlib-jdk8-1.4.20.jar
* presenti in un backup del server FENIX
* */


@WebServlet("/zpl")
class HomeController : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, res: HttpServletResponse) {
        res.writer.write("Hello, Kotlin World on Tomcat!")
        res.contentType = "application/json"
        var localCode = req?.getParameter("code")
        if (localCode != null) {
            ZPLSupportFunctions.logLocally("ANNOTATED-ZPL::HomeController::doGet reqBody=$localCode")

            if(localCode.startsWith("^A"))
                localCode = "^A "+localCode.substring(2)

            res.writer.println(ZPLSupportFunctions.objExplanation.lookupThenBuildTutorial(localCode))
        }
        else res.writer.println("<h1>Annotated ZPL Tomcat Kotlin </h1><br>Syntax: cxnt48.com/zpl?code=^XA")
    }


    @Throws(IOException::class)
    fun extractPostRequestBody(request: HttpServletRequest?): String? {
        if ("POST".equals(request?.method, ignoreCase = true)) {
            val s = Scanner(request?.inputStream, "UTF-8").useDelimiter("\\A")
            return if (s.hasNext()) s.next() else ""
        }
        return ""
    }

    override fun doPost(req: HttpServletRequest?, res: HttpServletResponse?) {

        //val httpSession: HttpSession? = req?.session
        //val _sc: ServletContext? = httpSession?.servletContext

        res?.addHeader("Access-Control-Allow-Origin", "*")
        res?.addHeader("Author", "{N.DZL}, cxnt48 (C)2019-2020")
        res?.contentType = "application/json"

        var localCode = extractPostRequestBody(req)
        //_sc?.log("ANNOTATED-ZPL::HomeController::doPost reqBody="+localCode)

        if (localCode != null) {
            ZPLSupportFunctions.logLocally("ANNOTATED-ZPL::HomeController::doPost reqBody=$localCode")
            if(localCode.startsWith("^A"))
                localCode = "^A "+localCode.substring(2)

            res?.writer?.write(ZPLSupportFunctions.objExplanation.lookupThenBuildTutorial(localCode))
        }
        else {
            res?.writer?.write("doPost::HttpServletRequest ERROR - pass the ZPL command as body.")
        }
    }
}