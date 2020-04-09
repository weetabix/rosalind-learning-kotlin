import java.io.BufferedReader
import java.io.File
import java.net.URL

class RosalindBiosciToolkit {

    // Resources for the Rosalind Code Exersizes

    fun url2string(fileUrl: String): String {
        val inputString = URL(fileUrl).readText()
        return inputString
    }

    fun file2string(fileName: String):String {
        val bufferedReader: BufferedReader = File(fileName).bufferedReader()
        val outString = bufferedReader.use { it.readText() }
        return outString    // TODO <------------------------------ This passes to the read hamming and strip fasta functions
    }

    fun stripFASTA(inString: String): List<Pair<String, String>> {

        var strands = listOf<Pair<String, String>>()

//        val bufferedReader: BufferedReader = File(fileName).bufferedReader()
//        val inputString = bufferedReader.use { it.readText() }                             // TODO     <-----------String Replacement here
        val samples = inString.split(">").toTypedArray()
        for (i in samples.indices) {
            strands += (Pair(
                samples[i].split("\n").toTypedArray()[0],
                samples[i].split("\n").toTypedArray().drop(1).joinToString(separator = "")
            ))
        }
        return strands.filterNot { it == Pair("", "") }
    }

    fun readHammingPair(fileName: String): Pair<String, String> {
        val bufferedReader: BufferedReader = File(fileName).bufferedReader()
        val inputString = bufferedReader.use { it.readText() } // TODO <-----------------------------------------------------------------------Here
        val strings = inputString.split("\n").toTypedArray()
        val outPair = Pair(strings[0], strings[1])
        return outPair
    }

    fun countBases(strand: String): Map<String, Int> {
        val basesMap = mutableMapOf("A" to 0, "C" to 0, "G" to 0, "T" to 0)
        for (base in strand.chunked(1)) {
            when (base) {
                "A" -> basesMap.computeIfPresent("A") { _, v -> v + 1 }
                "G" -> basesMap.computeIfPresent("G") { _, v -> v + 1 }
                "C" -> basesMap.computeIfPresent("C") { _, v -> v + 1 }
                "T" -> basesMap.computeIfPresent("T") { _, v -> v + 1 }
                else -> println("Glitch " + base)
            }
        }
        return basesMap
    }

    fun reverseCompliment(strand: String): String {
        val r_strand = strand.reversed()
        var result = ""
        for (base in 0..r_strand.length - 1) {
            when (r_strand[base].toString()) {
                "T" -> result += "A"
                "A" -> result += "T"
                "C" -> result += "G"
                "G" -> result += "C"
                else -> print("Glitch: " + r_strand[base])
            }
        }
        return result
    }

    fun gcContent(strandList: List<Pair<String, String>>): Pair<String, Double> {
        var gc_result = Pair("None", 0.0)

        for (strand in strandList) {
            var gc_count = 0

            for (base in 0..strand.second.length - 1) {
                when (strand.second[base].toString()) {
                    "C", "G" -> gc_count++
                }
            }
            val gc_perc = gc_count.toDouble() / strand.second.length * 100
            if (gc_perc > gc_result.second) {
                gc_result = Pair(strand.first, gc_perc)
            }
        }
        return gc_result
    }

    fun calculateHammingDistance(stringPair: Pair<String, String>): Int {
        var hamm_count = 0

        for (b in 0..stringPair.first.length - 1) {
            if (stringPair.first[b] != stringPair.second[b])
                hamm_count++
        }
        return hamm_count
    }

    fun transDnaToRna(strand: String): String {
        var result = ""
        for (base in 0..strand.length - 1) {
            when (strand[base].toString()) {
                "T" -> result += "U"
                else -> result += strand[base]
            }
        }
        return result
    }

    fun motifLocations(motifPair: Pair<String, String>): List<Int> {
        // Pair(strand, subs)
        var motifList = mutableListOf<Int>()
        val strand = motifPair.first
        val subs = motifPair.second
        for (i in 0..strand.length - subs.length) {
            if (strand.substring(i, i + subs.length).indexOf(subs) == 0) {
                motifList.add(i + 1)
            }
        }
        return motifList
    }

