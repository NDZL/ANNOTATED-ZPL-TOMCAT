package com.ndzl.zpl



import com.ndzl.zpl.Roadmap.lookupThenBuildJSON

import com.ndzl.zpl.Roadmap.objExplanation
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*


import com.ndzl.zpl.Roadmap.zpl2_all_raw_cmds

//JUST FOR APPLICATION EVOLUTION - lavora su copie dell'originale

//5DEC2020-TURNING  future_zpl.txt INTO JSON FORMAT

//==> memo: il future_zpl.txt contiene alcuni doppi apici che vanno sostituiti
//==> niente a-capo nelle stringhe -> basta un replace
//==> QUALCHE char unescaped in corrisp di "ZPL":"^BA",






object Roadmap {

    val zpl2_raw_Lines: ArrayList<String> = arrayListOf("^A  Scalable/Bitmapped Font", "^A@ Use Font Name to Call Font", "^B0 Aztec Bar Code Parameters", "^B1 Code 11 Bar Code", "^B2 Interleaved 2 of 5 Bar Code", "^B3 Code 39 Bar Code", "^B4 Code 49 Bar Code", "^B5 Planet Code bar code", "^B7 PDF417 Bar Code", "^B8 EAN-8 Bar Code", "^B9 UPC-E Bar Code", "^BA Code 93 Bar Code", "^BB CODABLOCK Bar Code", "^BC Code 128 Bar Code (Subsets A, B, and C)", "^BD UPS MaxiCode Bar Code", "^BE EAN-13 Bar Code", "^BF MicroPDF417 Bar Code", "^BI Industrial 2 of 5 Bar Codes", "^BJ Standard 2 of 5 Bar Code .", "^BK ANSI Codabar Bar Code .", "^BL LOGMARS Bar Code", "^BM MSI Bar Code .", "^BO Aztec Bar Code Parameters", "^BP Plessey Bar Code", "^BQ QR Code Bar Code", "^BR GS1 Databar", "^BS UPC/EAN Extensions", "^BT TLC39 Bar Code", "^BU UPC-A Bar Code", "^BX Data Matrix Bar Code", "^BY Bar Code Field Default", "^BZ POSTAL Bar Code", "^CC Change Caret", "~CC Change Caret", "^CD Change Delimiter", "~CD Change Delimiter", "^CF Change Alphanumeric Default Font", "^CI Change International Font/Encoding", "^CM Change Memory Letter Designation", "^CN Cut Now", "^CO Cache On", "^CP Remove Label", "^CT Change Tilde", "~CT Change Tilde", "^CV Code Validation", "^CW Font Identifier", "~DB Download Bitmap Font", "~DE Download Encoding", "^DF Download Format", "~DG Download Graphics", "~DN Abort Download Graphic", "~DS Download Intellifont (Scalable Font)", "~DT Download Bounded TrueType Font", "~DU Download Unbounded TrueType Font", "~DY Download Objects", "~EG Erase Download Graphics", "^FB Field Block", "^FC Field Clock", "^FD Field Data", "^FH Field Hexadecimal Indicator", "^FL Font Linking", "^FM Multiple Field Origin Locations", "^FN Field Number", "^FO Field Origin", "^FP Field Parameter", "^FR Field Reverse Print", "^FS Field Separator", "^FT Field Typeset", "^FV Field Variable", "^FW Field Orientation", "^FX Comment", "^GB Graphic Box", "^GC Graphic Circle", "^GD Graphic Diagonal Line", "^GE Graphic Ellipse", "^GF Graphic Field", "^GS Graphic Symbol", "~HB Battery Status", "~HD Head Diagnostic", "^HF Host Format", "^HG Host Graphic", "^HH Configuration Label Return", "~HI Host Identification", "~HM Host RAM Status", "~HQ Host Query", "~HS Host Status Return", "^HT Host Linked Fonts List", "~HU Return ZebraNet Alert Configuration", "^HV Host Verification", "^HW Host Directory List", "^HY Upload Graphics", "^HZ Display Description Information", "^ID Object Delete", "^IL Image Load", "^IM Image Move", "^IS Image Save", "~JA Cancel All", "^JB Initialize Flash Memory", "~JB Reset Optional Memory", "~JC Set Media Sensor Calibration", "~JD Enable Communications Diagnostics", "~JE Disable Diagnostics", "~JF Set Battery Condition", "~JG Graphing Sensor Calibration", "^JH Early Warning Settings", "^JI Start ZBI (Zebra BASIC Interpreter)", "~JI Start ZBI (Zebra BASIC Interpreter)", "^JJ Set Auxiliary Port", "~JL Set Label Length", "^JM Set Dots per Millimeter", "~JN Head Test Fatal", "~JO Head Test Non-Fatal", "~JP Pause and Cancel Format", "~JQ Terminate Zebra BASIC Interpreter", "~JR Power On Reset", "^JS Sensor Select", "~JS Change Backfeed Sequence", "^JT Head Test Interval", "^JU Configuration Update", "^JW Set Ribbon Tension", "~JX Cancel Current Partially Input Format", "^JZ Reprint After Error", "~KB Kill Battery (Battery Discharge Mode)", "^KD Select Date and Time Format (for Real Time Clock)", "^KL Define Language", "^KN Define Printer Name", "^KP Define Password", "^KV Kiosk Values", "^LF List Font Links", "^LH Label Home", "^LL Label Length", "^LR Label Reverse Print", "^LS Label Shift", "^LT Label Top", "^MA Set Maintenance Alerts", "^MC Map Clear", "^MD Media Darkness", "^MF Media Feed", "^MI Set Maintenance Information Message", "^ML Maximum Label Length", "^MM Print Mode", "^MN Media Tracking", "^MP Mode Protection", "^MT Media Type", "^MU Set Units of Measurement", "^MW Modify Head Cold Warning", "^NC Select the Primary Network Device", "~NC Network Connect", "^ND Change Network Settings", "^NI Network ID Number", "~NR Set All Network Printers Transparent", "^NS Change Wired Networking Settings .", "~NT Set Currently Connected Printer Transparent", "^PA Advanced Text Properties", "^PF Slew Given Number of Dot Rows", "^PH Slew to Home Position", "~PH Slew to Home Position", "~PL Present Length Addition", "^PM Printing Mirror Image of Label", "^PN Present Now", "^PO Print Orientation", "^PP Programmable Pause", "~PP Programmable Pause", "^PQ Print Quantity", "~PR Applicator Reprint", "^PR Print Rate", "~PS Print Start", "^PW Print Width", "~RO Reset Advanced Counters", "^SC Set Serial Communications", "~SD Set Darkness", "^SE Select Encoding Table", "^SF Serialization Field (with a Standard FD String)", "^SI Set Sensor Intensity", "^SL Set Mode and Language (for Real-Time Clock)", "^SN Serialization Data", "^SO Set Offset (for Real-Time Clock)", "^SP Start Print", "^SQ Halt ZebraNet Alert", "^SR Set Printhead Resistance", "^SS Set Media Sensors", "^ST Set Date and Time (for Real-Time Clock)", "^SX Set ZebraNet Alert", "^SZ Set ZPL Mode", "~TA Tear-off Adjust Position", "^TB Text Blocks", "^TO Transfer Object", "~WC Print Configuration Label", "^WD Print Directory Label", "~WQ Write Query", "^XA Start Format", "^XB Suppress Backfeed", "^XF Recall Format", "^XG Recall Graphic", "^XS Set Dynamic Media Calibration", "^XZ End Format", "^ZZ Printer Sleep", "^HL Return RFID Data Log to Host", "~HL Return RFID Data Log to Host", "^HR Calibrate RFID Tag Position", "^RA Read AFI or DSFID Byte", "^RB Define EPC Data Structure", "^RE Enable/Disable E.A.SBit", "^RF Read or Write RFID Format", "^RI Get RFID Tag ID", "^RL Lock/Unlock RFID Tag Memory", "^RLM Lock/Unlock the Specified Memory Bank", "^RLB Permanently Lock Specified Memory Sections", "^RM Enable RFID Motion", "^RN Detect Multiple RFID Tags in Encoding Field", "^RQ Quick Write EPC Data and Passwords", "^RR Specify RFID Retries for a Block or Enable Adaptive Antenna Selection", "^RS Set Up RFID Parameters", "^RT Read RFID Tag", "^RU Read Unique RFID Chip Serialization", "~RV Report RFID Encoding Results", "^RW Set RF Power Levels for Read and Write", "^RZ Set RFID Tag Password and Lock Tag", "^WF Encode AFI or DSFID Byte", "^WT Write (Encode) Tag", "^WV Verify RFID Encoding Operation", "^KC Set Client Identifier (Option 61)", "^NB Search for Wired Print Server during", "^NN Set SNMP", "^NP Set Primary/Secondary Device", "^NT Set SMTP", "^NW Set Web Authentication Timeout Value", "^WA Set Antenna Parameters", "^WE Set WEP Mode", "^WI Change Wireless Network Settings", "^WL Set LEAP Parameters", "~WL Print Network Configuration Label", "^WP Set Wireless Password", "^WR Set Transmit Rate", "~WR Reset Wireless Radio Card and Print Server", "^WS Set Wireless Radio Card Values", "^WX Configure Wireless Securities")

