class Problem3 {


    fun reverseCompliment(strand: String) {
        var r_strand = strand.reversed()
        for (base in 0..r_strand.length - 1) {
            when (r_strand[base].toString()) {
                "T" -> print("A")
                "A" -> print("T")
                "C" -> print("G")
                "G" -> print("C")

                else -> print("Glitch: " + r_strand[base])
            }
        }
    }
}


val foo = Problem3()
val dna =
    "TGCACCACCTTCTGGACTGGCGGCCTTAGTACCGATACCTCAATAAGCAGGGACCTGACGCGAACCATGGATGGGACAACCGCGTCCCTCGCCCGTTGCCTGTGGTAGCAGCGTCCTAATAAAACCATGCGCCGATTTGGGCTGCCTAAATTCACGATCTCGAAGCAGTAGGACAACTTTTTTGACATACAAATACGGCGGCAACGAGTGAAGGATGGTGCGAAAAGCCCCTGTGGAGGAATTTCAGGCCTGGCGTGCGTATCAGGGACATTCCTGGACTTTATGGGAGATCGAACCCGATGTCGGTCCGGTCAATCTGCGGAAATACTTTAGAGATGGTGAAAACCTGTTCCGCCGAGCGTCGTATTCCTTATAGACACGTAAAAACGTCCGTTTAAACAGCTCGTGGGGAGAGAACCCTGTTCAGAACCAAGAGGCGCCTGGTTAAATTGCTACAGTTAAAAGTAGTGCTGGTGGTCTTACCAAGACCGCCAATTACGGATCGATGAGAAGTGTGGTCTTTCCGATGTGGCTCCACCAGCAGCGGGCTGCGTATCTAATGCCAAGGACTAGGTCTCTAGTATGAGTGTAACGTAATTGCAATAGTTCTGTAATGCGCTGTATAATTAACTGTATAGATAACATCTGTGGAATCAGGCTTCGGAACGGGCCGGAGGTGGTCAACAGCACCAAAGACAGATCTATAATCTAACCTTGACGATAGTGATCCGCGCCCGACGGGCAGACGGCCAAAGATTCCTCCACTCGGAGCAGGTGAAAACGTCCGCCAAAGGGACGTAGGTGTGAGTACGGCGCGGGAATGCACCTCTGTAATACATGCCTCTCACTGTTGACCGGGGCTATGCGCTAC"
foo.reverseCompliment(dna)