    fun uniqMotifs(pairList: List<Pair<String, String>>): List<List<String>> {
        var uniqMotif = mutableListOf<List<String>>()
        val compStrand = pairList[0].second
        for (strandPair in pairList.drop(1)) {
            var temp_results = mutableListOf<String>()
            for (x in 0..strandPair.second.length - 1) {
                for (y in 0..strandPair.second.length - x) {
                    if (strandPair.second.substring(x, x + y).isNotEmpty() &&
                        compStrand.contains(strandPair.second.substring(x, x + y)) == true
                    ) {
                        temp_results.add(strandPair.second.substring(x, x + y))
                    }
                }
            }

            uniqMotif.add(temp_results.distinct())
        }
        return uniqMotif
    }

    fun largestCommonMotif(motifs: List<List<String>>): List<String> {

        var motLen: Int = 0
        var motSet: Set<String> = motifs[0].toSet()
        for (testMotif in 1..motifs.size - 1) {
            var motSet_new = motifs[testMotif].intersect(motSet)
            motSet = motSet_new
        }
        for (item in motSet) {
            if (item.length > motLen) {
                motLen = item.length
            }
        }
        return motSet.filter { it.length == motLen }
    }

    fun coLargestCommonMotif(motifs: List<List<String>>): List<String> {

        var motLen: Int = 0
        var motSet: Set<String> = motifs[0].toSet()
        for (testMotif in 1..motifs.size - 1) {

            var motSet_new = motifs[testMotif].intersect(motSet)
            motSet = motSet_new
        }
        for (item in motSet) {
            if (item.length > motLen) {
                motLen = item.length
            }
        }
        return motSet.filter { it.length == motLen }
    }

    fun transRna2Protein(strand: String): String {
        var result = ""
        for (base in strand.chunked(3)) {
            when (base) {
                "UUU" -> result += "F"
                "CUU" -> result += "L"
                "AUU" -> result += "I"
                "GUU" -> result += "V"
                "UUC" -> result += "F"
                "CUC" -> result += "L"
                "AUC" -> result += "I"
                "GUC" -> result += "V"
                "UUA" -> result += "L"
                "CUA" -> result += "L"
                "AUA" -> result += "I"
                "GUA" -> result += "V"
                "UUG" -> result += "L"
                "CUG" -> result += "L"
                "AUG" -> result += "M"
                "GUG" -> result += "V"
                "UCU" -> result += "S"
                "CCU" -> result += "P"
                "ACU" -> result += "T"
                "GCU" -> result += "A"
                "UCC" -> result += "S"
                "CCC" -> result += "P"
                "ACC" -> result += "T"
                "GCC" -> result += "A"
                "UCA" -> result += "S"
                "CCA" -> result += "P"
                "ACA" -> result += "T"
                "GCA" -> result += "A"
                "UCG" -> result += "S"
                "CCG" -> result += "P"
                "ACG" -> result += "T"
                "GCG" -> result += "A"
                "UAU" -> result += "Y"
                "CAU" -> result += "H"
                "AAU" -> result += "N"
                "GAU" -> result += "D"
                "UAC" -> result += "Y"
                "CAC" -> result += "H"
                "AAC" -> result += "N"
                "GAC" -> result += "D"
                "UAA" -> result += "Stop"
                "CAA" -> result += "Q"
                "AAA" -> result += "K"
                "GAA" -> result += "E"
                "UAG" -> result += "Stop"
                "CAG" -> result += "Q"
                "AAG" -> result += "K"
                "GAG" -> result += "E"
                "UGU" -> result += "C"
                "CGU" -> result += "R"
                "AGU" -> result += "S"
                "GGU" -> result += "G"
                "UGC" -> result += "C"
                "CGC" -> result += "R"
                "AGC" -> result += "S"
                "GGC" -> result += "G"
                "UGA" -> result += "Stop"
                "CGA" -> result += "R"
                "AGA" -> result += "R"
                "GGA" -> result += "G"
                "UGG" -> result += "W"
                "CGG" -> result += "R"
                "AGG" -> result += "R"
                "GGG" -> result += "G"
                else -> result += "---"
            }
        }
        return result
    }

    fun proteinMotifLocations(inUrl: String): List<Int> {
        val strandList: List<Pair<String, String>> = stripFASTA(url2string(inUrl))
        var locList: List<Int> = mutableListOf()
        for (strand in strandList) {
            val regex = "N[^P][ST][^P]".toRegex()
            val results = regex.findAll(strand.second)
            for (x in results) {
                locList += x.range.start + 1
            }
        }
        return locList
    }

//    }
}