    val zpl2_all_raw_cmds = zpl2_raw_Lines.map { s -> s.substring(0, 3) }

    fun getTimeStamp(): String {
        val sdf2 = SimpleDateFormat("yyyy-MMM-dd HH:mm:ss zzz", Locale.ITALY)
        return "" + sdf2.format(Date.from(LocalDateTime.now().atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.of("CET")).toInstant()))
    }

    @Throws(IOException::class)
    fun logLocally(tbw: String) {
        val path = Paths.get("/Users/n.dzl/Downloads/local_log.txt")
        Files.write(path, (getTimeStamp() + " "+ tbw+"\n").toByteArray(), StandardOpenOption.APPEND)
    }

    fun readFileAsLinesUsingUseLines(fileName: String): List<String> = File(fileName).useLines { /*println("-->calling readFileAsLinesUsingUseLines"); */logLocally("-->calling ZPLSupportFunctions::readFileAsLinesUsingUseLines"); it.toList() }  //ndzl-03dic2020-as a replacement of spring framework

    val txtzplpath: Path = Paths.get("/Users/n.dzl/IdeaProjects/Annotated-ZPL-TomcatKotlin/target/classes/future_zpl.txt")

    val lines = readFileAsLinesUsingUseLines(txtzplpath.toString())

    fun ArrayList<Int>.initRotateLeft(): ArrayList<Int> {
        this[0] = lines.size - 1
        Collections.rotate(this, -1)
        return this
    }

    val descriptionIdx = lines.mapIndexedNotNullTo(ArrayList()) { index, s -> if (s == "Description:") index else null }
    val lastCmdRowIdx = lines
            .mapIndexedNotNullTo(ArrayList()) { index, s -> if (s == "Description:") index else null }
            .initRotateLeft()

    val zplCmds = descriptionIdx.map { i -> lines[i - 2] }
