import br.unb.cic.wc._

@main def hello(path: String): Unit =
  val lines = readFile(path)
  val chunks = split(lines, 1000)
  val resMap = mapF(countFrequency)(chunks)
  val resReduce = reduceF(Map.empty[String, Int])(joinMaps(_+_))(resMap)
  val fw = sortAndTake(10)(resReduce)
  println(fw)

def msg = "I was compiled by Scala 3. :)"

def countFrequency(lines: List[String]) : Map[String, Int] = (frequencies compose removeStopWords compose scan)(lines)

def joinMaps[a,b](f : (b, b) => b)(m1: Map[a, b], m2: Map[a, b]): Map[a, b] = {
  var res: scala.collection.mutable.Map[a, b] = scala.collection.mutable.Map.empty[a, b]

  for(k <- m1.keys) {
    if (m2.contains(k)) {
      res(k) = f(m1(k), m2(k)) 
    }
    else {
      res(k) = m1(k)
    }
  }
  for(k <- m2.keys) {
    if (! m1.contains(k)) {
      res(k) = m2(k)
    }
  }
  res.toMap
}
