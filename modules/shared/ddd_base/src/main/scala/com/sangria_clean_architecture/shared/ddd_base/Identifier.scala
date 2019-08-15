package com.sangria_clean_architecture.shared.ddd_base

trait Identifier[+A] {
  def value: A
  def hashCode: Int
  def equals(that: Any): Boolean
}