//println(zplCmds)

    val shortDescriptions = descriptionIdx.map { i -> lines[i - 1] }

    val formatIdx = lines.mapIndexedNotNullTo(java.util.ArrayList())
    { idx, s ->
        when (s.indexOf("Format:")) {
            -1 -> null
            else -> idx
        }
    }

    val longDescription = descriptionIdx.mapIndexed { index, i -> lines.subList(i + 1, formatIdx[index]).reduce { acc, s -> acc + " " + s } }

    val zplCmdFormats = lines.mapIndexedNotNullTo(java.util.ArrayList())
    { _, s ->
        when (s.indexOf("Format:")) {
            -1 -> null
            else -> s.substring(7).trim()
        }
    }

    val paramsDetails = formatIdx.mapIndexed { index, i -> lines.subList(i, lastCmdRowIdx[index] - 3).fold("") { acc, s -> acc + "\n" + s } }

    //RICAVA I COMANDI ZPL DALLA RIGA DI FORMAT, ASSUMENDO CHE I COMANDI SIANO LUNGHI 3 CHARù
//ALCUNI CMD COME AD ES. ^A NON SONO LUNGHI 3 => TXT FIXED CON UNO SPAZIO
    val cmdParams = zplCmdFormats.map { s -> s.substring(3).split(",", ":", ".") }

    data class AnnotatedZPLCommand(val command: String, val shortExplanation: String, val longDescription: String, val formalParamsListTxt: String, val params: List<String>)

 /*   fun Map<String, AnnotatedZPLCommand>.lookupThenBuildTutorial(cmdAndParamsToExplain: String): String {
        val localCmd = this[cmdAndParamsToExplain.substring(0, 3)]?.command
        val localShortExpl = this[localCmd]?.shortExplanation

        var sb: StringBuilder = StringBuilder("")
        if (localCmd == null) {
            //cerca in zpl2.kt una descrizione semplice del comando non trovato
            val missingFullCommand = cmdAndParamsToExplain.substring(0, 3)
            val zpl2SimpleExpl = when (zpl2_all_raw_cmds.indexOf(missingFullCommand)) {
                -1 -> "$missingFullCommand ZPL command not found or not yet implemented"
                else -> zpl2_raw_Lines[zpl2_all_raw_cmds.indexOf(missingFullCommand)]
            }

            sb.appendLine(zpl2SimpleExpl)
        } else {
            sb.appendLine("ZPL command: $localCmd")
            sb.appendLine("MEANING: $localShortExpl")
            sb.appendLine("PARAMS: " + this[localCmd]?.formalParamsListTxt)
            sb.appendLine("DESCRIPTION: " + this[localCmd]?.longDescription)
        }
        return sb.toString()
    }

*/

    fun Map<String, AnnotatedZPLCommand>.lookupThenBuildJSON(cmdAndParamsToExplain: String): String {
        val localCmd = this[cmdAndParamsToExplain.substring(0, 3)]?.command
        val localShortExpl = this[localCmd]?.shortExplanation

        var sb: StringBuilder = StringBuilder("")
        sb.appendLine("{")
        if (localCmd == null) {
            //cerca in zpl2.kt una descrizione semplice del comando non trovato
            val missingFullCommand = cmdAndParamsToExplain.substring(0, 3)
            val zpl2SimpleExpl = when (zpl2_all_raw_cmds.indexOf(missingFullCommand)) {
                -1 -> "$missingFullCommand ZPL command not found or not yet implemented"
                else -> zpl2_raw_Lines[zpl2_all_raw_cmds.indexOf(missingFullCommand)]
            }

            sb.appendLine("\"ZPL\":\"$cmdAndParamsToExplain\",")
            sb.appendLine("\"MEANING\":\"$zpl2SimpleExpl\"")
        } else {

            sb.appendLine("\"ZPL\":\"$localCmd\",")
            sb.appendLine("\"MEANING\":\"$localShortExpl\",")
            sb.appendLine("\"PARAMS\":\""+this[localCmd]?.formalParamsListTxt?.replace("\n"," | ")+"\",")
            sb.appendLine("\"DESCRIPTION\":\""+this[localCmd]?.longDescription?.replace("\n"," | ")+"\"")
        }
        sb.appendLine("}")
        return sb.toString()
    }

    val objExplanation =
            zplCmds.map { command ->
                val idxToExplain = descriptionIdx[zplCmds.indexOf(command)]
                val paramsToExplain = cmdParams[zplCmds.indexOf(command)]
                val sd = shortDescriptions[zplCmds.indexOf(command)]
                val ld = longDescription[zplCmds.indexOf(command)]
                val pd = paramsDetails[zplCmds.indexOf(command)]
                val pte = paramsToExplain.mapNotNull { p -> lines.subList(idxToExplain, lines.size).find { s -> s.startsWith(p + " =") } }
                command to AnnotatedZPLCommand(
                        command,
                        sd,
                        ld,
                        pd,
                        pte
                )
            }.toMap()
}

fun main(args: Array<String>){
    println("hi from Roadmap\n-----\n\n")

    println("[")
    zpl2_all_raw_cmds .map { println( objExplanation.lookupThenBuildJSON( it ) +",\n" ) }
    println("]")

}