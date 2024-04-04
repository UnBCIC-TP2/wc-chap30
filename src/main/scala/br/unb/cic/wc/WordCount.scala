package br.unb.cic.wc

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

import scala.compiletime.uninitialized
type line = String

def split(lines: List[String], n: Integer = 100): List[List[String]] =
  if lines == List() then List()
  else
    val (f, s) = lines.splitAt(n)
    f :: split(s, n)


def mapF[a](f : List[String] => a)(chuncks: List[List[String]]): List[Future[a]] = chuncks.map(list => Future { f(list) })

def reduceF[a,b](base : b)(op : (b, a) => b)(futures : List[Future[a]]): b = {
  print(futures.length)
  val f = Future.sequence(futures)
  var res : scala.collection.mutable.Set[b] = scala.collection.mutable.Set.empty
  f.onComplete {
    case Success(l) => res.addOne(l.foldLeft(base)(op))
    case Failure(e) => {
      print("foo")
      throw new Exception("foo")
    }
  }
  res.iterator.next()
}
