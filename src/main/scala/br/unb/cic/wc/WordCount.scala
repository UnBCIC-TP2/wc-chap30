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

def reduceF[a,b](base : b)(op : (b, a) => b)(callBack : b => Unit)(futures : List[Future[a]]): Unit = {
  val f = Future.sequence(futures)
  f.onComplete({
    case Success(list) => callBack(list.foldLeft(base)(op))
    case Failure(exception) => 
  })
  println("waiting...")
}
