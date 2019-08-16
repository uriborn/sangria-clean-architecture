package com.sangria_clean_architecture.di

import com.google.inject.Provider

import scala.concurrent.ExecutionContext

class DefaultAppContextProvider extends Provider[ExecutionContext] {

  override def get(): ExecutionContext = ExecutionContext.global // TODO

}
