package com.sangria_clean_architecture.shared.util

import scala.language.implicitConversions

object EitherSyntax extends EitherSyntax

trait EitherSyntax {
  implicit final def syntaxEitherId[A](a: A): EitherIdOps[A] = new EitherIdOps(a)
}

final class EitherIdOps[A](private val obj: A) extends AnyVal {
  def asRight[B]: Either[B, A] = Right(obj)
  def asLeft[B]: Either[A, B] = Left(obj)
}
