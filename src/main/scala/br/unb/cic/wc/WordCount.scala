package br.unb.cic.wc

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

import scala.compiletime.uninitialized
type line = String

def split[a](lines: List[a], n: Integer = 100): List[List[a]] =
  if lines == List() then List()
  else
    val (f, s) = lines.splitAt(n)
    f :: split(s, n)


def mapF[a,b](f : List[a] => b)(chuncks: List[List[a]]): List[b] = chuncks.map(list => f(list))

def reduceF[a,b](base : b)(op : (b, a) => b)(list : List[a]): b = list.foldLeft(base)(op)
