package com.sangria_clean_architecture.di

import com.google.inject.AbstractModule
import com.google.inject.name.Names

import scala.concurrent.ExecutionContext

class ExecutionContextModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[ExecutionContext])
      .annotatedWith(Names.named("default-app-context"))
      .toProvider(classOf[DefaultAppContextProvider])
  }

}
