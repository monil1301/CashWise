package com.shah.cashwise.di

import org.koin.core.module.Module

/** Platform-specific Koin bindings — currently the preferences `DataStore`. */
expect val platformModule: Module
