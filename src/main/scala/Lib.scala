import java.nio.file.Path

import scala.io.Source
import scala.collection.mutable.HashMap
import scala.collection.immutable.ListMap


/**
  * Dado um 'path' para um arquivo, lê as linhas do arquivo e as
  * retorna como uma lista de strings. 
  */  
def readFile(path: String) = Source.fromFile(path).getLines.toList

/**
  * Dada uma String, com algumas palavras, realiza o
  * split da string (retornando uma lista de palavras) e em seguida
  * substitui todos os caracteres não-alpha pela string vazia e converte
  * cada palavra para 'lower case' 
  */ 
def filterChars(str: String) = str.replaceAll("[^a-zA-Z' ']", "").toLowerCase.split(" ").toList


/**
  * Dada uma lista com as linhas de um arquivo, onde cada linha
  * eh uma String, retorna uma lista com todas as palavras. 
  */
def scan(lst: List[String]) = lst.flatMap(s => filterChars(s))

/**
  * Retorna verdadeiro se 'word' for um stopWord. 
  */ 
def isStopWord(word: String) = stopWords.contains(word.toLowerCase()) 

/**
  * Dada uma lista de palavras, remove as palavras que são stopWord.
  */ 
def removeStopWords(words : List[String]) = words.filter(w => ! isStopWord(w) && w.size > 3)

/**
  * Dada uma lista de palavras, conta a frequencia com que elas
  * ocorrem na lista. Retorna um Mapa de String (palavra) para Int (frequencia). 
  */ 
def frequencies(words: List[String]) = {
  val res = new HashMap[String, Int]()
  words.foreach(w => res += w -> (res.getOrElse(w, 0) + 1))
  res.toMap
}

/**
  * Retorna as 'n' palavras mais frequentes.
  * Essa funcao pode ser aplicada a um unico argumento,
  * retornando uma nova funcao de dois agumentos.
  */ 
def sortAndTake(n: Int)(map: Map[String, Int]) = ListMap(map.toSeq.sortWith(_._2 > _._2)*).take(n)

// object MainProgram extends CommandApp(
//   name="Word Count",
//   header = "a simple word count implementation using the \"Pipeline\" style",
//   main = {
//     val inputOpt= Opts.argument[String](metavar = "Input file")
//     val sizeOpt  = Opts.option[Int]("words", short = "n", metavar = "count", help = "Set number of most frequent words to print.")

//     (inputOpt, sizeOpt).mapN { (input, size) => 
//       val fw = (sortAndTake(size) compose frequencies compose removeStopWords compose scan compose readFile)(input)
//       println(fw)
//     }
//   }
// )

/**
  * Retorna um set com as stop-words.
  */ 
val stopWords = Set("the", "about", "above", "after", "again", "against",
    "all", "and", "any", "because", "before", "below", "between", "but",
    "down", "during", "for", "from", "further", "here", "into", "more","once",
    "only", "other", "over", "same", "some", "such", "that", "then",
    "there", "these", "this", "those", "through", "under", "until", "very",
    "what", "when", "where", "which", "while", "who", "which",
    "with", "could", "were", "your", "have", "will", "been", "would", 
    "they", "their", "should", "myself", "them", "upon", "might",
    "first", "eyes", "every", "you", "than", "thought", "whom", "ever",
    "most", "even","said", "shall", "towards", "found", "being",
    "time", "also", "him", "her", "still", "must", "many")
