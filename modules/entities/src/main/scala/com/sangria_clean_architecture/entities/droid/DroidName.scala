package com.sangria_clean_architecture.entities.droid

case class DroidName(value: String) {
  assert(value.length <= 50, "droid name fields maximum length from 50 characters")
}
