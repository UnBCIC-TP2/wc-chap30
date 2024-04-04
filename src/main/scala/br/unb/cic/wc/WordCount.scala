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
  val buf = scala.collection.mutable.ListBuffer.empty[a]

  val res =
    for future <- futures buf += future
    yield (buf.foldLeft(base)(op))

  var temp = False
  res.onComplete {
    case Success(v) => temp = True 
    case Failure(e) => temp = False
  }

  if(temp) {
    match value {
      case Some(Success(v)) => v
      case _ => throw new RuntimeException("not expecting")  
    }
  }
  else throw new RuntimeException("error!!!!")
}
